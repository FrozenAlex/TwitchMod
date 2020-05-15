package tv.twitch.android.util;


import tv.twitch.android.mod.bridges.Hooks;

public class BuildConfigUtil {
    public final boolean isDebugConfigEnabled() { // TODO: __REPLACE_METHOD
        return Hooks.isDevModeOn();
    }

    public final boolean shouldShowDebugOptions(boolean z) { // TODO: __REPLACE_METHOD
        return Hooks.isDevModeOn();
    }

    public final boolean isAlpha() { // TODO: __REPLACE_METHOD
        return Hooks.isDevModeOn();
    }
}
