#!/bin/bash

. env_info.sh
bundle exec thin start -p 8338 -e production
