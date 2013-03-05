/* 
 * Licensed under the MIT license
 * http://htmlpreview.github.com/?https://github.com/tapfortap/Documentation/blob/master/License.html
 * Copyright (c) 2013 Tap for Tap
 */

!function () {

  var cordova = (function() {
    if (typeof window.cordova !== 'undefined') {
      return window.cordova;
    }
    if (typeof Cordova !== 'undefined') {
      return Cordova;
    }
    if (typeof PhoneGap !== 'undefined') {
      return PhoneGap;
    }
    return {
      exec: function() {
        alert('PhoneGap not found');
      }
    }
  }());

  window.TapForTap = {

    initializeWithAPIKey: function(apiKey, successCallback, failureCallback) {
      return cordova.exec(successCallback, failureCallback, 'TapForTap', 'initializeWithAPIKey', [apiKey]);
    }

  , createAdView: function(options, successCallback, failureCallback) {
      return cordova.exec(successCallback, failureCallback, 'TapForTap', 'createAdView', [options]);
    }

  , loadAds: function(successCallback, failureCallback) {
      return cordova.exec(successCallback, failureCallback, 'TapForTap', 'loadAds', []);
    }

  , moveAdView: function(successCallback, failureCallback) {
      return cordova.exec(successCallback, failureCallback, 'TapForTap', 'moveAdView', []);
    }

  , removeAdView: function(options, successCallback, failureCallback) {
      return cordova.exec(successCallback, failureCallback, 'TapForTap', 'removeAdView', [options]);
    }

  , prepareInterstitial: function(options, successCallback, failureCallback) {
        return cordova.exec(successCallback, failureCallback, 'TapForTap', 'prepareInterstitial', [options]);
    }

  , showInterstitial: function(options, successCallback, failureCallback) {
          return cordova.exec(successCallback, failureCallback, 'TapForTap', 'showInterstitial', [options]);
    }

  , prepareAppWall: function(options, successCallback, failureCallback) {
        return cordova.exec(successCallback, failureCallback, 'TapForTap', 'prepareAppWall', [options]);
    }

  , showAppWall: function(options, successCallback, failureCallback) {
          return cordova.exec(successCallback, failureCallback, 'TapForTap', 'showAppWall', [options]);
    }
  };

}();
