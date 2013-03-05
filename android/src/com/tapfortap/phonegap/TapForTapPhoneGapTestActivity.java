/* 
 * Licensed under the MIT license
 * http://htmlpreview.github.com/?https://github.com/tapfortap/Documentation/blob/master/License.html
 * Copyright (c) 2013 Tap for Tap
 */

package com.tapfortap.phonegap;

import org.apache.cordova.DroidGap;

import android.os.Bundle;

public class TapForTapPhoneGapTestActivity extends DroidGap {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadUrl("file:///android_asset/www/index.html");
    }
}

