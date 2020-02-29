package tv.twitch.a.e.f;

import tv.twitch.android.mod.bridges.LoaderLS;

// Source: ResumeWatchingVideosFetcher
public class w extends tv.twitch.a.b.h.e {
    public final boolean j() { // TODO: __ADD
        return super.j() && !LoaderLS.getInstance().getPrefManager().isDisableRecentWatching();
    }
}
