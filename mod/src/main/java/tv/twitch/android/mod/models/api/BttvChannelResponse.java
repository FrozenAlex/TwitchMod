package tv.twitch.android.mod.models.api;


import com.google.gson.annotations.SerializedName;

import java.util.List;


public class BttvChannelResponse {
    @SerializedName("channelEmotes")
    private List<BttvEmoteResponse> channelEmotes;
    @SerializedName("sharedEmotes")
    private List<BttvEmoteResponse> sharedEmotes;

    public List<BttvEmoteResponse> getChannelEmotes() {
        return channelEmotes;
    }

    public List<BttvEmoteResponse> getSharedEmotes() {
        return sharedEmotes;
    }

    public void setChannelEmotes(List<BttvEmoteResponse> channelEmotes) {
        this.channelEmotes = channelEmotes;
    }

    public void setSharedEmotes(List<BttvEmoteResponse> sharedEmotes) {
        this.sharedEmotes = sharedEmotes;
    }
}
