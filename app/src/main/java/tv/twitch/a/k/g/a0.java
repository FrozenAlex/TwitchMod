package tv.twitch.a.k.g;


import java.util.ArrayList;
import java.util.List;

import tv.twitch.android.mod.bridges.Hooks;
import tv.twitch.chat.ChatLiveMessage;
import tv.twitch.chat.ChatMessageInfo;

/**
 * Source: LiveChatSource
 */
public class a0 {
    public final void r(int i2, List<? extends ChatLiveMessage> list) {
        String u = "";
        list = Hooks.hookLiveMessages(list, u); // TODO: __HOOK

        //   move-object v0, p0
        //   move-object v10, p2
        //   iget-object v1, v0, Ltv/twitch/a/k/g/a0;->n:Ltv/twitch/a/b/n/a;
        //   invoke-virtual {v1}, Ltv/twitch/a/b/n/a;->y()Ljava/lang/String;
        //   move-result-object v1
        //   invoke-static {v10, v1}, Ltv/twitch/android/mod/bridges/Hooks;->hookLiveMessages(Ljava/util/List;Ljava/lang/String;)Ljava/util/List;
        //   move-result-object p2
        //   move-object v10, p2
    }
}