package tv.twitch.android.mod.models;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import java.util.HashMap;

import tv.twitch.chat.ChatEmoticon;
import tv.twitch.chat.ChatEmoticonUrl;

public class FfzEmote implements Emote {
    private final String mCode;
    private final String mId;
    private String url1x = null;
    private String url2x = null;
    private String url3x = null;

    private ChatEmoticon ce = null;

    public FfzEmote(String code, String id, HashMap<Integer, String> urls) {
        this.mCode = code;
        this.mId = id;

        for (Integer key : urls.keySet()) {
            String url = urls.get(key);
            if (TextUtils.isEmpty(url))
                continue;

            if (url.startsWith("//"))
                url = "https:" + url;
            switch (key) {
                case 1:
                    url1x = url;
                    break;
                case 2:
                    url2x = url;
                    break;
                case 4:
                    url3x = url;
                    break;
            }
        }
    }

    @Override
    public String getCode() {
        return mCode;
    }

    @Override
    public String getUrl(Size size) {
        switch (size) {
            case LARGE:
                if (url3x != null)
                    return url3x;
            default:
            case MEDIUM:
                if (url2x != null)
                    return url2x;
            case SMALL:
                if (url1x != null)
                    return url1x;
        }

        return null;
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
