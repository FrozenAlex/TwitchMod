package tv.twitch.android.mod.emotes;

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

    private EmotesManager() {
        fetchGlobalEmotes();
    }

    private static class Holder {
        static final EmotesManager mInstance = new EmotesManager();
    }

    public static EmotesManager getInstance() {
        return Holder.mInstance;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void init() {
        getInstance();
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
        if (code == null) {
            Logger.error("code is null");
            return null;
        } else if (code.isEmpty()) {
            Logger.warning("empty code");
            return null;
        }

        Emote emote = findRoomEmote(code, channelId);
        if (emote != null) {
            return emote;
        }

        emote = findGlobalEmote(code);
        return emote;
    }

    private Emote findRoomEmote(String code, int channelId) {
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

        return emote;
    }

    private Emote findGlobalEmote(String code) {
        Emote emote = mBttvGlobal.getEmote(code);
        if (emote != null)
            return emote;

        emote = mFfzGlobal.getEmote(code);
        return emote;
    }

    public void request(ChannelInfo channelInfo) {
        if (channelInfo != null) {
            int channelId = channelInfo.getId();
            if (channelId <= 0) {
                Logger.debug("Bad channelInfo: " + channelInfo.toString());
                return;
            }

            if (mCurrentRoomRequests.contains(channelId))
                return;

            if (channelInfo.getName() == null || channelInfo.getName().isEmpty()) {
                Logger.warning("channelInfo: getName() is null. Request name by id");
                request(channelId);
                return;
            }

            userInfo(channelInfo.getName(), channelInfo.getId());
            TwitchUsers.getInstance().checkAndAddUsername(channelInfo.getId(), channelInfo.getName());
        } else {
            Logger.error("channelInfo is null");
        }
    }

    private void request(int channelId) {
        if (mCurrentRoomRequests.contains(channelId))
            return;

        mCurrentRoomRequests.add(channelId);
        TwitchUsers.getInstance().getUserName(channelId, this);
    }

    @Override
    public void userInfo(String userName, int userId) {
        Logger.info(String.format("Fetching %s emoticons...", userName));
        if (userId <= 0) {
            Logger.error("Bad userId: " + userId);
            return;
        }
        if (userName == null || userName.isEmpty()) {
            Logger.error("onCreateuserName is empty");
            mCurrentRoomRequests.remove(userId);
            return;
        }

        Room room = new Room(userId, userName);
        mRooms.put(userId, room);
        mCurrentRoomRequests.remove(userId);
    }

    @Override
    public void fail(int userId) {
        Logger.error("Error fetching data for id: " + userId);
    }
}
