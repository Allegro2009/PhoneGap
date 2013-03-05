/* 
 * Licensed under the MIT license
 * http://htmlpreview.github.com/?https://github.com/tapfortap/Documentation/blob/master/License.html
 * Copyright (c) 2013 Tap for Tap
 */

var isShowingBottomBanner = false;

var toggleBottomBanner = function() {
    if(isShowingBottomBanner) {
        TapForTap.removeAdView();
        isShowingBottomBanner = false;
        return;
    }
    
    TapForTap.createAdView({}, function() {
    TapForTap.loadAds(function() {
    }, function(e) {
      console.error('error loading ads: ', e);
    });
  }, function(e) {
    console.error('error creating ad view: ', e);
  });
    isShowingBottomBanner = true;
};

var showInterstitial = function() {
    TapForTap.showInterstitial();
};

var showAppWall = function() {
    TapForTap.showAppWall();
};

function init() {
    document.addEventListener("deviceready", deviceInfo, true);
};

var deviceInfo = function() {
	TapForTap.initializeWithAPIKey('YOUR API KEY HERE');
};
