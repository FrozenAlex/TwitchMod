package tv.twitch.android.mod.models.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BttvResponse {
    @SerializedName("status")
    private String status;
    @SerializedName("urlTemplate")
    private String urlTemplate;
    @SerializedName("bots")
    private List<String> bots;
    @SerializedName("emotes")
    private List<BttvEmoteResponse> emotes;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<BttvEmoteResponse> getBttvEmotes() {
        return emotes;
    }

    public void setBttvEmotes(List<BttvEmoteResponse> emotes) {
        this.emotes = emotes;
    }

    public List<String> getBots() {
        return bots;
    }

    public void setBots(List<String> bots) {
        this.bots = bots;
    }

    public String getUrlTemplate() {
        return urlTemplate;
    }

    public void setUrlTemplate(String urlTemplate) {
        this.urlTemplate = urlTemplate;
    }
}
