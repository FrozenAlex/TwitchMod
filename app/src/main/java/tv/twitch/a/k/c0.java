package tv.twitch.a.k;

import tv.twitch.android.mod.emotes.EmotesManager;
import tv.twitch.android.mod.utils.Helper;
import tv.twitch.chat.ChatEmoticonSet;

import static tv.twitch.android.mod.utils.Helper.injectEmotes;

// Source: ChatController
public class c0 {
    public ChatEmoticonSet[] o = null; // widget set



    // i2 - user id?
    // i3 - channel id
    private boolean a(int i2, int i3) {
        EmotesManager.getInstance().request(i3);
        Helper.getInstance().setCurrentChannel(i3);
        return false;
    }


    public ChatEmoticonSet[] c() {
        return injectEmotes(this.o); // TODO: add
    }


}