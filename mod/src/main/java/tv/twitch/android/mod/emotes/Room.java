package tv.twitch.android.mod.emotes;

import java.util.ArrayList;
import java.util.List;

import tv.twitch.android.mod.models.Emote;


class Room {
    private final int mChannelId;
    private final String mChannelName;

    private BttvChannelEmoteSet mBttvSet;
    private FfzChannelEmoteSet mFfzSet;

    public Room(int channelId, String channelName) {
        this.mChannelId = channelId;
        this.mChannelName = channelName;
        requestEmotes();
    }

    private void requestEmotes() {
        mBttvSet = new BttvChannelEmoteSet(this.mChannelName, this.mChannelId);
        mBttvSet.fetch();
        mFfzSet = new FfzChannelEmoteSet(this.mChannelName, this.mChannelId);
        mFfzSet.fetch();
    }


    public final Emote findEmote(String emoteName) {
        Emote emote = null;
        if (mBttvSet != null)
            emote = mBttvSet.getEmote(emoteName);
        if (emote != null)
            return emote;

        if (mFfzSet != null)
            emote = mFfzSet.getEmote(emoteName);
        if (emote != null)
            return emote;

        return emote;
    }

    public final List<Emote> getEmotes() {
        List<Emote> list = new ArrayList<>();
        if (mBttvSet != null)
            list.addAll(mBttvSet.getEmotes());
        if (mFfzSet != null)
            list.addAll(mFfzSet.getEmotes());

        return list;
    }

    public final List<Emote> getBttvEmotes() {
        if (mBttvSet == null)
            return new ArrayList<>();

        return mBttvSet.getEmotes();
    }

    public final List<Emote> getFfzEmotes() {
        if (mFfzSet == null)
            return new ArrayList<>();

        return mFfzSet.getEmotes();
    }

    public int getChannelId() {
        return mChannelId;
    }

    public String getChannelName() {
        return mChannelName;
    }
}
