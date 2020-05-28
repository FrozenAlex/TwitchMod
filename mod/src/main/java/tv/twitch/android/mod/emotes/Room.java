package tv.twitch.android.mod.emotes;


import java.util.Collection;

import tv.twitch.android.mod.models.Emote;


class Room {
    private final int mChannelId;

    private final BttvChannelSet mBttvSet;
    private final FfzChannelSet mFfzSet;


    public Room(int channelId) {
        mChannelId = channelId;
        mBttvSet = new BttvChannelSet(getChannelId());
        mFfzSet = new FfzChannelSet(getChannelId());
    }

    private void clear() {
        mFfzSet.clear();
        mBttvSet.clear();
    }

    public boolean isReadyForRequest() {
        return mBttvSet.isReadyForFetch() && mFfzSet.isReadyForFetch();
    }

    public synchronized void requestEmotes() {
        clear();
        mBttvSet.fetch();
        mFfzSet.fetch();
    }

    public final Emote findEmote(String emoteName) {
        Emote emote = mBttvSet.getEmote(emoteName);
        if (emote != null)
            return emote;

        return mFfzSet.getEmote(emoteName);
    }

    public final Collection<Emote> getBttvEmotes() {
        return mBttvSet.getEmotes();
    }

    public final Collection<Emote> getFfzEmotes() {
        return mFfzSet.getEmotes();
    }

    public int getChannelId() {
        return mChannelId;
    }
}
