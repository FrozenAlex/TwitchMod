package tv.twitch.a.k.w;

import tv.twitch.android.mod.bridges.LoaderLS;

// Source: VideoDebugConfig
public class v {

    public final boolean org() { // TODO: __RENAME
        return false;
    }

    public final boolean a() { // TODO: __ADD
        boolean org = org();
        if (org)
            return org;

        return LoaderLS.getInstance().getPrefManager().isShowVideoDebugPanel();
    }
}