package tv.twitch.android.mod.settings;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import tv.twitch.android.mod.models.settings.EmotePickerView;
import tv.twitch.android.mod.models.settings.FollowView;
import tv.twitch.android.mod.models.settings.UserMessagesFiltering;
import tv.twitch.android.mod.models.settings.EmoteSize;
import tv.twitch.android.mod.models.settings.ExoPlayerSpeed;
import tv.twitch.android.mod.models.settings.Gifs;
import tv.twitch.android.mod.models.settings.MiniPlayerSize;
import tv.twitch.android.mod.models.settings.PlayerImpl;
import tv.twitch.android.mod.models.PreferenceItem;
import tv.twitch.android.mod.utils.Logger;


public class PrefManager implements SharedPreferences.OnSharedPreferenceChangeListener {
    public static final String PREF_KEY_EMOTES = "MOD_EMOTES";
    public static final String PREF_KEY_EMOTE_PICKER = "MOD_EMOTE_PICKER";
    public static final String PREF_KEY_TIMESTAMPS = "MOD_TIMESTAMPS";
    public static final String PREF_KEY_PLAYER = "MOD_PLAYER";
    public static final String PREF_KEY_COPY_MSG = "MOD_COPY_MSG";
    public static final String PREF_KEY_DISABLE_PLAYER_AUTOPLAY = "MOD_DISABLE_AUTOPLAY";
    public static final String PREF_KEY_DISABLE_RECENT_SEARCH = "MOD_DISABLE_RECENT_SEARCH";
    public static final String PREF_KEY_EXOPLAYER_SPEED = "MOD_EXOPLAYER_SPEED_NEW";
    public static final String PREF_KEY_MINIPLAYER_SIZE  = "MOD_MINIPLAYER_SIZE_NEW";
    public static final String PREF_KEY_EMOTE_SIZE = "MOD_EMOTE_SIZE_NEW";
    public static final String PREF_KEY_VIDEO_DEBUG = "MOD_VIDEO_DEBUG";
    public static final String PREF_KEY_SWIPE_VOLUME = "MOD_SWIPE_VOLUME";
    public static final String PREF_KEY_SWIPE_BRIGHTNESS = "MOD_SWIPE_BRIGHTNESS";
    public static final String PREF_KEY_DEV_MODE = "MOD_DEV_MOD";
    public static final String PREF_KEY_INTERCEPTOR = "MOD_INTERCEPTOR";
    public static final String PREF_KEY_HIDE_DISCOVER = "MOD_HIDE_NAVIGATION_DISCOVER";
    public static final String PREF_KEY_HIDE_ESPORTS = "MOD_HIDE_NAVIGATION_ESPORTS";
    public static final String PREF_KEY_FLOATING_CHAT = "MOD_FLOATING_CHAT";
    public static final String PREF_KEY_GIFS = "MOD_GIFS_STRATEGY";
    public static final String PREF_KEY_USER_MESSAGES_FILTERING = "MOD_USER_MESSAGES_FILTERING";
    public static final String PREF_KEY_EMOTE_PICKER_VIEW = "MOD_EMOTE_PICKER_VIEW";
    public static final String PREF_KEY_FOLLOW_VIEW = "MOD_FOLLOW_VIEW";

    public static final String PREF_KEY_TWITCH_DARK_THEME = "dark_theme_enabled";

    public static final String PREF_KEY_DISABLE_RECOMMENDATIONS = "MOD_DISABLE_RECOMMENDATIONS";
    public static final String PREF_KEY_DISABLE_FOLLOWED_GAMES = "MOD_DISABLE_FOLLOWED_GAMES";
    public static final String PREF_KEY_DISABLE_RECENT_WATCHING = "MOD_DISABLE_RESUME_WATCHING";

    private final SharedPreferences mPref;

    private EmoteSize mEmoteSize;
    private UserMessagesFiltering mUserMessagesFiltering;
    private Gifs mGifsStrategy;
    private boolean isDarkThemeEnabled;

    public PrefManager(Context context) {
        mPref = PreferenceManager.getDefaultSharedPreferences(context);
        mPref.registerOnSharedPreferenceChangeListener(this);

        initLocalPreferences();
    }

    public boolean isCopyMsgOn() {
        return getBoolean(PREF_KEY_COPY_MSG, false);
    }

    public boolean isEmotesOn() {
        return getBoolean(PREF_KEY_EMOTES, true);
    }

    public boolean isHookEmoticonSetOn() {
        return getBoolean(PREF_KEY_EMOTE_PICKER, true);
    }

    public boolean isTimestampsOn() {
        return getBoolean(PREF_KEY_TIMESTAMPS, false);
    }

