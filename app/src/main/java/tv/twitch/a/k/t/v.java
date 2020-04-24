package tv.twitch.a.k.t;

import tv.twitch.android.mod.bridges.LoaderLS;

// Source: VideoDebugConfig
public class v {

    public final boolean org() { // TODO: __RENAME
        return false;
    }

    public final boolean a() { // TODO: __REPLACE
        boolean org = org();
        if (org)
            return org;

        return LoaderLS.getInstance().getPrefManager().isShowVideoDebugPanel();
    }
}