package tv.twitch.android.mod.models;

import androidx.annotation.NonNull;

import java.util.Locale;

import tv.twitch.android.mod.models.api.BttvEmoteResponse;

public final class BttvEmote implements Emote {
    private static final String sUrlTemplate = "https://cdn.betterttv.net/emote/%s/%dx";
    private final String mCode;
    private final String mId;
    private final boolean isGif;
    private String url;

    public enum EmoteSize {
        SMALL(1),
        MEDIUM(2),
        LARGE(3);

        private final int value;

        EmoteSize(int size) {
            this.value = size;
        }

        public int getValue() {
            return value;
        }
    }

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
            this.url = String.format(Locale.ENGLISH, sUrlTemplate, this.mId, EmoteSize.LARGE.getValue());
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
