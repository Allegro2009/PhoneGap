/* 
 * Licensed under the MIT license
 * http://htmlpreview.github.com/?https://github.com/tapfortap/Documentation/blob/master/License.html
 * Copyright (c) 2013 Tap for Tap
 */

package com.tapfortap.phonegap;

import org.apache.cordova.api.Plugin;
import org.apache.cordova.api.PluginResult;
import org.apache.cordova.api.PluginResult.Status;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.location.Location;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.tapfortap.AdView;
import com.tapfortap.AppWall;
import com.tapfortap.Interstitial;
import com.tapfortap.TapForTap;

public class TapForTapPhoneGapPlugin extends Plugin {

    private static final String TAG = TapForTapPhoneGapPlugin.class.getSimpleName();

    private AdView adView;
    private LinearLayout rootLayout;

    @Override
    public PluginResult execute(String action, JSONArray arguments, String callbackId) {
        PluginResult result = null;

        Object firstArg = null;
        if (arguments.length() > 0) {
            try {
                firstArg = arguments.get(0);
            }
            catch (JSONException e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
                return new PluginResult(Status.ERROR);
            }
        }

        try {
            Log.d(TAG, "Action " + action + ", arguments: " + arguments);
            if (action.equals("initializeWithAPIKey")) {
                result = initialize((String) firstArg);
            }
            else if (action.equals("createAdView")) {
                result = createAdView((JSONObject) firstArg);
            }
            else if (action.equals("loadAds")) {
                result = loadAds((JSONObject) firstArg);
            }
            else if (action.equals("moveAdView")) {
                result = moveAdView((JSONObject) firstArg);
            }
            else if (action.equals("removeAdView")) {
                result = removeAdView();
            }
            else if (action.equals("prepareInterstitial")) {
                result = prepareInterstitial();
            }
            else if (action.equals("showInterstitial")) {
                result = showInterstitial();
            }
            else if (action.equals("prepareAppWall")) {
                result = prepareAppWall();
            }
            else if (action.equals("showAppWall")) {
                result = showAppWall();
            }
            else {
                Log.w(TAG, "Unknown action " + action + ", arguments: " + arguments);
            }
        }
        catch (JSONException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
            result = new PluginResult(Status.JSON_EXCEPTION);
        }

        return result;
    }

    private PluginResult initialize(String appId) {

        TapForTap.plugin = "phonegap";
        TapForTap.pluginVersion = "1.0.0";
        TapForTap.initialize(cordova.getActivity(), appId);
        return new PluginResult(Status.OK);
    }

    private PluginResult createAdView(JSONObject options) throws JSONException {
        if (adView == null) {
            adView = new AdView(cordova.getActivity());
            setAdOptions(options);
            getRootLayout(new Runnable() {
                @Override
                public void run() {
                    rootLayout.addView(adView);
                }
            });
        }
        return new PluginResult(Status.OK);
    }

    private PluginResult loadAds(JSONObject options) {
        if (adView != null) {
            adView.loadAds();
            return new PluginResult(Status.OK);
        }
        return new PluginResult(Status.ERROR, "Cannot load ads before creating an ad view");
    }

    private PluginResult moveAdView(JSONObject options) throws JSONException {
        if (adView != null) {
            adView.setLayoutParams(getLayoutParamsForOptions(options));
            return new PluginResult(Status.OK);
        }
        return new PluginResult(Status.ERROR, "Cannot move the ad view before creating it");
    }

    private PluginResult removeAdView() {
        if (adView != null) {
            getRootLayout(new Runnable() {
                @Override
                public void run() {
                    rootLayout.removeView(adView);
                    adView = null;
                }
            });
        }
        return new PluginResult(Status.OK);
    }

    private PluginResult prepareInterstitial() {
        Interstitial.prepare(cordova.getActivity());
        return new PluginResult(Status.OK);
    }

    private PluginResult showInterstitial() {
        Interstitial.show(cordova.getActivity());
        return new PluginResult(Status.OK);
    }

    private PluginResult prepareAppWall() {
        AppWall.prepare(cordova.getActivity());
        return new PluginResult(Status.OK);
    }

    private PluginResult showAppWall() {
        AppWall.show(cordova.getActivity());
        return new PluginResult(Status.OK);
    }


    private void setAdOptions(JSONObject options) throws JSONException {
        adView.setLayoutParams(getLayoutParamsForOptions(options));
        if (options.has("gender")) {
            String gender = options.getString("gender");
            if (gender.equals("male"))
                TapForTap.setGender(TapForTap.Gender.MALE);
            else if (gender.equals("female"))
                TapForTap.setGender(TapForTap.Gender.FEMALE);
            else
                TapForTap.setGender(TapForTap.Gender.NONE);
        }
        if (options.has("age")) {
            TapForTap.setAge(options.getInt("age"));
        }
        if (options.has("location")) {
            JSONObject locationObject = options.getJSONObject("location");
            Location location = new Location("user");
            location.setLatitude(locationObject.getDouble("latitude"));
            location.setLongitude(locationObject.getDouble("longitude"));
            TapForTap.setLocation(location);
        }
    }

    private LayoutParams getLayoutParamsForOptions(JSONObject options) {
        int widthPx = options.optInt("width", LayoutParams.MATCH_PARENT);
        int heightPx = options.optInt("height", 50);
        float widthDip;
        if (widthPx > 0) {
            widthDip = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, widthPx, cordova.getActivity().getResources().getDisplayMetrics());
        }
        else {
            widthDip = widthPx;
        }
        float heightDip = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, heightPx, cordova.getActivity().getResources().getDisplayMetrics());
        return new LayoutParams((int)widthDip, (int)heightDip);
    }

    private void getRootLayout(final Runnable r) {
        (cordova.getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (rootLayout == null) {
                    ViewGroup viewGroup = (ViewGroup)(cordova.getActivity()).findViewById(android.R.id.content);
                    rootLayout = (LinearLayout)viewGroup.getChildAt(0);
                }
                if (r != null) r.run();
            }
        });
    }
}
