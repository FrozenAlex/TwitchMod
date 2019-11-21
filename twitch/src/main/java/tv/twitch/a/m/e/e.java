package tv.twitch.a.m.e;

import java.util.List;

import tv.twitch.android.models.chat.MessageToken;

// Source: ChatMessageInterface
public interface e {
    //List<MessageBadge> getBadges();

    String getDisplayName();

    List<MessageToken> getTokens();

    int getUserId();

    String getUserName();

    boolean isAction();

    boolean isDeleted();

    boolean isSystemMessage();
}