#!/bin/sh

VERSION="1.0.0"

cd "$(dirname $0)"

ANDROID_FILE=android/src/com/tapfortap/phonegap/TapForTapPhoneGapPlugin.java
IOS_FILE=ios/TapForTapPhoneGapPlugin/Plugins/TapForTapPhoneGapPlugin.m

# Update the Plugin Version numbers in the native files
sed -i '' "s/.*TapForTap.pluginVersion.*/        TapForTap.pluginVersion = \"$VERSION\";/" $ANDROID_FILE
sed -i '' "s/.*TapForTap performSelector: @selector(_setPluginVersion:).*/    [TapForTap performSelector: @selector(_setPluginVersion:) withObject: @\"$VERSION\"];/" $IOS_FILE

# Delete the old release
rm -rf release
mkdir -p release/iOS
mkdir -p release/Android

if [ -f update_native_libraries ];
then
	./update_native_libraries
fi

# Build Android
android/release.sh
cp -r android/release/* release/Android/

# Build iOS
ios/release.sh
cp -r ios/release/* release/ios/

# Check to make sure that the tapfortap.js files are the same
if diff release/ios/tapfortap.js release/android/tapfortap.js  ; then
  echo "The tapfortap.js files are the same!"
else
  echo "Oops you made a change in one tapfortap.js and forgot to change the other!"
  rm -rf release
  exit
fi

# Zip Android and iOS release
cd release
zip -r TapForTap-PhoneGap-Android-"$VERSION".zip Android
zip -r TapForTap-PhoneGap-iOS-"$VERSION".zip iOS
zip -r TapForTap-PhoneGap-"$VERSION".zip Android iOS

rm -rf Android
rm -rf iOS
