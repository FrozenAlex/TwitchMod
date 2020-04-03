package tv.twitch.android.mod.models;

import androidx.annotation.NonNull;

import tv.twitch.android.mod.models.api.BttvEmoteResponse;
import tv.twitch.chat.ChatEmoticon;
import tv.twitch.chat.ChatEmoticonUrl;

public final class BttvEmote implements Emote {
    private static final String sUrlTemplate = "https://cdn.betterttv.net/emote/";
    private final String mCode;
    private final String mId;
    private final boolean isGif;
    private final String url1x;
    private final String url2x;
    private final String url3x;

    private ChatEmoticon ce = null;

    public BttvEmote(String code, String id, BttvEmoteResponse.ImageType imageType) {
        this.mCode = code;
        this.mId = id;
        this.isGif = imageType == BttvEmoteResponse.ImageType.GIF;

        url1x = sUrlTemplate + "/" + id + "/" + "1x";
        url2x = sUrlTemplate + "/" + id + "/" + "2x";
        url3x = sUrlTemplate + "/" + id + "/" + "3x";
    }

    @Override
    public String getCode() {
        return mCode;
    }

    @Override
    public String getUrl(Size size) {
        switch (size) {
            default:
            case LARGE:
                return url3x;
            case MEDIUM:
                return url2x;
            case SMALL:
                return url1x;
        }
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
    public ChatEmoticon getChatEmoticon() {
        if (ce == null) {
            synchronized (this) {
                if (ce == null) {
                    ce = new ChatEmoticonUrl(getCode(), getUrl(Size.LARGE));
                }
            }
        }

        return ce;
    }

    @Override
    @NonNull
    public String toString() {
        return "{Code: " + getCode() + ", Id: " + getId() + ", isGif: " + isGif() + "}";
    }
}
