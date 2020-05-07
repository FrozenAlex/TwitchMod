package tv.twitch.a.k.g.r1;

import android.content.Context;

import tv.twitch.chat.ChatEmoticon;

// Source: ChatEmoticonUtils
public final class c {
    public static String a(Context context, ChatEmoticon chatEmoticon) { // TODO: __INJECT_METHOD
        if (chatEmoticon.url != null)
            return chatEmoticon.url;

        return org(context, chatEmoticon);
    }

    private static String org(Context context, ChatEmoticon chatEmoticon) { // TODO: __RENAME__a
        return null;
    }
}
