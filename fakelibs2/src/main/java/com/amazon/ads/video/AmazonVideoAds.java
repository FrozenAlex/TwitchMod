package com.amazon.ads.video;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.amazon.ads.video.analytics.Analytics;
import com.amazon.ads.video.error.AmazonVideoAdsError;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class AmazonVideoAds {
    public static final Object lock = new Object();

    public static AmazonVideoAds sInstance = null;


    public static AmazonVideoAds getInstance() {
        synchronized (lock) {
            if (sInstance == null)
                sInstance = new AmazonVideoAds();

            return sInstance;
        }
    }

    public interface AdsManager {
        void mute();

        void onPlayerStateChange(VideoPlayerState videoPlayerState);

        boolean openAndReportClickThrough();

        void pause();

        void playAds(PauseContentHandler pauseContentHandler, ResumeContentHandler resumeContentHandler, OnAdClickListener onAdClickListener, List<View> list);

        void reportClickThrough();

        void resume();

        void setVolume(float f);

        void unmute();
    }

    public interface AdsReadyHandler {
        void onAdsNotAvailable();

        void onAdsReady(AdsManager adsManager);
    }

    public interface IHttpClient {

        public enum HTTPMethod {
            POST,
            GET
        }

        String executeRequest(String str, HTTPMethod hTTPMethod, Boolean bool, HashMap<String, Object> hashMap, HashMap<String, Object> hashMap2, int i, OnErrorListener onErrorListener, Analytics analytics) throws IOException, InterruptedException;
    }

    public interface OnAdClickListener {
        void onAdClick();
    }

    public interface OnErrorListener {
        void onError(AmazonVideoAdsError amazonVideoAdsError, String str);
    }

    public interface OnTrackingEventListener {
        void onTrackingEvent(String str, AdInfo adInfo);
    }

    public interface PauseContentHandler {
        void pauseContent();
    }

    public interface ResumeContentHandler {
        void resumeContent();
    }

    public synchronized void initialize(Context context, String str) {
    }

    public void setOmEnabled(boolean z) {
    }

    public synchronized void onPause() {
    }

    public synchronized void onResume() {
    }

    public synchronized void playAdPod(String str, int i, Context context, ViewGroup viewGroup, AdsReadyHandler adsReadyHandler, OnErrorListener onErrorListener, OnTrackingEventListener onTrackingEventListener) {

    }

    public synchronized void fetchAds(String str, int i, Context context, ViewGroup viewGroup, AdsReadyHandler adsReadyHandler, OnErrorListener onErrorListener, OnTrackingEventListener onTrackingEventListener) {

    }
}