#! /bin/bash

if [ ! -d src/ ]
then
  printf "Please run the script from the root directory of the project.\n"
  exit 1
fi;

python3 -m venv env
source env/bin/activate
python3 -m pip install --upgrade pip
pip install -r requirements.txt