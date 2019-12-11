package tv.twitch.a.f.f;

import tv.twitch.a.c.h.d;
import tv.twitch.android.mod.settings.PrefManager;

// Source: RecommendedStreamsFetcher
public class q extends d {

    @Override
    public final boolean j() { // TODO: __ADD
        final boolean org = super.j();
        if (PrefManager.isDisRec())
            return false;

        return org;
    }
}
