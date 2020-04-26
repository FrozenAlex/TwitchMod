package tv.twitch.android.sdk;

import tv.twitch.android.mod.bridges.Hooks;
import tv.twitch.chat.ChatEmoticonSet;

// Source: ChatController
public class z {
    public ChatEmoticonSet[] k = null; // widget set

    public ChatEmoticonSet[] b() { // TODO: __REPLACE
        return Hooks.hookChatEmoticonSet(this.k);
    }
}