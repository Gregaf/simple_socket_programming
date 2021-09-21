#!/bin/bash

port=5000

java Server.java $port > server_output.txt &
sleep 1
java Client.java localhost $port < input.txt  > client_output.txt &

