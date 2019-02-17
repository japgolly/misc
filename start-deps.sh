#!/bin/bash
cd "$(dirname "$0")" || exit 1
#cd "$(dirname "$(readlink -e "$0")")" || exit 1
#[ $# -ne 1 ] && echo "Usage: $0 <xxx>" && exit 1
#tmp=/tmp/$(date +%Y%m%d-%H%M%S)-$$

docker run --rm -p 6379:6379 redis:5.0
