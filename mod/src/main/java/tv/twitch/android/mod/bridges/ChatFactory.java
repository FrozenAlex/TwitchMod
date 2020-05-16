package tv.twitch.android.mod.bridges;

import java.util.Collection;

import tv.twitch.android.mod.models.Emote;
import tv.twitch.chat.ChatEmoticon;
import tv.twitch.chat.ChatEmoticonSet;

public class ChatFactory {
    public static ChatEmoticon getEmoticon(String url, String code) {
        ChatEmoticon chatEmoticon = new ChatEmoticon();
        chatEmoticon.match = code;
        chatEmoticon.isRegex = false;
        chatEmoticon.emoticonId = "-1";
        chatEmoticon.url = url;

        return chatEmoticon;
    }

    public static ChatEmoticonSet getSet(String setId, Collection<Emote> emotes) {
        ChatEmoticonSet set = new ChatEmoticonSet();
        set.emoticonSetId = setId;
        set.emoticons = convert(emotes);

        return set;
    }

    private static ChatEmoticon[] convert(Collection<Emote> emoteList) {
        if (emoteList == null || emoteList.size() == 0)
            return new ChatEmoticon[0];

        ChatEmoticon[] chatEmoticons = new ChatEmoticon[emoteList.size()];

        int i = 0;
        for (Emote emote : emoteList) {
            chatEmoticons[i++] = emote.getChatEmoticon();
        }

        return chatEmoticons;
    }
}
