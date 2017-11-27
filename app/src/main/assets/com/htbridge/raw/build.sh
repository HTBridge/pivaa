#!/bin/bash

rm -rf *.dex
rm -rf *.class
rm -rf ExternalCode.jar

/usr/bin/javac -classpath /storage/android-sdk-linux/platforms/android-27/android.jar ExternalCode.java
/usr/bin/java -jar dx.jar --dex --output ExternalCode.jar ExternalCode.class

