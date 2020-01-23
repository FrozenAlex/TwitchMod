package com.google.android.gms.ads.identifier;

import android.content.Context;

import java.io.IOException;

public class AdvertisingIdClient {

    public static Info getAdvertisingIdInfo(Context context) {
        return new Info();
    }

    public static void setShouldSkipGmsCoreVersionCheck(boolean z) {}

    public static final class Info {
        public Info() {
        }
        public Info(String str, boolean z) {
        }

        public final String getId() {
            return "0";
        }

        public final boolean isLimitAdTrackingEnabled() {
            return false;
        }

        public final String toString() {
            return "";
        }
    }
}
