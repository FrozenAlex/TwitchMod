package tv.twitch.android.mod.settings;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import tv.twitch.android.mod.models.Emote;
import tv.twitch.android.mod.utils.Logger;


public class PrefManager implements SharedPreferences.OnSharedPreferenceChangeListener {
    public static final String PREF_KEY_EMOTES = "MOD_EMOTES";
    public static final String PREF_KEY_DISABLE_GIFS = "MOD_DISABLE_GIFS2";
    public static final String PREF_KEY_SHOW_DEL_MESSAGES = "MOD_SHOW_DEL_MESSAGES";
    public static final String PREF_KEY_EMOTE_PICKER = "MOD_EMOTE_PICKER";
    public static final String PREF_KEY_POINTS = "MOD_POINTS";
    public static final String PREF_KEY_TIMESTAMPS = "MOD_TIMESTAMPS";
    public static final String PREF_KEY_EXOPLAYER = "MOD_EXOPLAYER";
    public static final String PREF_KEY_COPY_MSG = "MOD_COPY_MSG";
    public static final String PREF_KEY_DISABLE_PLAYER_AUTOPLAY = "MOD_DISABLE_AUTOPLAY";
    public static final String PREF_KEY_DISABLE_RECENT_SEARCH = "MOD_DISABLE_RECENT_SEARCH";
    public static final String PREF_KEY_FIX_BRIGHTNESS  = "MOD_FIX_BRIGHTNESS";
    public static final String PREF_KEY_EXOPLAYER_SPEED_LIST  = "MOD_EXOPLAYER_SPEED2";
    public static final String PREF_KEY_MINIPLAYER_SIZE  = "MOD_MINIPLAYER_SIZE";
    public static final String PREF_KEY_EMOTE_SIZE = "MOD_EMOTE_SIZE2";
    public static final String PREF_KEY_VIDEO_DEBUG = "MOD_VIDEO_DEBUG";
    public static final String PREF_KEY_ADBLOCK = "MOD_ADBLOCK";
    public static final String PREF_KEY_SWIPE_VOLUME = "MOD_SWIPE_VOLUME";
    public static final String PREF_KEY_SWIPE_BRIGHTNESS = "MOD_SWIPE_BRIGHTNESS";
    public static final String PREF_KEY_DEV_MODE = "MOD_DEV_MOD";
    public static final String PREF_KEY_HIDE_DISCOVER = "MOD_HIDE_NAVIGATION_DISCOVER";
    public static final String PREF_KEY_HIDE_ESPORTS = "MOD_HIDE_NAVIGATION_ESPORTS";
    public static final String PREF_KET_FLOATING_CHAT = "MOD_FLOATING_CHAT";
    public static final String PREF_KEY_TWITCH_DARK_THEME_ENABLED = "dark_theme_enabled";

    public static final String PREF_KEY_DISABLE_RECOMMENDATIONS = "MOD_DISABLE_RECOMMENDATIONS";
    public static final String PREF_KEY_DISABLE_FOLLOWED_GAMES = "MOD_DISABLE_FOLLOWED_GAMES";
    public static final String PREF_KEY_DISABLE_RECENT_WATCHING = "MOD_DISABLE_RESUME_WATCHING";

    private final SharedPreferences mPref;

    private Emote.Size mEmoteSize;

    public PrefManager(Context context) {
        mPref = PreferenceManager.getDefaultSharedPreferences(context);

        setEmoteSize(getString(PREF_KEY_EMOTE_SIZE, "MEDIUM"));
        mPref.registerOnSharedPreferenceChangeListener(this);
    }

    private boolean getBoolean(String key, boolean def) {
        if (mPref == null) {
            Logger.warning("mPref is null");
            return def;
        }

        return mPref.getBoolean(key, def);
    }

    private String getString(String key, String def) {
        if (mPref == null) {
            Logger.warning("mPref is null");
            return def;
        }

        return mPref.getString(key, def);
    }

