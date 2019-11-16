package tv.twitch.android.mod.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import tv.twitch.android.mod.utils.Logger;


public class PrefManager {
    private static final String PREF_KEY_EMOTES = "MOD_EMOTES";
    private static final String PREF_KEY_DISABLE_GIFS = "MOD_DISABLE_GIFS";
    private static final String PREF_KEY_SHOW_DEL_MESSAGES = "MOD_SHOW_DEL_MESSAGES";
    private static final String PREF_KEY_EMOTE_PICKER = "MOD_EMOTE_PICKER";
    private static final String PREF_KEY_POINTS = "MOD_POINTS";
    private static final String PREF_KEY_TIMESTAMPS = "MOD_TIMESTAMPS";
    private static final String PREF_KEY_EMOTE_TOAST = "MOD_EMOTE_TOAST";
    private static final String PREF_KEY_DISABLE_ADS = "MOD_DISABLE_ADS";
    private static final String PREF_KEY_EXOPLAYER = "MOD_EXOPLAYER";
    private static final String PREF_KEY_STAT = "MOD_STAT";
    private static final String PREF_KEY_DISABLE_RECOMMENDATIONS = "MOD_STAT";

    private static SharedPreferences mPref;

    public static synchronized void init(Context context) {
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

    public static boolean isPreventMsg() {
        return getBoolean(PREF_KEY_SHOW_DEL_MESSAGES, false);
    }

    public static boolean isEmotePickerOn() {
        return getBoolean(PREF_KEY_EMOTE_PICKER, false);
    }

    public static boolean isClickerOn() {
        return getBoolean(PREF_KEY_POINTS, false);
    }

    public static boolean isTimestampsOn() {
        return getBoolean(PREF_KEY_TIMESTAMPS, false);
    }

    public static boolean isEToastOn() {
        return getBoolean(PREF_KEY_EMOTE_TOAST, false);
    }

    public static boolean isAdblockOn() {
        return getBoolean(PREF_KEY_DISABLE_ADS, true);
    }

    public static boolean isExoPlayerOn() {
        return getBoolean(PREF_KEY_EXOPLAYER, false);
    }

    public static boolean isShowStatPanel() {
        return getBoolean(PREF_KEY_STAT, false);
    }

    public static boolean isDisRec() {
        return getBoolean(PREF_KEY_DISABLE_RECOMMENDATIONS, false);
    }
}
