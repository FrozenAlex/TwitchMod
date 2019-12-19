package com.amazon.ads.video;


import java.util.Map;

public class RequestBuilder {
    public static final Object lock = new Object();

    public static RequestBuilder sInstance = null;


    public static RequestBuilder getInstance() {
        synchronized (lock) {
            if (sInstance == null)
                sInstance = new RequestBuilder();

            return sInstance;
        }
    }

    public enum AdBreakPosition {
        preroll,
        midroll,
        postroll
    }

    public enum ReturnType {
        vast2,
        vast3
    }



    public String buildAdTagURL() {
        return "null";
    }

    public RequestBuilder withAdBreakPosition(AdBreakPosition adBreakPosition2) {
        return this;
    }

    public RequestBuilder withBaseURL(String str) {
        return this;
    }

    public RequestBuilder withBundleId(String str) {
        return this;
    }

    public RequestBuilder withCountryCode(String str) {
        return this;
    }

    public RequestBuilder withDeviceCategory(String str) {
        return this;
    }

    public RequestBuilder withDevicePlatform(String str) {
        return this;
    }

    public RequestBuilder withDomain(String str) {
        return this;
    }

    public RequestBuilder withDuration(int i) {
        return this;
    }

    public RequestBuilder withGDPRConsentString(String str) {
        return this;
    }

    public RequestBuilder withGDPREnabled(Boolean bool) {
        return this;
    }

    public RequestBuilder withGDPRLog(Map<String, String> map) {
        return this;
    }

    public RequestBuilder withKeyValuePair(String str, String str2) {
        return this;
    }

    public RequestBuilder withProducerId(String str) {
        return this;
    }

    public RequestBuilder withProducerName(String str) {
        return this;
    }

    public RequestBuilder withPublisherId(String str) {
        return this;
    }

    public RequestBuilder withPublisherName(String str) {
        return this;
    }

    public RequestBuilder withReturnType(ReturnType returnType2) {
        return this;
    }

    public RequestBuilder withSlot(Slot slot) {
        return this;
    }

    public RequestBuilder withWindowSize(String str) {
        return this;
    }
}
