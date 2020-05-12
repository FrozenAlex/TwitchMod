package tv.twitch.android.sdk;

import tv.twitch.android.mod.bridges.Hooks;
import tv.twitch.chat.ChatEmoticonSet;
import tv.twitch.chat.IChatAPIListener;

// Source: ChatController
public class z {
    public ChatEmoticonSet[] k = null; // widget set

    public ChatEmoticonSet[] b() { // TODO: __REPLACE_METHOD
        return Hooks.hookChatEmoticonSet(this.k);
    }
}