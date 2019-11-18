package tv.twitch.android.mod.utils;

import android.content.Context;

import tv.twitch.android.mod.emotes.EmotesManager;
import tv.twitch.android.mod.settings.PrefManager;

public class Loader {
    public static void init(Context context) {
        if (context == null) {
            Logger.error("context is null");
            return;
        }
        PrefManager.init(context);
        EmotesManager.init();
    }
}
