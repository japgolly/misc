#!/bin/bash

find . -type f '!' '(' -path '*/target/*' -o -path '*/tmp/*' ')' \
  | sort \
  | xargs egrep --color=auto -i 'bananas?|tempactivity|mycompanyname'

