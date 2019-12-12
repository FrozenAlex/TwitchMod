package tv.twitch.a.f.f;

import tv.twitch.a.c.h.d;
import tv.twitch.android.mod.settings.PrefManager;

// Source: FollowedGamesFetcher
public class f extends d {
    @Override
    public final boolean j() { // TODO: __ADD
        final boolean org = super.j();
        if (PrefManager.isDisableFollowedGames())
            return false;

        return org;
    }
}
