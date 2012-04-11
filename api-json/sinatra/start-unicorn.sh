#!/bin/bash

. env_info.sh
unicorn -p 8338 -E production
