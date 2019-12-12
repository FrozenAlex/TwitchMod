package tv.twitch.a.f.f;

import tv.twitch.a.c.h.d;
import tv.twitch.android.mod.settings.PrefManager;

// Source: ResumeWatchingVideosFetcher
public class s extends d {

    @Override
    public final boolean j() { // TODO: __ADD
        final boolean org = super.j();
        if (PrefManager.isDisableRecentWatching())
            return false;

        return org;
    }
}
