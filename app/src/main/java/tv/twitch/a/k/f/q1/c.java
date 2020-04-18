package tv.twitch.a.k.f.q1;

import android.content.Context;

import tv.twitch.chat.ChatEmoticon;
import tv.twitch.chat.ChatEmoticonUrl;

// Source: ChatEmoticonUtils
public final class c {
    public static String a(Context context, ChatEmoticon chatEmoticon) { // TODO: __ADD
        if (chatEmoticon instanceof ChatEmoticonUrl)
            return ((ChatEmoticonUrl) chatEmoticon).getUrl();
        else
            return org(context, chatEmoticon);
    }

    public static String a(ChatEmoticon chatEmoticon) { // TODO: __ADD
        if (chatEmoticon instanceof ChatEmoticonUrl)
            return chatEmoticon.match + " ";
        else
            return org(chatEmoticon);
    }

    private static String org(Context context, ChatEmoticon chatEmoticon) { // TODO: __RENAME
        return null;
    }

    private static String org(ChatEmoticon chatEmoticon) { // TODO: __RENAME
        return null;
    }
}
