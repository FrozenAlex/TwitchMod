package tv.twitch.android.models.channel;

// Source: ChannelInfo.kt
public interface ChannelInfo {
    String getDisplayName();

    String getGame();

    int getId();

    String getName();

    boolean isPartner();

    boolean isRecommendation();

    void setRecommendation(boolean z);
}
