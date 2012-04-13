#!/bin/bash

export WARMUPS=5
BENCHMARK='jruby --1.9 -Xcompile.mode=FORCE --fast ./benchmark.rb'

if [ $# -gt 0 ]; then
  $BENCHMARK "$@"
else
  for f in json_gem json_pure oj yajl; do $BENCHMARK $f; done
fi
