package tv.twitch.a.e.f;

import tv.twitch.android.mod.bridges.LoaderLS;

// Source: RecommendedStreamsFetcher
public class u extends tv.twitch.a.b.h.e {
    public final boolean j() { // TODO: __ADD
        return super.j() && !LoaderLS.getInstance().getPrefManager().isDisableRecommendations();
    }
}
