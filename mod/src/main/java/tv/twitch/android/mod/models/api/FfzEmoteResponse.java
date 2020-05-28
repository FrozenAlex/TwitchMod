package tv.twitch.android.mod.models.api;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class FfzEmoteResponse {
    @SerializedName("id")
    private String emoteId;
    @SerializedName("code")
    private String code;
    @SerializedName("images")
    private HashMap<String, String> images;

    public String getId() {
        return emoteId;
    }

    public String getCode() {
        return code;
    }

    public HashMap<String, String> getImages() {
        return images;
    }

    public void setId(String emoteId) {
        this.emoteId = emoteId;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setImages(HashMap<String, String> images) {
        this.images = images;
    }
}
