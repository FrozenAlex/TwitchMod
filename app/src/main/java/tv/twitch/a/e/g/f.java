package tv.twitch.a.e.g;

import tv.twitch.android.mod.bridges.Hooks;

// Source: FollowedGamesFetcher
public class f extends tv.twitch.a.b.h.e {
    public final boolean j() { // TODO: __INJECT_METHOD
        return Hooks.hookFollowerFetcher(super.j());
    }
}
