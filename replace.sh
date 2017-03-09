#!/bin/bash

if [ $# -ne 1 ]; then
  echo "Usage: $0 <regex>"
  echo
  echo "Examples:"
  echo "  $0 's/Bananas/Apples/g'"
  echo "  $0 's/Bananas/Apples/g; s/bananas/apples/g; s/mycompanyname/fruitman/g'"
  echo
  exit 1
fi

find . -type f '!' '(' -path '*/target/*' -o -path '*/tmp/*' -o -name "$(basename "$0")" ')' \
  -exec perl -pi -e "$1" {} +

