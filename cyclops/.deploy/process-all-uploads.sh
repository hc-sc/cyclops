#!/bin/bash

if test -z "$1"; then
    echo "usage: process-all-uploads.sh uploads_folder" >&2
    echo "Must provide an uploads folder" >&2
    exit 1
fi

if [ ! -d "$1" ]; then
    echo "usage: process-all-uploads.sh uploads_folder" >&2
    echo "Uploads folder '$1' not found. Wrong path?" >&2
    exit 1
fi

pushd "$1"
for f in *; do
    curl -X POST "https://cyclops.happylittlecloud.ca:8080/extract" \
        -H  "accept: application/json" 
        -H  "Content-Type: application/json" 
        -d  "{\"filenames\":[\"$f\"]}"
done
popd
