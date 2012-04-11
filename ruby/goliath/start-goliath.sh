#!/bin/bash

echo "Starting... (no futher output - just assume its up)"
./service.rb -e production -p 8338 #-sv
