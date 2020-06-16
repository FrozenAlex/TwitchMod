package tv.twitch.android.core.adapters;


import java.util.List;

import tv.twitch.android.mod.utils.GifHelper;

/**
 * Source: TwitchAdapter
 */
public class d0 {
    private List<t> d;

    protected final List<t> U() {
        return this.d;
    }


    public void J(Object b0Var) {
        if (b0Var instanceof Object) {
            GifHelper.recycleObject(b0Var, false); // TODO: __INJECT_CODE
        }
    }

    public final void S() {
        GifHelper.recycleAdapterItems(this.d); // TODO: __INJECT_CODE
    }

    public void R(List<? extends t> list)  {}
}
