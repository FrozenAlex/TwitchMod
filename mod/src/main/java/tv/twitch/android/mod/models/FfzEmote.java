package tv.twitch.android.mod.models;

public class FfzEmote implements Emote {
    private final String mCode;
    private final String mId;
    private final String mUrl;

    public FfzEmote(String code, String id, String url) {
        this.mCode = code;
        this.mId = id;
        this.mUrl = url;
    }

    @Override
    public String getCode() {
        return mCode;
    }

    @Override
    public String getUrl() {
        return mUrl;
    }

    @Override
    public String getId() {
        return mId;
    }

    @Override
    public boolean isGif() {
        return false;
    }

    @Override
    public String toString() {
        return "{Code: " + getCode() + ", Url: " + getUrl() + ", Id: " + getId() + ", isGif: " + isGif() + "}";
    }
}
