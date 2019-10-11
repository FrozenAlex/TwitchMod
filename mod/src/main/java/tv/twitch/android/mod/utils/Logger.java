package tv.twitch.android.mod.utils;

import android.util.Log;

public class Logger {
    public static void error(String msg) {
        Log.e(getTag(), msg);
    }

    public static void warning(String msg) {
        Log.w(getTag(), msg);
    }

    public static void info(String msg) {
        Log.i(getTag(), msg);
    }

    public static void debug(String msg) {
        Log.d(getTag(), msg);
    }

    private static String getTag() {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[4];
        String className = stackTraceElement.getClassName();
        String methodName = stackTraceElement.getMethodName();
        return String.format("%s->%s", className, methodName);
    }
}
