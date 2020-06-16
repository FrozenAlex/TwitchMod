package tv.twitch.a.k.g.v1;

import android.content.Context;

import tv.twitch.chat.ChatEmoticon;

/**
 * Source: ChatEmoticonUtils
 */
public final class c {
    public static final String c(Context context, ChatEmoticon chatEmoticon) { // TODO: __INJECT_METHOD
        if (chatEmoticon.url != null)
            return chatEmoticon.url;

        return org(context, chatEmoticon);
    }

    public static final String org(Context context, ChatEmoticon chatEmoticon) { // TODO: __RENAME__c
        return null;
    }
}
