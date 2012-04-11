#!/bin/bash

echo "Starting... (no futher output - just assume its up)"
jruby ./service.rb -e production -p 8338 #-sv
