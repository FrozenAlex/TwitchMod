package tv.twitch.android.util;


import android.util.Log;

public class Logger {
    private static final int MAX_LOG_LENGTH = 4000;
    private static final String TAG = "Twitch";

    public static void d(String str) {
        d(LogTag.DEFAULT, str);
    }

    public static void d(LogTag logTag, String str) {
        Log.d(logTag.value, str);
    }

    public static void d(LogTag logTag, String str, Throwable th) {
        Log.d(logTag.value, str);
        th.printStackTrace();
    }

    public static void dArgs(LogTag logTag, Object... objArr) {
        Log.d(logTag.value, "...");
    }

    public static void e(String str) {
        e(LogTag.DEFAULT, str);
    }

    public static void e(LogTag logTag, String str) {
        Log.e(logTag.value, str);
    }

    public static void e(LogTag logTag, String str, Throwable th) {
        Log.e(logTag.value, str);
        th.printStackTrace();
    }

    public static void i(String str) {
        i(LogTag.DEFAULT, str);
    }

    public static void i(LogTag logTag, String str) {
        Log.i(logTag.value, str);
    }

    private static void recursiveD(String str, String str2) {
        d(str);
        if (str2.length() > MAX_LOG_LENGTH) {
            str2.substring(0, MAX_LOG_LENGTH);
            recursiveD(str, str2.substring(MAX_LOG_LENGTH));
        }

    }

    public static void stackTrace(Exception exc) {
        exc.printStackTrace();
    }

    public static void v(String str) {
        v(LogTag.DEFAULT, str);
    }

    public static void v(LogTag logTag, String str) {
        Log.v(logTag.value, str);
    }

    public static void w(LogTag logTag, String str) {
        Log.w(logTag.value, str);
    }

    public static void wtf(String str) {
        wtf(LogTag.DEFAULT, str);
    }

    public static void wtf(LogTag logTag, String str) {
        Log.wtf(logTag.value, str);
    }

    public static void e(String str, Throwable th) {
        e(LogTag.DEFAULT, str, th);
    }

    public static void w(String str) {
        w(LogTag.DEFAULT, str);
    }

}
