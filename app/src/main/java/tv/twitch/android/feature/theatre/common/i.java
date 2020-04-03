package tv.twitch.android.feature.theatre.common;

import tv.twitch.android.mod.bridges.LoaderLS;

// Source: MiniPlayerSize
public class i {

    // Source: b()
    public final int org() { // TODO: __RENAME
        return 0;
    }

    public final int b() {
        return (int) (org() * LoaderLS.getInstance().getPrefManager().getMiniplayerSize());
    }
}
