package tv.twitch.android.sdk;

import tv.twitch.android.mod.utils.ChatUtils;
import tv.twitch.chat.ChatEmoticonSet;

// Source: ChatController
public class c0 {
    public ChatEmoticonSet[] k = null; // widget set

    public ChatEmoticonSet[] b() { // TODO: __REPLACE
        return ChatUtils.hookChatEmoticonSet(this.k);
    }
}