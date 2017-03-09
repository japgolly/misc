#!/bin/bash

find . -type f '!' '(' -path '*/target/*' -o -path '*/tmp/*' -o -name replace.sh ')' \
  | sort \
  | xargs egrep --color=auto -i 'bananas?|tempactivity|mycompanyname'

