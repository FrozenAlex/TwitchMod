package tv.twitch.android.core.crashreporter;

import java.util.Arrays;

import tv.twitch.android.mod.utils.Logger;
import tv.twitch.android.util.LogArg;
import tv.twitch.android.util.LogTag;

// Source: CrashReporter
public class a {
    public final String a(int i, LogArg... logArgArr) {
        return "";
    }

    public final void c(int i, LogArg... logArgArr) { // TODO: __REPLACE_METHOD
        Logger.printStackTrace();
        Logger.error(a(i, Arrays.copyOf(logArgArr, logArgArr.length)));
    }

    public final void b(String str) { // TODO: __REPLACE_METHOD
        Logger.printStackTrace();
        Logger.error(str);
    }

    public final void a(LogTag logTag, int i, LogArg... logArgArr) { // TODO: __REPLACE_METHOD
        Logger.printStackTrace();
        Logger.error(a(i,  Arrays.copyOf(logArgArr, logArgArr.length)));
    }

    public final void a(Throwable th) { // TODO: __REPLACE_METHOD
        Logger.printStackTrace();
        Logger.error(th);
    }

    public final void a(String str, String str2) { // TODO: __REPLACE_METHOD
        Logger.printStackTrace();
        Logger.error(str2);
    }

    public final void a(String str) { // TODO: __REPLACE_METHOD
        Logger.printStackTrace();
        Logger.error(str);
    }

    public final void b(int i) {
        Logger.printStackTrace();
        Logger.error(Integer.toString(i));
    }
}