package tv.twitch.android.mod.utils;

import android.util.Log;

import java.util.Arrays;


public class Logger {
    public static void error(String msg) {
        Log.e(getTag(), msg);
    }

    public static void error(Throwable th) {
        Log.e(getTag(), Log.getStackTraceString(th));
    }

    public static void printStackTrace() {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        StringBuilder sb = new StringBuilder();

        for (StackTraceElement element : Arrays.copyOfRange(elements, 4, elements.length)) {
            sb.append(element.toString()).append(System.lineSeparator());
        }

        Log.d("STACK", sb.toString());
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
        return className + "->" + methodName;
    }
}
