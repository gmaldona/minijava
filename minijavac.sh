#!/bin/bash
FILENAME="$1"

sbt "runMain Compiler $FILENAME"