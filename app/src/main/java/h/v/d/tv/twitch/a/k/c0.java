package h.v.d.tv.twitch.a.k;

import tv.twitch.android.mod.emotes.EmotesManager;

public class c0 {
    // i2 - user id?
    // i3 - channel id
    private boolean a(int i2, int i3) {
        EmotesManager.getInstance().request(i3);
        return false;
    }
}