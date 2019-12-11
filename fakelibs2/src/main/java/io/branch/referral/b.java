package io.branch.referral;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;

import org.json.JSONObject;

// Source: Branch
public class b {
    public static b sInstance = null;

    public interface f {
        void a(JSONObject jSONObject, e eVar);
    }

    private static b d(Context context) {
        return new b(context.getApplicationContext());
    }

    private b(Context context) {}

    public boolean a(f fVar, Uri uri, Activity activity) {
        return true;
    }

    public static void a(Boolean bool) {}

    private static b a(Context context, boolean z2, String str) {
        if (sInstance == null) {
            sInstance = d(context);
        }

        return sInstance;
    }

    public static b a(Context context) {
        a(context, true, "");
        return sInstance;
    }

    public static b t() {
        return sInstance;
    }

    // TODO: test with empty JSON
    public JSONObject g() {
        return null;
    }

    public void a(String str, JSONObject jSONObject) {}

    public static b b(Context context) {
        return a(context, true,  null);
    }
}