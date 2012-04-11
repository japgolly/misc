#!/bin/bash

. env_info.sh
thin start -p 8338 -e production