    public PlayerImpl getPlayer() {
        return (PlayerImpl) getOrDefault(PlayerImpl.AUTO);
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

    public boolean isVolumeSwipeEnabled() {
        return getBoolean(PREF_KEY_SWIPE_VOLUME, false);
    }

    public boolean isBrightnessSwipeEnabled() {
        return getBoolean(PREF_KEY_SWIPE_BRIGHTNESS, false);
    }

    public boolean isDevModeOn() {
        return getBoolean(PREF_KEY_DEV_MODE, false);
    }

    public boolean isInterceptorOn() {
        return getBoolean(PREF_KEY_INTERCEPTOR, false);
    }

    public boolean isHideDiscoverTab() {
        return getBoolean(PREF_KEY_HIDE_DISCOVER, false);
    }

    public boolean isHideEsportsTab() {
        return getBoolean(PREF_KEY_HIDE_ESPORTS, false);
    }

    public boolean isFloatingChatEnabled() {
        return getBoolean(PREF_KEY_FLOATING_CHAT, false);
    }

    public EmoteSize getEmoteSize() {
        return mEmoteSize;
    }

    public UserMessagesFiltering getChatFiltering() {
        return mUserMessagesFiltering;
    }

    public ExoPlayerSpeed getExoplayerSpeed() {
        return (ExoPlayerSpeed) getOrDefault(ExoPlayerSpeed.DEFAULT);
    }

    private PreferenceItem getOrDefault(PreferenceItem preferenceItem) {
        return preferenceItem.getPreference(getString(preferenceItem.getPreferenceKey(), preferenceItem.getPreferenceValue()));
    }

    public MiniPlayerSize getMiniPlayerSize() {
        return (MiniPlayerSize) getOrDefault(MiniPlayerSize.DEFAULT);
    }

    public FollowView getFollowView() {
        return (FollowView) getOrDefault(FollowView.AUTO);
    }

    public EmotePickerView getEmotePickerView() {
        return (EmotePickerView) getOrDefault(EmotePickerView.AUTO);
    }

    public Gifs getGifsStrategy() {
        return mGifsStrategy;
    }

    public void setEmoteSize(EmoteSize emoteSize) {
        mEmoteSize = emoteSize;
    }

    public void setChatFiltering(UserMessagesFiltering userMessagesFiltering) {
        mUserMessagesFiltering = userMessagesFiltering;
    }

    public boolean isDarkThemeEnabled() {
        return isDarkThemeEnabled;
    }

    public void setGifs(Gifs gifs) {
        mGifsStrategy = gifs;
    }

    private void initLocalPreferences() {
        mEmoteSize = (EmoteSize) getOrDefault(EmoteSize.MEDIUM);
        mGifsStrategy = (Gifs) getOrDefault(Gifs.STATIC);
        mUserMessagesFiltering = (UserMessagesFiltering) getOrDefault(UserMessagesFiltering.DISABLED);
        isDarkThemeEnabled = getBoolean(PREF_KEY_TWITCH_DARK_THEME, false);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String preferenceKey) {
        if (sharedPreferences == null || TextUtils.isEmpty(preferenceKey)) {
            Logger.error("null");
            return;
        }

        switch (preferenceKey) {
            case PrefManager.PREF_KEY_EMOTE_SIZE:
                setEmoteSize((EmoteSize) getOrDefault(EmoteSize.MEDIUM));
                break;
            case PrefManager.PREF_KEY_GIFS:
                setGifs((Gifs) getOrDefault(Gifs.STATIC));
                break;
            case PrefManager.PREF_KEY_USER_MESSAGES_FILTERING:
                setChatFiltering((UserMessagesFiltering) getOrDefault(UserMessagesFiltering.DISABLED));
            case PrefManager.PREF_KEY_TWITCH_DARK_THEME:
                isDarkThemeEnabled = sharedPreferences.getBoolean(PREF_KEY_TWITCH_DARK_THEME, false);
            default:
                Logger.debug(preferenceKey);
                break;
        }
    }

    public synchronized void updateBoolean(String key, boolean val) {
        if (mPref == null) {
            Logger.error("mPref is null");
            return;
        }

        if (TextUtils.isEmpty(key)) {
            Logger.error("Empty key");
            return;
        }

        mPref.edit().putBoolean(key, val).apply();
    }

    public synchronized void updateString(String key, String val) {
        if (mPref == null) {
            Logger.error("mPref is null");
            return;
        }

        if (TextUtils.isEmpty(key)) {
            Logger.error("Empty key");
            return;
        }

        if (TextUtils.isEmpty(val)) {
            Logger.error("Empty val");
            return;
        }

        mPref.edit().putString(key, val).apply();
    }

    public boolean getBoolean(String key, boolean def) {
        if (mPref == null) {
            Logger.warning("mPref is null");
            return def;
        }

        return mPref.getBoolean(key, def);
    }

    public String getString(String key, String def) {
        if (mPref == null) {
            Logger.warning("mPref is null");
            return def;
        }

        return mPref.getString(key, def);
    }
}
