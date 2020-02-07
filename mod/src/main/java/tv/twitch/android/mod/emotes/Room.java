package tv.twitch.android.mod.emotes;

import java.util.ArrayList;
import java.util.List;

import tv.twitch.android.mod.models.Emote;


class Room {
    private final int mChannelId;
    private final String mChannelName;

    private final BttvChannelEmoteSet mBttvSet;
    private final FfzChannelEmoteSet mFfzSet;

    public Room(int channelId, String channelName) {
        mChannelId = channelId;
        mChannelName = channelName;
        mBttvSet = new BttvChannelEmoteSet(this.mChannelName, this.mChannelId);
        mFfzSet = new FfzChannelEmoteSet(this.mChannelName, this.mChannelId);
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

        emote = mFfzSet.getEmote(emoteName);
        if (emote != null)
            return emote;

        return emote;
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

    public String getChannelName() {
        return mChannelName;
    }
}
