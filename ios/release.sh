#!/bin/sh

cd "$(dirname $0)"

rm -rf release
mkdir release
cp -r TapForTap release/TapForTap
cp TapForTapPhoneGapPlugin/Plugins/TapForTapPhoneGapPlugin.h release/
cp TapForTapPhoneGapPlugin/Plugins/TapForTapPhoneGapPlugin.m release/
cp www/js/tapfortap.js release/
