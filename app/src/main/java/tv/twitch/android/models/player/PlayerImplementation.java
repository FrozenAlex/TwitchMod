package tv.twitch.android.models.player;

import tv.twitch.android.mod.settings.PrefManager;

public enum PlayerImplementation {
    Core("playercore", "b"),
    Exo2("exoplayer_2", "e2");

    public static final Companion Companion = null;
    public final String mName;
    private final String mTag;

    private PlayerImplementation(String str, String str2) {
        this.mName = str;
        this.mTag = str2;
    }

    public static final class Companion {
        public final PlayerImplementation getProviderForName(String str) { // TODO: __ADD
            if (PrefManager.isExoPlayerOn())
                return PlayerImplementation.Exo2;
            else
                return org(str);
        }

        public final PlayerImplementation org(String str) {  // TODO: __RENAME
            return null;
        }
    }
}
