#! /bin/bash

if [ ! -d src/ ]
then
  printf "Please run the script from the root directory of the project.\n"
  exit 1
fi;


cd src/main/scala/antlr4 && rm -v !("MiniJava.g4")