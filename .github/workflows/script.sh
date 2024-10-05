#!/bin/bash

app_name=$1
image=$2
namespace=$3

deploy=`oc get deployment $app_name`
if oc get deployment "$app_name" -n "$namespace" >/dev/null 2>&1; then
  echo "Deployment '$app_name' already exists in namespace '$namespace'."
else
  echo "Creating deployment '$app_name' in namespace '$namespace'..."
  oc create deployment "$app_name" --image=my-image:latest -n "$namespace"

if [[ "$?" -eq 0 ]]; then
    oc set image deployment/$app_name $app_name=$image
    oc rollout restart deployment/$app_name
else
    oc new-app $image --name $app_name -n $namespace
fi
