package io.branch.referral;

import android.content.Context;

// Source: PrefHelper
public class r {
    private static r sInstance = null;

    private r(Context context) {}

    public static r a(Context context) {
        if (sInstance == null) {
            sInstance = new r(context);
        }
        return sInstance;
    }

    public String o() {
        return "0";
    }
}
