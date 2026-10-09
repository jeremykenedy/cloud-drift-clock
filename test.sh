#!/usr/bin/env bash
set -euo pipefail
ROOT="$(cd "$(dirname "$0")" && pwd)"
"$ROOT/scripts/test-coverage.sh"
"$ROOT/scripts/test-python-coverage.sh"
