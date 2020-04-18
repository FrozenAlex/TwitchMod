package tv.twitch.a.e.l;

import tv.twitch.android.api.i1.g1;
import tv.twitch.android.mod.bridges.LoaderLS;
import tv.twitch.android.models.Playable;

// Source: ModelTheatreModeTracker
public class c {
    public c(g1 g1Var, Playable playable, Object pageViewTracker) {
        LoaderLS.getInstance().getHelper().newRequest(g1Var, playable); // TODO: __ADD_END
    }
}
