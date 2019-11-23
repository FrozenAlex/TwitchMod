package tv.twitch.android.mod.models;

import androidx.annotation.NonNull;

public final class BttvEmote implements Emote {
    private final String mCode;
    private final String mTemplateUrl;
    private final String mId;
    private final boolean isGif;
    private String url;

    public BttvEmote(String code, String template, String id, String imageType) {
        this.mCode = code;
        this.mTemplateUrl = template;
        this.mId = id;
        this.isGif = imageType != null && imageType.equalsIgnoreCase("gif");
    }

    @Override
    public String getCode() {
        return mCode;
    }

    @Override
    public String getUrl() {
        if (url == null)
            this.url = mTemplateUrl.replace("{{id}}", this.mId).replace("{{image}}", "3x");
        return this.url;
    }

    @Override
    public String getId() {
        return mId;
    }

    @Override
    public boolean isGif() {
        return isGif;
    }

    @Override
    @NonNull
    public String toString() {
        return "{Code: " + getCode() + ", Url: " + getUrl() + ", Id: " + getId() + ", isGif: " + isGif() + "}";
    }
}
