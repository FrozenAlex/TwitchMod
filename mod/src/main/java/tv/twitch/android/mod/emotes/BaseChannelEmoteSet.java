package tv.twitch.android.mod.emotes;

public abstract class BaseChannelEmoteSet<T> extends BaseEmoteSet<T> {
    private final String mChannelName;

    public BaseChannelEmoteSet(String channelName) {
        super();
        mChannelName = channelName;
    }

    public String getChannelName() {
        return mChannelName;
    }
}
