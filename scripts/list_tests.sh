#!/bin/bash

# Знайти всі .java файли з класами, які містять @Test (тільки public)
find ./src/test/java -name "*.java" \
    | xargs grep -l '@Test' \
    | sed 's|./src/test/java/||' \
    | sed 's|/|.|g' \
    | sed 's|.java||' \
    | sort