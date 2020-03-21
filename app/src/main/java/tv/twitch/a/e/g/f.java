package tv.twitch.a.e.g;

import tv.twitch.android.mod.bridges.LoaderLS;

// Source: FollowedGamesFetcher
public class f extends tv.twitch.a.b.h.e {
    public final boolean j() { // TODO: __ADD
        return super.j() && !LoaderLS.getInstance().getPrefManager().isDisableFollowedGames();
    }
}
