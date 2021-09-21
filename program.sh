#!/bin/bash

port=5000

>error_log.txt

java Server.java $port > server_output.txt 2>> error_log.txt &
sleep 2
cat input.txt | java Client.java localhost $port > client_output.txt 2>> error_log.txt &

jobs

