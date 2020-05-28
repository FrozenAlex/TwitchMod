package tv.twitch.android.shared.ui.menus;

import tv.twitch.android.shared.ui.menus.o.b;

public interface k {
    enum a { // TODO: __REPLACE_CLASS
        b(null),
        SmartFeed(null),
        c(null),
        d(null),
        InAppWhispers( null),
        InAppFriendRequests( null),
        e(null),
        f(  null),
        g(null),
        BackgroundAudio(null),
        h( null),
        i(null),
        j( null),
        k(null),
        l(null),
        ShowActivityFeedResubs(null),
        m( null),
        n( null),
        o(null),
        p( null),
        q(null),
        r( null),
        s(null),
        t( null),
        u( null),
        v( null),
        w( null),
        x( null),
        y( null),
        z(null),
        A( null),
        B( null),
        C(null),
        D(null),
        E( null),
        GameBroadcastingViewerCount( null),
        BttvEmotes( "MOD_EMOTES"),
        BttvEmotesPicker( "MOD_EMOTE_PICKER"),
        FloatingChat("MOD_FLOATING_CHAT"),
        Timestamps( "MOD_TIMESTAMPS"),
        Points("MOD_POINTS"),
        VideoDebugPanel( "MOD_VIDEO_DEBUG"),
        VolumeSwipe("MOD_SWIPE_VOLUME"),
        BrightnessSwipe( "MOD_SWIPE_BRIGHTNESS"),
        HideRecommendedStreams("MOD_DISABLE_RECOMMENDATIONS"),
        HideResumeWatchingStreams( "MOD_DISABLE_RESUME_WATCHING"),
        HideFollowedGames("MOD_DISABLE_FOLLOWED_GAMES"),
        HideDiscoverTab( "MOD_HIDE_NAVIGATION_DISCOVER"),
        HideEsportsTab("MOD_HIDE_NAVIGATION_ESPORTS"),
        RecentSearch( "MOD_DISABLE_RECENT_SEARCH"),
        DevMode( "MOD_DEV_MOD"),
        AutoPlay("MOD_DISABLE_AUTOPLAY"),
        CopyMsg( "MOD_COPY_MSG");

        private final String preferenceKey;

        a(String prefKey) {
            this.preferenceKey = prefKey;
        }

        public String getPreferenceKey() {
            return preferenceKey;
        }

    }

    void a(b bVar);

    void a(tv.twitch.android.shared.ui.menus.w.b bVar, boolean z);
}