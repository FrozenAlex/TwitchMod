package tv.twitch.android.shared.ui.menus;

import tv.twitch.android.shared.ui.menus.k.b;

public interface j {
    enum a { // TODO: __REPLACE_CLASS
        b(null), // 0
        SmartFeed(null), // 1
        c(null), // 2
        d(null), // 3
        InAppWhispers( null), // 4
        InAppFriendRequests( null), // 5
        e(null), // 6
        f(  null), // 7
        g(null), // 8
        BackgroundAudio(null), // 9
        h( null), // 10
        i(null), // 11
        j( null), // 12
        k(null), // 13
        l(null), // 14
        ShowActivityFeedResubs(null), // 15
        m( null), // 16
        n( null), // 17
        o(null), // 18
        p( null), // 19
        q(null), // 20
        r( null), // 21
        s(null), // 22
        t( null), // 23
        u( null), // 24
        v( null), // 25
        w( null), // 26
        x( null), // 27
        y( null), // 28
        z(null), // 29
        A( null), // 30
        B( null), // 31
        C(null), // 32
        D(null), // 33
        E( null), // 34
        GameBroadcastingViewerCount( null), // 35
        BttvEmotes( "MOD_EMOTES"),
        BttvEmotesPicker( "MOD_EMOTE_PICKER"),
        FloatingChat("MOD_FLOATING_CHAT"),
        Timestamps( "MOD_TIMESTAMPS"),
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
        CopyMsg( "MOD_COPY_MSG"),
        Interceptor( "MOD_INTERCEPTOR");

        private final String preferenceKey;

        a(String prefKey) {
            this.preferenceKey = prefKey;
        }

        public String getPreferenceKey() {
            return preferenceKey;
        }

    }

    void a(b bVar);

    void a(tv.twitch.android.shared.ui.menus.s.b bVar, boolean z);
}