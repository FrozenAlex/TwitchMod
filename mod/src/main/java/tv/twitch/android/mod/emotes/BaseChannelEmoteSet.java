package tv.twitch.android.mod.emotes;

public abstract class BaseChannelEmoteSet<T> extends BaseEmoteSet<T> {
    private final String mChannelName;
    private final int mChannelId;

    public BaseChannelEmoteSet(String channelName, int channelId) {
        super();
        mChannelName = channelName;
        mChannelId = channelId;
    }

    public String getChannelName() {
        return mChannelName;
    }

    public int getChannelId() {
        return mChannelId;
    }
}
