package tv.twitch.android.mod.emotes;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import tv.twitch.android.mod.models.Emote;
import tv.twitch.android.mod.models.UserInfoCallback;
import tv.twitch.android.mod.bridges.LoaderLS;
import tv.twitch.android.mod.utils.Logger;
import tv.twitch.android.models.channel.ChannelInfo;

public class EmoteManager implements UserInfoCallback {
    private static final BttvGlobalEmoteSet sGlobalSet = new BttvGlobalEmoteSet();

    private final ConcurrentHashMap<Integer, Room> mRooms = new ConcurrentHashMap<>();
    private final Set<Integer> mCurrentRoomRequests = Collections.newSetFromMap(new ConcurrentHashMap<Integer, Boolean>());

    private static volatile EmoteManager sInstance;

    private EmoteManager() {
        fetchGlobalEmotes();
    }

    public static EmoteManager getInstance() {
        if (sInstance == null) {
            synchronized(EmoteManager.class) {
                if (sInstance == null)
                    sInstance = new EmoteManager();
            }
        }
        return sInstance;
    }

    public List<Emote> getGlobalEmotes() {
        return sGlobalSet.getEmotes();
    }

    public List<Emote> getBttvEmotes(int channelId) {
        if (channelId == 0)
            return new ArrayList<>();

        Room room = mRooms.get(channelId);
        if (room == null)
            return new ArrayList<>();

        return room.getBttvEmotes();
    }

    public List<Emote> getFfzEmotes(int channelId) {
        if (channelId == 0)
            return new ArrayList<>();

        Room room = mRooms.get(channelId);
        if (room == null)
            return new ArrayList<>();

        return room.getFfzEmotes();
    }

    public List<Emote> getRoomEmotes(int channelId) {
        if (channelId == 0)
            return new ArrayList<>();

        Room room = mRooms.get(channelId);
        if (room == null)
            return new ArrayList<>();

        return room.getEmotes();
    }

    private void fetchGlobalEmotes() {
        Logger.info("Fetching global emoticons...");
        sGlobalSet.fetch();
    }

    public Emote getEmote(String code, int channelId) {
        if (TextUtils.isEmpty(code)) {
            Logger.error("Empty code");
            return null;
        }

        if (channelId == 0) {
            Logger.error("Bad channelId");
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

        emote = sGlobalSet.getEmote(code);
        if (emote != null)
            return emote;

        return emote;
    }

    public void requestChannelEmoteSet(String channelName, int channelId, boolean force) {
        if (channelId == 0) {
            Logger.error("Bad channelId");
            return;
        }

        if (TextUtils.isEmpty(channelName)) {
            Logger.error("Empty channelName");
            return;
        }

        if (!force && mCurrentRoomRequests.contains(channelId))
            return;

        userInfo(channelName, channelId);
    }

    public void requestChannelEmoteSet(ChannelInfo channelInfo, boolean force) {
        if (channelInfo != null) {
            int channelId = channelInfo.getId();
            if (channelId == 0) {
                Logger.debug("Bad channelId");
                return;
            }

            if (mCurrentRoomRequests.contains(channelId))
                return;

            if (!force && mRooms.containsKey(channelId))
                return;

            if (TextUtils.isEmpty(channelInfo.getName())) {
                Logger.warning("channelInfo: empty name. Request name by id");
                request(channelId);
                return;
            }

            userInfo(channelInfo.getName(), channelInfo.getId());
        } else {
            Logger.error("channelInfo is null");
        }
    }

    private void request(int channelId) {
        if (mCurrentRoomRequests.contains(channelId))
            return;

        synchronized (EmoteManager.class) {
            if (mCurrentRoomRequests.contains(channelId))
                return;

            mCurrentRoomRequests.add(channelId);
            LoaderLS.getInstance().getTwitchUser().getUserName(channelId, this);
        }
    }

    @Override
    public void userInfo(String userName, int userId) {
        if (userId == 0) {
            Logger.error("Bad userId");
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
