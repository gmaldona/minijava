#!/bin/bash

for a in *.java; do mv -- "$a" "${a%.java}.minijava"; done