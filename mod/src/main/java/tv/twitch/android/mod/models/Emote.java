package tv.twitch.android.mod.models;

import tv.twitch.chat.ChatEmoticon;

public interface Emote {
    String getCode();

    String getUrl(Size size);

    String getId();

    boolean isGif();

    ChatEmoticon getChatEmoticon();

    enum Size {
        SMALL,
        MEDIUM,
        LARGE
    }
}
