package tv.twitch.android.core.adapters;


import java.util.List;

import tv.twitch.android.adapters.a.b;
import tv.twitch.android.mod.utils.GifHelper;
import tv.twitch.android.mod.utils.Logger;

/**
 * Source: TwitchAdapter
 */
public class d0 {
    private List<t> d;

    protected final List<t> i() {
        return this.d;
    }


    public void c(Object b0Var) {
        if (b0Var instanceof Object) {
            GifHelper.recycle(b0Var, false); // TODO: __INJECT_CODE
        }
    }

    public final void h() {
        GifHelper.recycleAdapter(this.d); // TODO: __INJECT_CODE
    }
}
