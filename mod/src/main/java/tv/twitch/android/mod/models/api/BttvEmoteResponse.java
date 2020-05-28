package tv.twitch.android.mod.models.api;


import com.google.gson.annotations.SerializedName;


public class BttvEmoteResponse {
    @SerializedName("id")
    private String emoteId;
    @SerializedName("code")
    private String code;
    @SerializedName("imageType")
    private ImageType imageType;

    public String getCode() {
        return code;
    }

    public String getId() {
        return emoteId;
    }

    public ImageType getImageType() {
        return imageType;
    }

    public void setImageType(ImageType imageType) {
        this.imageType = imageType;
    }

    public void setId(String emoteId) {
        this.emoteId = emoteId;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
