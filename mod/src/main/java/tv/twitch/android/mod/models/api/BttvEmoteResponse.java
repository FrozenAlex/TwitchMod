package tv.twitch.android.mod.models.api;

import com.google.gson.annotations.SerializedName;

public class BttvEmoteResponse {
    @SerializedName("id")
    private String id;
    @SerializedName("code")
    private String code;
    @SerializedName("imageType")
    private String imageType;
    @SerializedName("restrictions")
    private BttvRestrictions restrictions;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public BttvRestrictions getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(BttvRestrictions restrictions) {
        this.restrictions = restrictions;
    }
}
