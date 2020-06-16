package tv.twitch.a.k.g.l1;


import tv.twitch.android.mod.bridges.Hooks;
import tv.twitch.android.models.channel.ChannelInfo;

/**
 * Source: ChatConnectionController
 */
public class a {
    private final void n2(ChannelInfo channelInfo) {
        Hooks.requestEmotes(channelInfo); // TODO: __INJECT_CODE
    }
}
