#!/bin/bash

PROJECT_ROOT=$(pwd)

echo $PROJECT_ROOT

./build-front.sh
cd $PROJECT_ROOT
./mvnw clean install