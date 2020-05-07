package tv.twitch.android.core.adapters;

import java.util.List;

import tv.twitch.android.mod.utils.GifHelper;

/**
 * Source: TwitchAdapter
 */
public class d0 {
    private List<t> d;

    /**
     * @return List of chat items
     */
    protected final List<t> i() {
        return null;
    }

    public final void h() {
        GifHelper.recycle(d);
        this.d.clear();

    }
}