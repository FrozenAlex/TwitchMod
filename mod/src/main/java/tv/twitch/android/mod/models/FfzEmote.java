package tv.twitch.android.mod.models;


import android.text.TextUtils;

import androidx.annotation.NonNull;

import java.util.HashMap;

import tv.twitch.android.mod.bridges.ChatFactory;
import tv.twitch.android.mod.models.settings.EmoteSize;
import tv.twitch.chat.ChatEmoticon;


public class FfzEmote implements Emote {
    private final String mCode;
    private final String mId;
    private String url1x = null;
    private String url2x = null;
    private String url3x = null;

    private ChatEmoticon ce = null;

    public FfzEmote(String code, String id, HashMap<String, String> urls) {
        this.mCode = code;
        this.mId = id;

        for (String key : urls.keySet()) {
            String url = urls.get(key);
            if (TextUtils.isEmpty(url))
                continue;

            if (url.startsWith("//"))
                url = "https:" + url;
            switch (key) {
                case "1x":
                    url1x = url;
                    break;
                case "2x":
                    url2x = url;
                    break;
                case "4x":
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
    public String getUrl(EmoteSize size) {
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
                    this.ce = ChatFactory.getEmoticon(getUrl(EmoteSize.LARGE), getCode());
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
