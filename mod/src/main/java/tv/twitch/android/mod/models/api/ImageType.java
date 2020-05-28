package tv.twitch.android.mod.models.api;

import com.google.gson.annotations.SerializedName;

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
