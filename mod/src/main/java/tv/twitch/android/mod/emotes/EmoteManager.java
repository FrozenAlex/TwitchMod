package tv.twitch.android.mod.emotes;


import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import tv.twitch.android.mod.models.Emote;
import tv.twitch.android.mod.utils.Logger;


public class EmoteManager {
    private static final BttvGlobalEmoteSet sGlobalSet = new BttvGlobalEmoteSet();

    private final ConcurrentHashMap<Integer, Room> mRooms = new ConcurrentHashMap<>();
    private final Set<Integer> mCurrentRoomRequests = Collections.newSetFromMap(new ConcurrentHashMap<Integer, Boolean>());

    public EmoteManager() {
        fetchGlobalEmotes();
    }

    public Collection<Emote> getGlobalEmotes() {
        return sGlobalSet.getEmotes();
    }

    public Collection<Emote> getBttvEmotes(int channelId) {
        if (channelId == 0)
            return new ArrayList<>();

        Room room = mRooms.get(channelId);
        if (room == null)
            return new ArrayList<>();

        return room.getBttvEmotes();
    }

    public Collection<Emote> getFfzEmotes(int channelId) {
        if (channelId == 0)
            return new ArrayList<>();

        Room room = mRooms.get(channelId);
        if (room == null)
            return new ArrayList<>();

        return room.getFfzEmotes();
    }

    private void fetchGlobalEmotes() {
        Logger.info("Fetching global emoticons...");
        sGlobalSet.fetch();
    }

    public Emote getEmote(String code, int channelId) {
        if (TextUtils.isEmpty(code)) {
            Logger.error("empty code");
            return null;
        }

        if (channelId != 0) {
            Room room = mRooms.get(channelId);
            if (room != null) {
                Emote emote = room.findEmote(code);
                if (emote != null)
                    return emote;
            }
        }

        return sGlobalSet.getEmote(code);
    }

    public void requestChannelEmoteSet(int channelId, boolean force) {
        if (channelId == 0)
            return;

        if (!force && mRooms.containsKey(channelId))
            return;

        request(channelId);
    }

    private synchronized void request(int channelId) {
        if (channelId == 0)
            return;

        if (!mCurrentRoomRequests.contains(channelId)) {
            synchronized (mCurrentRoomRequests) {
                if (!mCurrentRoomRequests.contains(channelId)) {
                    mCurrentRoomRequests.add(channelId);
                    Room room = new Room(channelId);
                    mRooms.put(channelId, room);
                    mCurrentRoomRequests.remove(channelId);
                }
            }
        }
    }
}
