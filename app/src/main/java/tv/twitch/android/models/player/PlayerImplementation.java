package tv.twitch.android.models.player;

import android.text.TextUtils;

import tv.twitch.android.mod.bridges.LoaderLS;

public enum PlayerImplementation {
    Core("playercore", "c"),
    Exo2("exoplayer_2", "e2");

    public static final Companion Companion = null;

    private PlayerImplementation(String str, String str2) {
    }

    public static final class Companion {
        public final PlayerImplementation getProviderForName(String str) { // TODO: __ADD
            if (TextUtils.isEmpty(str))
                return org(str);

            if (LoaderLS.getInstance().getPrefManager().isExoPlayerOn())
                return PlayerImplementation.Exo2;
            else
                return org(str);
        }

        public final PlayerImplementation org(String str) {  // TODO: __RENAME
            return null;
        }
    }
}
