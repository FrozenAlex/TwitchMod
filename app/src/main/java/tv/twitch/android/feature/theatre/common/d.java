package tv.twitch.android.feature.theatre.common;

import tv.twitch.android.mod.bridges.Hooks;


/**
 * Source: FloatingChatPresenter
 */
public class d {
    public final boolean org() { // TODO: __RENAME__l0
        return false;
    }

    public final boolean l0() { // TODO: __INJECT_METHOD
        if (Hooks.isFloatingChatEnabled())
            return true;

        return org();
    }
}
