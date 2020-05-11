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

    public void c(Object b0Var) {
        recycleGifs(b0Var); // TODO: __INJECT_CALL
    }

    private void recycleGifs(Object item) { // TODO: __INJECT_METHOD
        if (item instanceof tv.twitch.android.adapters.a.b.a) {
            tv.twitch.android.adapters.a.b.a view = (tv.twitch.android.adapters.a.b.a) item;

            GifHelper.recycle(view.E());
        }
    }
}