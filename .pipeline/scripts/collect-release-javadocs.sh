#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)"
OUTPUT_DIR="${1:-$ROOT_DIR/target/release-javadocs}"

MODULES=(
  "core:core"
  "orchestration:orchestration"
  "core-services/document-grounding:document-grounding"
  "core-services/prompt-registry:prompt-registry"
  "foundation-models/openai:openai"
  "foundation-models/sap-rpt:sap-rpt"
)

MODULE_LIST="$(printf '%s\n' "${MODULES[@]}" | cut -d: -f1 | paste -sd, -)"

cd "$ROOT_DIR"

echo "[INFO] Generating release-profile Javadocs for modules: $MODULE_LIST"
mvn --batch-mode --no-transfer-progress --fail-at-end --show-version \
  -Drelease \
  -pl "$MODULE_LIST" \
  -am \
  javadoc:jar@javadoc-jar

rm -rf "$OUTPUT_DIR"
mkdir -p "$OUTPUT_DIR"

echo "[INFO] Collecting generated Javadoc JARs into: $OUTPUT_DIR"
for entry in "${MODULES[@]}"; do
  module="${entry%%:*}"
  artifact="${entry##*:}"
  jar_path="$ROOT_DIR/$module/target/$artifact-"*
  jar_file=$(compgen -G "$jar_path-javadoc.jar" | head -n 1 || true)

  if [[ -z "$jar_file" ]]; then
    echo "[ERROR] Missing Javadoc JAR for module '$module' (expected $artifact-*-javadoc.jar)." >&2
    exit 1
  fi

  cp "$jar_file" "$OUTPUT_DIR/"
  echo "[OK] $(basename "$jar_file")"
done

echo "[DONE] Javadoc bundle ready in: $OUTPUT_DIR"
