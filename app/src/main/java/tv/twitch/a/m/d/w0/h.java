package tv.twitch.a.m.d.w0;

import android.content.Context;

import tv.twitch.chat.ChatEmoticon;
import tv.twitch.chat.ChatEmoticonUrl;

// Source: EmoteUtils
public final class h {

    public static final String a(Context context, ChatEmoticon chatEmoticon) { // TODO: __REPLACE
        String str = chatEmoticon.emoticonId;
        if (chatEmoticon instanceof ChatEmoticonUrl)
            return ((ChatEmoticonUrl) chatEmoticon).getUrl();
        else
            return a(context, str);
    }

    public static final String a(Context context, String str) {
        return null;
    }
}
