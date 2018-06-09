#!/usr/bin/env bash
apt-get -y update
apt-get -y install openjdk-8-jdk

./gradlew bootJar --info --stacktrace