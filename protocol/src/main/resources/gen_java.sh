#!/bin/bash

cd ../proto

for file in ./*
do
    echo process file: $file
    protoc --proto_path=./ --java_out=../java $file
done

