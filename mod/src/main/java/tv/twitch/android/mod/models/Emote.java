package tv.twitch.android.mod.models;


import tv.twitch.android.mod.models.settings.EmoteSize;
import tv.twitch.chat.ChatEmoticon;


public interface Emote {
    String getCode();

    String getUrl(EmoteSize size);

    String getId();

    boolean isGif();

    ChatEmoticon getChatEmoticon();
}
