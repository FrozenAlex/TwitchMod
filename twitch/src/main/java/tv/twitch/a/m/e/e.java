package tv.twitch.a.m.e;

// Source: ChatMessageInterface
public interface e {
    //List<MessageBadge> getBadges();

    String getDisplayName();

    //List<MessageToken> getTokens();

    int getUserId();

    String getUserName();

    boolean isAction();

    boolean isDeleted();

    boolean isSystemMessage();
}