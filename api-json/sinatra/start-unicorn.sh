#!/bin/bash

. env_info.sh
bundle exec unicorn -p 8338 -E production
