package tv.twitch.a.e.l;

import tv.twitch.android.api.i1.f1;
import tv.twitch.android.mod.bridges.LoaderLS;
import tv.twitch.android.models.Playable;

// Source: ModelTheatreModeTracker
public class c {
    public c(f1 f1Var, Playable playable, Object pageViewTracker) {
        LoaderLS.getInstance().getHelper().newRequest(f1Var, playable); // TODO: __ADD_END
    }
}
