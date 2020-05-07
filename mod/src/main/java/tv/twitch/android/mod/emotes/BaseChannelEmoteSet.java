package tv.twitch.android.mod.emotes;


public abstract class BaseChannelEmoteSet<T> extends BaseEmoteSet<T> {
    private final int mChannelId;

    public BaseChannelEmoteSet(int channelId) {
        mChannelId = channelId;
    }

    public int getChannelId() {
        return mChannelId;
    }
}
