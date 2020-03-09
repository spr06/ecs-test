#!/bin/sh

java -XX:+UseContainerSupport \
 -jar $(find / -maxdepth 1 -name 'test*.jar')