#!/bin/bash

./collect-tainted.sh infer-out/ | jq '.[] | .passthroughs | unique | .[] | .method,."src-distance",."sink-distance"' | tr '\n' , | sed 's/,/\n/3;P;D' | sed 's/\"//g' | sed 's/\./,/g'
