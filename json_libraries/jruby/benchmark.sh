#!/bin/bash

export WARMUPS=5
BENCHMARK='jruby --1.9 --server -Xcompile.mode=FORCE --fast ./benchmark.rb'

if [ $# -gt 0 ]; then
  $BENCHMARK "$@"
else
  $BENCHMARK json_gem
  $BENCHMARK json_pure
  $BENCHMARK oj

  # These are crazily slow. Reduce reps by factor of 10.
  export REPS=10000
  $BENCHMARK ok_json
  $BENCHMARK yajl
fi
