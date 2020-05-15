package tv.twitch.a.k.g.h1;


import tv.twitch.android.mod.bridges.Hooks;
import tv.twitch.android.models.channel.ChannelInfo;

/**
 * Source: ChatConnectionController
 */
public class a {
    private final void a(ChannelInfo channelInfo) {
        Hooks.requestEmotes(channelInfo); // TODO: __INJECT_CALL
    }
}
