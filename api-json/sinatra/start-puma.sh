#!/bin/bash

. env_info.sh
RACK_ENV=production bundle exec puma -p 8338 -q
