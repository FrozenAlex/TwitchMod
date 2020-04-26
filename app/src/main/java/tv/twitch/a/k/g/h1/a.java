package tv.twitch.a.k.g.h1;


import tv.twitch.android.mod.bridges.LoaderLS;
import tv.twitch.android.models.channel.ChannelInfo;

// Source: ChatConnectionController
public class a {

    private final void a(ChannelInfo channelInfo) {
        LoaderLS.getInstance().getHelper().newRequest(channelInfo); // TODO: __ADD_START
    }
}
