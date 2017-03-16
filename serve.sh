#!/bin/bash
cd "$(dirname "$(readlink -e "$0")")/dist" || exit 1
exec python -m http.server 4000
