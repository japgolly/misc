#!/bin/bash

. env_info.sh
RACK_ENV=production puma -p 8338 -q
