## CI Scripts

The files here are used by the GitHub Actions workflows, but can also be used locally.
Their purpose is to improve the pipeline UX by parsing result files and printing results in a readable way.

## Local Javadoc Bundle

Use `collect-release-javadocs.sh` to generate per-module Javadoc JARs for the release modules and copy them into one folder for manual upload.

```bash
./.pipeline/scripts/collect-release-javadocs.sh
```

Optional custom output directory:

```bash
./.pipeline/scripts/collect-release-javadocs.sh /absolute/path/to/output
```

Default output: `target/release-javadocs`.