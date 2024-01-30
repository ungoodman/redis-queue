#!/bin/bash

# Set the curl command
curl_command="curl -X 'POST' 'http://localhost:8080/v1/report/generate' -H 'accept: */*' -H 'Content-Type: application/json' -d '{ \"reportId\": \"string\", \"requestedBy\": \"test\" }'"

# Set the number of times you want to execute the command
num_requests=20

# Loop to execute the curl command multiple times
for ((i=0; i<$num_requests; i++)); do
    echo "Executing request $i"
    eval $curl_command
    echo "----------------------------------"
done
