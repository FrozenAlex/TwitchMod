package tv.twitch.android.feature.theatre.common;

import tv.twitch.android.mod.bridges.Hooks;

// Source: MiniPlayerSize
public class i {

    // Source: b()
    public final int org() { // TODO: __RENAME__b
        return 0;
    }

    public final int b() { // TODO: __INJECT_METHOD
        return Hooks.hookMiniplayerSize(org());
    }
}
