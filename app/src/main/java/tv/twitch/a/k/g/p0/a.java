package tv.twitch.a.k.g.p0;

import java.util.List;

import tv.twitch.android.core.adapters.d0;
import tv.twitch.android.core.adapters.t;
import tv.twitch.android.mod.utils.GifHelper;

/**
 * Source: ChannelChatAdapter
 */
public class a extends d0 {
    public void b() {
        GifHelper.recycleAdapter(i()); // TODO: __INJECT_CODE
    }

    public void a(List<t> list) {
        int size = 0;
        if (size > 0) {
            for (int i2 = 0; i2 < size; i2++) {
                i().remove(0);
            }
            GifHelper.recycleAdapterRange(i(), size); // TODO: __INJECT_CODE
            // d(0, size);
        }
    }
}
