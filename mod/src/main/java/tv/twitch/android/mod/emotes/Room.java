package tv.twitch.android.mod.emotes;


import java.util.Collection;

import tv.twitch.android.mod.models.Emote;


class Room {
    private final int mChannelId;

    private final BttvChannelEmoteSet mBttvSet;
    private final FfzChannelEmoteSet mFfzSet;

    public Room(int channelId) {
        mChannelId = channelId;
        mBttvSet = new BttvChannelEmoteSet(getChannelId());
        mFfzSet = new FfzChannelEmoteSet(getChannelId());
        requestEmotes();
    }

    private void requestEmotes() {
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
