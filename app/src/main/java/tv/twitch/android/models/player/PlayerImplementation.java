package tv.twitch.android.models.player;


import tv.twitch.android.mod.bridges.Hooks;


public enum PlayerImplementation {
    Core("playercore", "c"),
    Exo2("exoplayer_2", "e2");

    PlayerImplementation(String str, String str2) {
    }

    public static final class Companion {
        public final PlayerImplementation getProviderForName(String str) {
            str = Hooks.hookPlayerProvider(str); // TODO: __HOOK_PARAM

            return null;
        }
    }
}
