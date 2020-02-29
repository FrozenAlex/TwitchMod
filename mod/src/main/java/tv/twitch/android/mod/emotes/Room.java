package tv.twitch.android.mod.emotes;

import java.util.ArrayList;
import java.util.List;

import tv.twitch.android.mod.models.Emote;


class Room {
    private final int mChannelId;

    private final BttvChannelEmoteSet mBttvSet;
    private final FfzChannelEmoteSet mFfzSet;

    public Room(int channelId) {
        mChannelId = channelId;
        mBttvSet = new BttvChannelEmoteSet(this.mChannelId);
        mFfzSet = new FfzChannelEmoteSet(this.mChannelId);
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

    public final List<Emote> getEmotes() {
        List<Emote> list = new ArrayList<>(mBttvSet.getEmotes());
        list.addAll(mFfzSet.getEmotes());

        return list;
    }

    public final List<Emote> getBttvEmotes() {
        return mBttvSet.getEmotes();
    }

    public final List<Emote> getFfzEmotes() {
        return mFfzSet.getEmotes();
    }

    public int getChannelId() {
        return mChannelId;
    }
}