    public boolean isAdblockOn() {
        return getBoolean(PREF_KEY_ADBLOCK, true);
    }

    public boolean isEmotesOn() {
        return getBoolean(PREF_KEY_EMOTES, true);
    }

    public boolean isDisableGifs() {
        return getBoolean(PREF_KEY_DISABLE_GIFS, true);
    }

    public boolean isPreventMsg() {
        return getBoolean(PREF_KEY_SHOW_DEL_MESSAGES, false);
    }

    public boolean isHookEmoticonSetOn() {
        return getBoolean(PREF_KEY_EMOTE_PICKER, true);
    }

    public boolean isClickerOn() {
        return getBoolean(PREF_KEY_POINTS, false);
    }

    public boolean isTimestampsOn() {
        return getBoolean(PREF_KEY_TIMESTAMPS, false);
    }

    public boolean isExoPlayerOn() {
        return getBoolean(PREF_KEY_EXOPLAYER, false);
    }

    public boolean isCopyMsgOn() {
        return getBoolean(PREF_KEY_COPY_MSG, false);
    }

    public boolean isDisableAutoplay() {
        return getBoolean(PREF_KEY_DISABLE_PLAYER_AUTOPLAY, false);
    }

    public boolean isShowVideoDebugPanel() {
        return getBoolean(PREF_KEY_VIDEO_DEBUG, false);
    }

    public boolean isDisableRecentSearch() {
        return getBoolean(PREF_KEY_DISABLE_RECENT_SEARCH, false);
    }

    public boolean isDisableRecentWatching() {
        return getBoolean(PREF_KEY_DISABLE_RECENT_WATCHING, false);
    }

    public boolean isDisableRecommendations() {
        return getBoolean(PREF_KEY_DISABLE_RECOMMENDATIONS, false);
    }

    public boolean isDisableFollowedGames() {
        return getBoolean(PREF_KEY_DISABLE_FOLLOWED_GAMES, false);
    }

    public boolean isDarkTheme() {
        return getBoolean(PREF_KEY_TWITCH_DARK_THEME_ENABLED, false);
    }

    public boolean isFixBrightness() {
        return getBoolean(PREF_KEY_FIX_BRIGHTNESS, true);
    }

    public float getExoplayerSpeed() {
        return Float.parseFloat(getString(PREF_KEY_EXOPLAYER_SPEED_LIST, "1.0"));
    }

    public float getMiniplayerSize() {
        return Float.parseFloat(getString(PREF_KEY_MINIPLAYER_SIZE, "1.0"));
    }

    public boolean isVolumeSwipeEnabled() {
        return getBoolean(PREF_KEY_SWIPE_VOLUME, false);
    }

    public boolean isBrightnessSwipeEnabled() {
        return getBoolean(PREF_KEY_SWIPE_BRIGHTNESS, false);
    }

    public boolean isDevModeOn() {
        return getBoolean(PREF_KEY_DEV_MODE, false);
    }

    public boolean isHideDiscoverTab() {
        return getBoolean(PREF_KEY_HIDE_DISCOVER, false);
    }

    public boolean isHideEsportsTab() {
        return getBoolean(PREF_KEY_HIDE_ESPORTS, false);
    }

    public boolean isFloatingChatEnabled() {
        return getBoolean(PREF_KET_FLOATING_CHAT, false);
    }

    private synchronized void setEmoteSize(String size) {
        mEmoteSize = Emote.Size.valueOf(size);
    }

    public Emote.Size getEmoteSize() {
        return mEmoteSize;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (sharedPreferences == null || TextUtils.isEmpty(key))
            return;

        switch (key) {
            case PREF_KEY_EMOTE_SIZE:
                setEmoteSize(sharedPreferences.getString(PREF_KEY_EMOTE_SIZE,"MEDIUM"));
                break;
        }
    }
}
