#!/bin/sh
chmod +x build.sh
# Fail the script on any error
set -e

# Run Maven build
mvn clean install