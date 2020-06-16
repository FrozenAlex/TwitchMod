package tv.twitch.android.feature.theatre.common;

import tv.twitch.android.mod.bridges.Hooks;


/**
 * Source: FloatingChatPresenter
 */
public class d {
    public final boolean org() { // TODO: __RENAME__h2
        return false;
    }

    public final boolean h2() { // TODO: __REPLACE_METHOD
        if (Hooks.isFloatingChatEnabled())
            return true;

        return org();
    }
}
