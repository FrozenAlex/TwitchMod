package tv.twitch.a.k.x;

import tv.twitch.android.mod.bridges.Hooks;

/**
 * Source: VideoDebugConfig
 */
public class v {
    public final boolean org() { // TODO: __RENAME__a
        return false;
    }

    public final boolean a() { // TODO: __INJECT_METHOD
        return Hooks.hookVideoDebugPanel(org());
    }
}