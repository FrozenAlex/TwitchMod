package tv.twitch.android.mod.utils;

import android.content.Context;

import tv.twitch.android.mod.badges.BadgeManager;
import tv.twitch.android.mod.emotes.EmoteManager;
import tv.twitch.android.mod.settings.PrefManager;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class Loader {
    public static void init(Context context) {
        PrefManager.init(context);
        EmoteManager.getInstance();
        BadgeManager.getInstance();
    }
}
