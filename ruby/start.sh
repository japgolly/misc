#!/bin/bash

BEXEC="bundle exec"

# Parse an optional <app dir> argument
if [ -d "$1" ]; then
  APP_DIR="$1"
  shift
else
  [ ! -f config.ru ] && bad_args=1
fi

# Parse and check args
if [ -n "$bad_args" -o $# -ne 1 ]; then
  echo "Usage: $(basename "$0") [<app dir>] <server>"
  echo
  echo "Examples:"
  echo "  $0 rails puma"
  echo "  cd sinatra && ../$(basename "$0") thin"
  exit 1
fi
server="$1"

# Note fullpath to current script for later
self="$(cd $(dirname "$0") && pwd)/$(basename "$0")"

# Enter app dir, display app/env info
[ -z "$APP_DIR" ] || cd "$APP_DIR" || exit 2
ruby -v
echo
bundle list || exit 3
echo

# Start server
case "$server" in
  thin)    $BEXEC thin start -p 8338 -e production ;;
  puma)    RACK_ENV=production $BEXEC puma -p 8338 -q ;;
  unicorn) $BEXEC unicorn -p 8338 -E production ;;
  goliath) ./start-goliath.sh ;;
  *)
    echo "Unsupported server type: $server"
    echo "Supported server types are: $(cat "$self" | egrep '\).*;;' | fgrep -v grep | sed 's/^ *//; s/).*$//' | sort | xargs echo)"
    exit 4
esac
