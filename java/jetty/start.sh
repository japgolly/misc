#!/bin/bash

mvn -v || exit 1
echo
mvn -D jetty.port=8338 jetty:run
