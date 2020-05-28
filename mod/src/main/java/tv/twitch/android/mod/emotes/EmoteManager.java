package tv.twitch.android.mod.emotes;


import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import tv.twitch.android.mod.models.Emote;
import tv.twitch.android.mod.utils.Logger;


public class EmoteManager {
    private static final BttvGlobalSet sGlobalEmotes = new BttvGlobalSet();

    private final ConcurrentHashMap<Integer, Room> mRooms = new ConcurrentHashMap<>();


    public EmoteManager() {
        fetchGlobalEmotes();
    }

    public Collection<Emote> getGlobalEmotes() {
        return sGlobalEmotes.getEmotes();
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
        sGlobalEmotes.fetch();
    }

    public Emote getEmote(String code, int channelId) {
        if (TextUtils.isEmpty(code))
            return null;

        if (channelId != 0) {
            Room room = mRooms.get(channelId);
            if (room != null) {
                Emote emote = room.findEmote(code);
                if (emote != null)
                    return emote;
            }
        }

        return sGlobalEmotes.getEmote(code);
    }

    public void requestEmotes(int channelId, boolean force) {
        if (channelId == 0)
            return;

        if (!force && mRooms.containsKey(channelId))
            return;

        request(channelId);
    }

    private synchronized void request(int channelId) {
        if (channelId == 0)
            return;

        Room room = mRooms.get(channelId);
        if (room == null) {
            Logger.debug("Create new room: " + channelId);
            room = new Room(channelId);
            mRooms.put(channelId, room);
        }

        if (room.isReadyForRequest()) {
            Logger.debug("Emotes request: " + channelId);
            room.requestEmotes();
        }
    }
}
