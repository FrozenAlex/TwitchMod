package tv.twitch.android.core.crashreporter;

import android.text.TextUtils;
import android.util.Log;

/**
 * Source: CrashReporter
 */
public class a {
    private void test() {
        Log("KEKW");
        Log("KEKW", "LULW");
        Log(0, "KEKW", "LULW");
        Log(new Exception());
        Log("KEKW", true);

    }
    private static void Log(String what) {
        if (TextUtils.isEmpty(what)) {
            return;
        }
        Log.d("CRASHLYTICS", "what==" + what);
    }

    private static void Log(String tag, String what) {
        if (TextUtils.isEmpty(what)) {
            return;
        }
        if (TextUtils.isEmpty(tag)) {
            Log.d("CRASHLYTICS", "tag==null");
            return;
        }
        Log.d("CRASHLYTICS", "tag==" + tag + ", what==" + what);
    }

    private static void Log(String what, boolean z) {
        if (TextUtils.isEmpty(what)) {
            return;
        }
        Log.d("CRASHLYTICS", "what==" + what + ", z=" + z);
    }

    private static void Log(Throwable throwable) {
        if (throwable == null) {
            return;
        }
        Log.d("CRASHLYTICS", "th==" + throwable.toString());
    }

    private static void Log(int i, String tag, String what) {
        if (TextUtils.isEmpty(what)) {
            return;
        }
        if (TextUtils.isEmpty(tag)) {
            return;
        }
        Log.d("CRASHLYTICS", "i==" + i + ", tag==" + tag + ", what==" + what);
    }
}
