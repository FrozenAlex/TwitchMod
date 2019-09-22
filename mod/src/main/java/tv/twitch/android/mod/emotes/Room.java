package tv.twitch.android.mod.emotes;

import android.util.Log;

import tv.twitch.android.mod.models.Emote;
import tv.twitch.android.mod.models.EmoteSet;
import tv.twitch.android.mod.models.UserInfoCallback;
import tv.twitch.android.mod.utils.TwitchUsers;


class Room implements UserInfoCallback {
    private final static String LOG_TAG = Room.class.getName();

    private final long mChannelId;

    private EmoteSet mBttvSet;
    private EmoteSet mFfzSet;

    public Room(long channelId) {
        this.mChannelId = channelId;
        requestEmotes();
    }

    private void requestEmotes() {
        TwitchUsers.getInstance().requestUserName(this.mChannelId, this);
    }

    private void fetch(long userId, String userName) {
        Log.i(LOG_TAG, "Fetching '" + userName + "' emoticons...");
        try {
            if (mBttvSet == null) {
                mBttvSet = new BttvChannelEmoteSet(userName);
                mBttvSet.fetch();
            }
            if (mFfzSet == null) {
                mFfzSet = new FfzChannelEmoteSet(userId);
                mFfzSet.fetch();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    @Override
    public void userInfo(String userName, long userId) {
        if (userId == 0 || userName == null) {
            Log.e(LOG_TAG, "userInfo error");
            return;
        }

        fetch(userId, userName);
    }
}
