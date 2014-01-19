#!/bin/sh
SRC=$(ls src/ischemia/*.java)
mkdir -p bin
javac -d bin $SRC
cd bin
jar cfe ischemia.jar ischemia.Ischemia ischemia/*
mv ischemia.jar ..
cd ..
