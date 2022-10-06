#! /bin/bash


if [ ! -d src/ ]
then
  printf "Please run the script from the root directory of the project.\n"
  exit 1
fi;

if [ ! -d env/ ]
then
  echo "----==== Downloading ANTLR4 Tools ====----"
  ./scripts/build_venv.sh
fi;

source env/bin/activate
cd src/main/scala/antlr4/ && antlr4 -no-listener -visitor MiniJava.g4