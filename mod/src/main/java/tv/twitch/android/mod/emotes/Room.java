package tv.twitch.android.mod.emotes;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import tv.twitch.android.mod.models.Emote;
import tv.twitch.android.mod.models.UserInfoCallback;
import tv.twitch.android.mod.utils.TwitchUsers;


class Room implements UserInfoCallback {
    private final static String LOG_TAG = Room.class.getName();

    private final int mChannelId;

    private BttvChannelEmoteSet mBttvSet;
    private FfzChannelEmoteSet mFfzSet;

    public Room(int channelId) {
        this.mChannelId = channelId;
        requestEmotes();
    }

    private void requestEmotes() {
        TwitchUsers.getInstance().getUserName(this.mChannelId, this);
    }

    private void update(String userName, int userId) {
        Log.i(LOG_TAG, String.format("Fetching %s [%d] emoticons...", userName, userId));
        mBttvSet = new BttvChannelEmoteSet(userName);
        mBttvSet.fetch();
        mFfzSet = new FfzChannelEmoteSet(userName);
        mFfzSet.fetch();
    }

    public final Emote findEmote(String emoteName) {
        Emote emote = null;
        if (mBttvSet != null)
            emote = mBttvSet.getEmote(emoteName);
        if (emote == null)
            if (mFfzSet != null)
                emote = mFfzSet.getEmote(emoteName);

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

    @Override
    public void userInfo(String userName, int userId) {
        update(userName, userId);
    }

    @Override
    public void fail(int userId) {
        Log.e(LOG_TAG, String.format("Error fetching emoticons for channel %d", userId));
    }
}
