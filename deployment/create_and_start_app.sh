#!/bin/bash

# shellcheck disable=SC2046
BASEDIR=$(dirname $(realpath "$0"))

docker-compose -f "${BASEDIR}/docker-compose.yml" up -d
