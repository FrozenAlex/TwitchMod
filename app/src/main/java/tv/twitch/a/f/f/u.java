package tv.twitch.a.f.f;

import tv.twitch.android.mod.bridges.LoaderLS;

// Source: RecommendedStreamsFetcher
public class u extends tv.twitch.a.c.h.e {
    public final boolean j() { // TODO: __ADD
        return super.j() && !LoaderLS.getInstance().getPrefManager().isDisableRecommendations();
    }
}
