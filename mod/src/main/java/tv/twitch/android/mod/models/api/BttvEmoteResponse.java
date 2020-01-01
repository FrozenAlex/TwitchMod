package tv.twitch.android.mod.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BttvEmoteResponse {
    @SerializedName("id")
    private String emoteId;
    @SerializedName("code")
    private String code;
    @SerializedName("imageType")
    private ImageType imageType;
    @SerializedName("userId")
    @Expose
    private Object userId;
    @SerializedName("user")
    @Expose
    private Object user;

    public enum ImageType {
        @SerializedName("png")
        PNG("png"),
        @SerializedName("gif")
        GIF("gif");

        private final String mValue;

        ImageType(String value) {
            mValue = value;
        }

        public String getValue() {
            return mValue;
        }
    }

    public String getCode() {
        return code;
    }

    public String getId() {
        return emoteId;
    }

    public Object getUser() {
        return user;
    }

    public Object getUserId() {
        return userId;
    }

    public ImageType getImageType() {
        return imageType;
    }

    public void setImageType(ImageType imageType) {
        this.imageType = imageType;
    }

    public void setUserId(Object userId) {
        this.userId = userId;
    }

    public void setId(String emoteId) {
        this.emoteId = emoteId;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setUser(Object user) {
        this.user = user;
    }
}
