package tv.twitch.android.mod.emotes;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import tv.twitch.android.mod.models.Emote;
import tv.twitch.android.mod.utils.Logger;
import tv.twitch.android.models.channel.ChannelInfo;

public class EmoteManager {
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

        Emote emote = null;
        if (channelId != 0) {
            if (!mRooms.containsKey(channelId))
                request(channelId);
            else {
                Room room = mRooms.get(channelId);
                if (room != null)
                    emote = room.findEmote(code);
                if (emote != null)
                    return emote;
            }
        }

        emote = sGlobalSet.getEmote(code);

        return emote;
    }

    public void requestChannelEmoteSet(int channelId, boolean force) {
        if (channelId == 0) {
            Logger.error("Bad channelId");
            return;
        }

        if (!force && mRooms.containsKey(channelId))
            return;

        request(channelId);
    }

    private synchronized void request(int channelId) {
        if (channelId == 0) {
            Logger.error("Bad channelId");
            return;
        }

        if (mCurrentRoomRequests.contains(channelId))
            return;

        mCurrentRoomRequests.add(channelId);
        Room room = new Room(channelId);
        mRooms.put(channelId, room);
        mCurrentRoomRequests.remove(channelId);
    }
}
