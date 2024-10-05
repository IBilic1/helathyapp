#!/bin/bash

app_name=$1
image=$2
namespace=$3

oc create deployment $app_name --image=$app_name:main -n $namespace

if [[ "$?" -eq 0 ]]; then
    oc set image deployment/$app_name $app_name=$image
    oc rollout restart deployment/$app_name
else
    oc new-app $image --name $app_name -n $namespace
fi
