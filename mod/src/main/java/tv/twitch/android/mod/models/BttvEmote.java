package tv.twitch.android.mod.models;

import androidx.annotation.NonNull;

import java.util.Locale;

import tv.twitch.android.mod.models.api.BttvEmoteResponse;

public final class BttvEmote implements Emote {
    private static final String sUrlTemplate = "https://cdn.betterttv.net/emote/%s/3x";
    private final String mCode;
    private final String mId;
    private final boolean isGif;
    private String url;

    public BttvEmote(String code, String id, BttvEmoteResponse.ImageType imageType) {
        this.mCode = code;
        this.mId = id;
        this.isGif = imageType == BttvEmoteResponse.ImageType.GIF;
    }

    @Override
    public String getCode() {
        return mCode;
    }

    @Override
    public String getUrl() {
        if (url == null) {
            this.url = String.format(Locale.ENGLISH, sUrlTemplate, this.mId);
        }
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
