#!/usr/bin/env bash
set -euo pipefail
shopt -s nullglob

join_by() {
  local sep="$1"
  shift
  local out=""
  local first=1
  for item in "$@"; do
    if (( first )); then
      out="$item"
      first=0
    else
      out+="$sep$item"
    fi
  done
  printf '%s' "$out"
}

run_logged() {
  local log_file="$1"
  shift

  if ! "$@" >"$log_file" 2>&1; then
    echo "Command failed: $*" >&2
    echo "See log: $log_file" >&2
    tail -n 40 "$log_file" >&2 || true
    exit 1
  fi
}

print_compact_aggregate_output() {
  local aggregate_log="$1"
  awk '
    /^\[INFO\]$/ { print; next }
    /^\[INFO\] -+< / { print; next }
    /^\[INFO\] Building / { print; next }
    /^\[INFO\]   from / { print; next }
    /^\[INFO\] -+\[ jar \]-+$/ { print; next }
    /^\[INFO\] --- javadoc:[^:]+:aggregate-no-fork .* ---$/ { print; next }
  ' "$aggregate_log"
}

repo_root="$(cd "$(dirname "$0")/.." && pwd)"
cd "$repo_root"

module_paths=(
  "core"
  "orchestration"
  "core-services/document-grounding"
  "core-services/prompt-registry"
  "foundation-models/openai"
  "foundation-models/sap-rpt"
)

module_names=(
  "AI Core client"
  "Orchestration client"
  "Document Grounding Client"
  "Prompt Registry client"
  "OpenAI client"
  "SAP RPT Model Client"
)

module_artifact_ids=(
  "sdk-parent"
  "core"
  "orchestration"
  "document-grounding"
  "prompt-registry"
  "openai"
  "sap-rpt"
)

out_dir="$repo_root/target/local-javadocs"
logs_dir="$repo_root/.local-javadocs-logs"

mkdir -p "$logs_dir"

build_log="$logs_dir/build.log"
aggregate_log="$logs_dir/aggregate-no-fork.log"
delombok_javadoc_log="$logs_dir/delombok-javadoc.log"

# Generate everything in one Maven run.
run_logged "$build_log" mvn -B -ntp -Dstyle.color=never -Drelease -DskipTests -Dmaven.javadoc.failOnError=false clean package site

# Run aggregate-no-fork once in isolation so we can print a short, stable status block.
run_logged "$aggregate_log" mvn -B -ntp -Dstyle.color=never -Drelease -DskipTests -Dmaven.javadoc.failOnError=false javadoc:aggregate-no-fork

for module_artifact_id in "${module_artifact_ids[@]}"; do
  if ! grep -qE "^\[INFO\] --- javadoc:[^:]+:aggregate-no-fork .* @ ${module_artifact_id} ---$" "$aggregate_log"; then
    echo "Missing aggregate-no-fork execution line for module artifact: ${module_artifact_id}" >&2
    echo "See log: $aggregate_log" >&2
    exit 1
  fi
done

print_compact_aggregate_output "$aggregate_log"

options_file="$repo_root/target/site/apidocs/options"
packages_file="$repo_root/target/site/apidocs/packages"
patched_options_file="$repo_root/target/site/apidocs/options.delombok"

if [[ ! -f "$options_file" || ! -f "$packages_file" ]]; then
  echo "Missing aggregate javadoc argument files in $repo_root/target/site/apidocs" >&2
  exit 1
fi

delombok_paths=()
for module_path in "${module_paths[@]}"; do
  delombok_dir="$repo_root/$module_path/target/delombok"
  if [[ ! -d "$delombok_dir" ]]; then
    echo "Missing delombok sources for $module_path at $delombok_dir" >&2
    exit 1
  fi
  delombok_paths+=("$delombok_dir")
done

delombok_sourcepath="$(join_by : "${delombok_paths[@]}")"

# Re-run javadoc using delomboked sources to avoid Lombok annotation parser errors.
awk -v sp="$delombok_sourcepath" 'BEGIN{replace_next=0} {if(replace_next==1){print "\047" sp "\047"; replace_next=0; next} print; if($0=="-sourcepath"){replace_next=1}}' "$options_file" > "$patched_options_file"
run_logged "$delombok_javadoc_log" javadoc @"$patched_options_file" @"$packages_file"

# Javadoc jars were already built by the Maven command above.

rm -rf "$out_dir"
mkdir -p "$out_dir"

aggregate_dir="$repo_root/target/site/apidocs"
if [[ ! -f "$aggregate_dir/index.html" ]]; then
  echo "Missing aggregate javadocs at $aggregate_dir" >&2
  exit 1
fi
cp -R "$aggregate_dir" "$out_dir/aggregate"

index_file="$out_dir/index.html"
cat > "$index_file" <<'HTML'
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8" />
  <title>Local Javadocs</title>
</head>
<body>
  <h1>Local Javadocs</h1>
  <p><a href="aggregate/index.html">Aggregate API docs</a></p>
  <ul>
HTML

for i in "${!module_paths[@]}"; do
  module_path="${module_paths[$i]}"
  module_name="${module_names[$i]}"
  module_id="${module_path//\//-}"
  module_dir="$out_dir/$module_id"
  mkdir -p "$module_dir"

  jar_path=("$repo_root/$module_path"/target/*-javadoc.jar)
  if [[ ${#jar_path[@]} -eq 0 ]]; then
    echo "Missing javadoc jar for $module_path" >&2
    exit 1
  fi

  unzip -q "${jar_path[0]}" -d "$module_dir"
  printf '    <li><a href="%s/index.html">%s</a></li>\n' "$module_id" "$module_name" >> "$index_file"
done

cat >> "$index_file" <<'HTML'
  </ul>
</body>
</html>
HTML

echo "Local javadocs are available at: $out_dir/index.html"