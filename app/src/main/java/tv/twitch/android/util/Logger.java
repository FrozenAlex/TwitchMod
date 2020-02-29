package tv.twitch.android.util;


public class Logger {
    private static final int MAX_LOG_LENGTH = 4000;
    private static final String TAG = "Twitch";

    public static void d(String str) {
        d(LogTag.DEFAULT, str);
    }

    public static void d(LogTag logTag, String str) {
        tv.twitch.android.mod.utils.Logger.debug(String.format("<%s> %s", logTag, str));
    }

    public static void d(LogTag logTag, String str, Throwable th) {
        tv.twitch.android.mod.utils.Logger.debug(String.format("<%s> %s", logTag, str));
        th.printStackTrace();
    }

    public static void dArgs(LogTag logTag, Object... objArr) {
    }

    public static void e(String str) {
        e(LogTag.DEFAULT, str);
    }

    public static void e(LogTag logTag, String str) {
        tv.twitch.android.mod.utils.Logger.error(String.format("<%s> %s", logTag, str));
    }

    public static void e(LogTag logTag, String str, Throwable th) {
        tv.twitch.android.mod.utils.Logger.info(String.format("<%s> %s", logTag, str));
        th.printStackTrace();
    }

    public static void i(String str) {
        i(LogTag.DEFAULT, str);
    }

    public static void i(LogTag logTag, String str) {
        tv.twitch.android.mod.utils.Logger.info(String.format("<%s> %s", logTag, str));
    }

    private static void recursiveD(String str, String str2) {
    }

    public static void stackTrace(Exception exc) {
        exc.printStackTrace();
    }

    public static void v(String str) {
        v(LogTag.DEFAULT, str);
    }

    public static void v(LogTag logTag, String str) {
        tv.twitch.android.mod.utils.Logger.info(String.format("<%s> %s", logTag, str));
    }

    public static void w(LogTag logTag, String str) {
        tv.twitch.android.mod.utils.Logger.warning(String.format("<%s> %s", logTag, str));
    }

    public static void wtf(String str) {
        wtf(LogTag.DEFAULT, str);
    }

    public static void wtf(LogTag logTag, String str) {
        tv.twitch.android.mod.utils.Logger.debug(String.format("<%s> %s", logTag, str));
    }

    public static void e(String str, Throwable th) {
        e(LogTag.DEFAULT, str, th);
    }

    public static void w(String str) {
        w(LogTag.DEFAULT, str);
    }

}
