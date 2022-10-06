#! /bin/bash

if [ ! -d src/ ]
then
  printf "Please run the script from the root directory of the project.\n"
  exit 1
fi

if [ -d build/ ]
then
  rm -rf build/
fi

if [ -d env/ ]
then
  rm -rf env/
fi

if [ -d target/ ]
then
  rm -rf target/
fi

cd src/main/scala/antlr4 && rm *.java && rm *.tokens && rm *.interp
