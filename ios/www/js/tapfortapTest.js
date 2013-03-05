var isShowingBottomBanner = false;

var toggleBottomBanner = function() {
    if(isShowingBottomBanner) {
        TapForTap.removeAdView();
        isShowingBottomBanner = false;
        return;
    }
    
    TapForTap.createAdView({}, function() {
    TapForTap.loadAds(function() {
      // successfully loaded ads
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
	TapForTap.initializeWithApiKey('4b4037be19c7bbc168e799fd044c5063');
};
