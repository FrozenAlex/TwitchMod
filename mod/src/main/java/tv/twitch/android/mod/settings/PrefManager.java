package tv.twitch.android.mod.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import tv.twitch.android.mod.utils.Logger;

public class PrefManager {
    private static final String PREF_KEY_EMOTES = "MOD_EMOTES";
    private static final String PREF_KEY_DISABLE_GIFS = "MOD_DISABLE_GIFS";
    private static final String PREF_KEY_SHOW_DEL_MESSAGES = "MOD_SHOW_DEL_MESSAGES";
    private static final String PREF_KEY_DONT_LOAD_GIFS = "MOD_DONT_LOAD_GIFS";
    private static final String PREF_KEY_EMOTE_PICKER = "MOD_EMOTE_PICKER";
    private static final String PREF_KEY_POINTS = "MOD_POINTS";
    private static final String PREF_KEY_TIMESTAMPS = "MOD_TIMESTAMPS";
    private static final String PREF_KEY_DISABLE_ADS = "MOD_DISABLE_ADS";
    private static final String PREF_KEY_EXOPLAYER = "MOD_EXOPLAYER";
    private static final String PREF_KEY_COPY_MSG = "MOD_COPY_MSG";
    private static final String PREF_KEY_DISABLE_PLAYER_AUTOPLAY = "MOD_DISABLE_AUTOPLAY";
    private static final String PREF_KEY_DISABLE_RECENT_SEARCH = "MOD_DISABLE_RECENT_SEARCH";

    private static final String PREF_KEY_DISABLE_RECOMMENDATIONS = "MOD_DISABLE_RECOMMENDATIONS";
    private static final String PREF_KEY_DISABLE_RECENT_WATCHING = "MOD_DISABLE_RESUME_WATCHING";
    private static final String PREF_KEY_DISABLE_FOLLOWED_CHANNELS = "MOD_DISABLE_FOLLOWED_CHANNELS";
    private static final String PREF_KEY_DISABLE_FOLLOWED_STREAMS = "MOD_DISABLE_FOLLOWED_STREAMS";
    private static final String PREF_KEY_DISABLE_FOLLOWED_GAMES = "MOD_DISABLE_FOLLOWED_GAMES";

    private static SharedPreferences mPref;

    public static synchronized void init(Context context) {
        if (context == null) {
            Logger.error("context is null");
            return;
        }
        if (mPref == null) {
            mPref = PreferenceManager.getDefaultSharedPreferences(context);
        }
    }

    private PrefManager() {
    }

    private static boolean getBoolean(String key, boolean def) {
        if (mPref == null) {
            Logger.warning("mPref is null");
            return def;
        }

        return mPref.getBoolean(key, def);
    }

    public static boolean isEmotesOn() {
        return getBoolean(PREF_KEY_EMOTES, false);
    }

    public static boolean isDisableGifs() {
        return getBoolean(PREF_KEY_DISABLE_GIFS, false);
    }

    public static boolean isDontLoadGifs() {
        return getBoolean(PREF_KEY_DONT_LOAD_GIFS, false);
    }

    public static boolean isPreventMsg() {
        return getBoolean(PREF_KEY_SHOW_DEL_MESSAGES, false);
    }

    public static boolean isHookEmoticonSetOn() {
        return getBoolean(PREF_KEY_EMOTE_PICKER, false);
    }

    public static boolean isClickerOn() {
        return getBoolean(PREF_KEY_POINTS, false);
    }

    public static boolean isTimestampsOn() {
        return getBoolean(PREF_KEY_TIMESTAMPS, false);
    }

    public static boolean isAdblockOn() {
        return getBoolean(PREF_KEY_DISABLE_ADS, true);
    }

    public static boolean isExoPlayerOn() {
        return getBoolean(PREF_KEY_EXOPLAYER, false);
    }

    public static boolean isCopyMsgOn() {
        return getBoolean(PREF_KEY_COPY_MSG, false);
    }

    public static boolean isDisableAutoplay() {
        return getBoolean(PREF_KEY_DISABLE_PLAYER_AUTOPLAY, false);
    }

    public static boolean isDisableRecentSearch() {
        return getBoolean(PREF_KEY_DISABLE_RECENT_SEARCH, false);
    }

    public static boolean isDisableRecentWatching() {
        return getBoolean(PREF_KEY_DISABLE_RECENT_WATCHING, false);
    }

    public static boolean isDisableRecommendations() {
        return getBoolean(PREF_KEY_DISABLE_RECOMMENDATIONS, false);
    }

    public static boolean isDisableFollowedGames() {
        return getBoolean(PREF_KEY_DISABLE_FOLLOWED_GAMES, false);
    }

    public static boolean isDisableFollowedStreams() {
        return getBoolean(PREF_KEY_DISABLE_FOLLOWED_STREAMS, false);
    }
}
