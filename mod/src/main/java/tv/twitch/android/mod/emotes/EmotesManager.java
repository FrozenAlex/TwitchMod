package tv.twitch.android.mod.emotes;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import tv.twitch.android.mod.models.Emote;
import tv.twitch.android.mod.models.UserInfoCallback;
import tv.twitch.android.mod.utils.Logger;
import tv.twitch.android.mod.utils.TwitchUsers;
import tv.twitch.android.models.channel.ChannelInfo;

public class EmotesManager implements UserInfoCallback {
    private BttvGlobalEmoteSet mBttvGlobal;
    private FfzGlobalEmoteSet mFfzGlobal;

    private final ConcurrentHashMap<Integer, Room> mRooms = new ConcurrentHashMap<>();
    private final Set<Integer> mCurrentRoomRequests = Collections.newSetFromMap(new ConcurrentHashMap<Integer, Boolean>());

    private static final TwitchUsers sTwitchUsers = TwitchUsers.getInstance();

    private EmotesManager() {
        fetchGlobalEmotes();
    }

    private static class Holder {
        static final EmotesManager mInstance = new EmotesManager();
    }

    public static EmotesManager getInstance() {
        return Holder.mInstance;
    }

    public List<Emote> getGlobalEmotes() {
        List<Emote> list = new ArrayList<>();
        if (mBttvGlobal != null)
            list.addAll(mBttvGlobal.getEmotes());
        if (mFfzGlobal != null)
            list.addAll(mFfzGlobal.getEmotes());

        return list;
    }

    public List<Emote> getRoomEmotes(int channelId) {
        List<Emote> list = new ArrayList<>();
        if (channelId <= 0)
            return list;

        Room room = mRooms.get(channelId);
        if (room == null)
            return list;

        return room.getEmotes();
    }

    private void fetchGlobalEmotes() {
        Logger.info("Fetching global emoticons...");
        if (mBttvGlobal == null) {
            mBttvGlobal = new BttvGlobalEmoteSet();
            mBttvGlobal.fetch();
        }
        if (mFfzGlobal == null) {
            mFfzGlobal = new FfzGlobalEmoteSet();
            mFfzGlobal.fetch();
        }
    }

    public Emote getEmote(String code, int channelId) {
        if (TextUtils.isEmpty(code)) {
            Logger.error("empty code");
            return null;
        }

        if (channelId <= 0) {
            Logger.warning("Bad channel id: " + channelId);
            return null;
        }

        Emote emote = null;
        if (!mRooms.containsKey(channelId))
            request(channelId);
        else {
            Room room = mRooms.get(channelId);
            if (room != null)
                emote = room.findEmote(code);
            if (emote != null)
                return emote;
        }

        emote = mBttvGlobal.getEmote(code);
        if (emote != null)
            return emote;

        emote = mFfzGlobal.getEmote(code);
        return emote;
    }

    public void request(final String channelName, final int channelId) {
        if (channelId == 0) {
            Logger.error("Bad channelId");
            return;
        }

        if (TextUtils.isEmpty(channelName)) {
            Logger.error("Empty channelName");
            return;
        }

        if (mCurrentRoomRequests.contains(channelId))
            return;

        userInfo(channelName, channelId);
        sTwitchUsers.checkAndAddUsername(channelId, channelName);
    }

    public void request(ChannelInfo channelInfo) {
        if (channelInfo != null) {
            int channelId = channelInfo.getId();
            if (channelId == 0) {
                Logger.debug("Bad channelId");
                return;
            }

            if (mCurrentRoomRequests.contains(channelId))
                return;

            if (mRooms.containsKey(channelId))
                return;

            if (TextUtils.isEmpty(channelInfo.getName())) {
                Logger.warning("channelInfo: empty name. Request name by id");
                request(channelId);
                return;
            }

            userInfo(channelInfo.getName(), channelInfo.getId());
            sTwitchUsers.checkAndAddUsername(channelInfo.getId(), channelInfo.getName());
        } else {
            Logger.error("channelInfo is null");
        }
    }

    private void request(int channelId) {
        if (mCurrentRoomRequests.contains(channelId))
            return;

        mCurrentRoomRequests.add(channelId);
        sTwitchUsers.getUserName(channelId, this);
    }

    @Override
    public void userInfo(String userName, int userId) {
        if (userId <= 0) {
            Logger.error("Bad userId: " + userId);
            return;
        }
        if (TextUtils.isEmpty(userName)) {
            Logger.error("empty userName");
            mCurrentRoomRequests.remove(userId);
            return;
        }

        Logger.info(String.format("Fetching %s emoticons...", userName));
        Room room = new Room(userId, userName);
        mRooms.put(userId, room);
        mCurrentRoomRequests.remove(userId);
    }

    @Override
    public void fail(int userId) {
        Logger.error("Error fetching data for id: " + userId);
    }
}
