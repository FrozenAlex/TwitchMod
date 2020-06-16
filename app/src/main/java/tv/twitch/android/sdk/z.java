package tv.twitch.android.sdk;

import tv.twitch.android.mod.bridges.Hooks;
import tv.twitch.chat.ChatEmoticonSet;

/**
 * Source: ChatController
 */
public class z {
    public ChatEmoticonSet[] k = null; // widget set

    public ChatEmoticonSet[] Q() { // TODO: __REPLACE_METHOD
        return getK();
    }

    // TODO: __REPLACE_DIRECT_CALL
    public ChatEmoticonSet[] getK() { // TODO: __INJECT_METHOD
        return Hooks.hookChatEmoticonSet(this.k);
    }
}