package tv.twitch.android.mod.emotes;


public abstract class BaseChannelSet<T> extends BaseEmoteSet<T> {
    private final int mChannelId;

    public BaseChannelSet(int channelId) {
        mChannelId = channelId;
    }

    public int getChannelId() {
        return mChannelId;
    }
}
