package tv.twitch.a.k.e.p1;

import android.content.Context;

import tv.twitch.chat.ChatEmoticon;
import tv.twitch.chat.ChatEmoticonUrl;

// Source: ChatEmoticonUtils
public final class c {
    public static final String a(Context context, ChatEmoticon chatEmoticon) { // TODO: __ADD
        if (chatEmoticon instanceof ChatEmoticonUrl)
            return ((ChatEmoticonUrl) chatEmoticon).getUrl();
        else
            return org(context, chatEmoticon);
    }

    public static final String a(ChatEmoticon chatEmoticon) { // TODO: __ADD
        if (chatEmoticon instanceof ChatEmoticonUrl)
            return chatEmoticon.match + " ";
        else
            return org(chatEmoticon);
    }

    public static final String org(Context context, ChatEmoticon chatEmoticon) { // TODO: __RENAME
        return null;
    }

    public static final String org(ChatEmoticon chatEmoticon) { // TODO: __RENAME
        return null;
    }
}
