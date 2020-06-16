package tv.twitch.android.mod.models;


import tv.twitch.android.mod.bridges.ChatFactory;
import tv.twitch.android.mod.models.api.ImageType;
import tv.twitch.android.mod.models.settings.EmoteSize;
import tv.twitch.chat.ChatEmoticon;


public final class BttvEmoteModel implements Emote {
    private static final String sUrlTemplate = "https://cdn.betterttv.net/emote/{id}/{size}";

    private final String mCode;
    private final String mId;
    private final boolean isGif;

    private String url1x = null;
    private String url2x = null;
    private String url3x = null;

    private ChatEmoticon ce = null;

    public BttvEmoteModel(String code, String id, ImageType imageType) {
        this.mCode = code;
        this.mId = id;
        this.isGif = imageType == ImageType.GIF;
    }

    @Override
    public String getCode() {
        return mCode;
    }

    @Override
    public String getUrl(EmoteSize size) {
        switch (size) {
            case LARGE:
                return getUrl3x();
            default:
            case MEDIUM:
                return getUrl2x();
            case SMALL:
                return getUrl1x();
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
                    this.ce = ChatFactory.getEmoticon(getUrl(EmoteSize.LARGE), getCode());
                }
            }
        }

        return ce;
    }

    private String getUrl1x() {
        if (url1x == null) {
            synchronized (this) {
                if (url1x == null) {
                    url1x = sUrlTemplate.replace("{id}", getId()).replace("{size}", "1x");
                }
            }
        }

        return url1x;
    }

    private String getUrl2x() {
        if (url2x == null) {
            synchronized (this) {
                if (url2x == null) {
                    url2x = sUrlTemplate.replace("{id}", getId()).replace("{size}", "2x");
                }
            }
        }

        return url2x;
    }

    private String getUrl3x() {
        if (url3x == null) {
            synchronized (this) {
                if (url3x == null) {
                    url3x = sUrlTemplate.replace("{id}", getId()).replace("{size}", "3x");
                }
            }
        }

        return url3x;
    }

    @Override
    public String toString() {
        return "{Code: " + getCode() + ", Id: " + getId() + ", isGif: " + isGif() + "}";
    }
}
