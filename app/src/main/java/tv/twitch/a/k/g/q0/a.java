package tv.twitch.a.k.g.q0;

import java.util.List;

import tv.twitch.android.core.adapters.d0;
import tv.twitch.android.core.adapters.t;
import tv.twitch.android.mod.utils.GifHelper;

/**
 * Source: ChannelChatAdapter
 */
public class a extends d0 {
    public void g() {
        GifHelper.recycleAdapterItems(U()); // TODO: __INJECT_CODE
    }

    public void R(List<? extends t> list) {
        int size = 0;
        if (size > 0) {
            GifHelper.recycleAdapterItems(U(), size); // TODO: __INJECT_CODE
            for (int i2 = 0; i2 < size; i2++) {
                U().remove(0);
            }
            // d(0, size);
        }
    }
}
