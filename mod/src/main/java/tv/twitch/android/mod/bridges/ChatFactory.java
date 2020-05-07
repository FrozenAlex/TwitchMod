package tv.twitch.android.mod.bridges;

import tv.twitch.chat.ChatEmoticon;

public class ChatFactory {
    public static ChatEmoticon getEmoticon(String url, String code) {
        ChatEmoticon chatEmoticon = new ChatEmoticon();
        chatEmoticon.match = code;
        chatEmoticon.isRegex = false;
        chatEmoticon.emoticonId = "-1";
        chatEmoticon.url = url;

        return chatEmoticon;
    }
}
