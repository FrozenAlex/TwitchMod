package tv.twitch.android.mod.utils;


import tv.twitch.android.mod.models.settings.UserMessagesFiltering;
import tv.twitch.chat.ChatLiveMessage;
import tv.twitch.chat.ChatMessageInfo;


public class ChatFilteringUtil {
    public enum MessageLevel {
        DEFAULT,
        SUBS,
        MODS,
        STAFF,
        BROADCASTER,
        ACCOUNT,
        SYSTEM,
        UNKNOWN;

        public boolean isHighOrSame(MessageLevel level) {
            if (level == null)
                return false;

            return this.ordinal() >= level.ordinal();
        }
    }

    public static boolean compare(String str, String str2) {
        if (str == null)
            return false;

        if (str2 == null)
            return false;

        return str.equalsIgnoreCase(str2);
    }

    public static boolean filter(ChatLiveMessage liveMessage, String accountName, UserMessagesFiltering filtering) {
        if (liveMessage == null || liveMessage.messageInfo == null)
            return true;

        ChatFilteringUtil.MessageLevel level = ChatFilteringUtil.getMessageLevel(liveMessage.messageInfo, accountName);
        switch (filtering) {
            case BROADCASTER:
                return level.isHighOrSame(MessageLevel.BROADCASTER);
            case MODS:
                return level.isHighOrSame(MessageLevel.MODS);
            case SUBS:
                return level.isHighOrSame(MessageLevel.SUBS);
            case PLEBS:
                return level.isHighOrSame(MessageLevel.DEFAULT);
        }

        return true;
    }

    public static MessageLevel getMessageLevel(ChatMessageInfo messageInfo, String account) {
        if (messageInfo == null)
            return MessageLevel.UNKNOWN;

        if (messageInfo.userMode.system)
            return MessageLevel.SYSTEM;

        if (compare(account, messageInfo.userName))
            return MessageLevel.ACCOUNT;

        if (messageInfo.userMode.broadcaster)
            return MessageLevel.BROADCASTER;

        if (messageInfo.userMode.staff)
            return MessageLevel.STAFF;

        if (messageInfo.userMode.moderator || messageInfo.userMode.globalModerator || messageInfo.userMode.administrator)
            return MessageLevel.MODS;

        if (messageInfo.userMode.vip || messageInfo.userMode.subscriber)
            return MessageLevel.SUBS;

        return MessageLevel.DEFAULT;
    }
}
