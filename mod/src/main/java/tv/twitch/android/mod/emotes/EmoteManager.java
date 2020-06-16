package tv.twitch.android.mod.emotes;


import android.text.TextUtils;
import android.util.LruCache;

import java.util.ArrayList;
import java.util.Collection;

import tv.twitch.android.mod.models.Emote;
import tv.twitch.android.mod.utils.Logger;


public class EmoteManager {
    private static final int MAX_CACHE_SIZE = 20;
    private static final BttvGlobalSet sGlobalEmotes = new BttvGlobalSet();

    private final RoomCache mRoomCache = new RoomCache(MAX_CACHE_SIZE);

    public EmoteManager() {
        fetchGlobalEmotes();
    }

    private static class RoomCache extends LruCache<Integer, Room> {
        public RoomCache(int maxSize) {
            super(maxSize);
        }

        @Override
        protected Room create(Integer key) {
            Room room = new Room(key);
            room.requestEmotes();

            return room;
        }
    }

    private void fetchGlobalEmotes() {
        Logger.info("Fetching global emoticons...");
        sGlobalEmotes.fetch();
    }

    public void requestEmotes(int channelId, boolean force) {
        if (channelId == 0) {
            Logger.warning("Cannot request emotes: channelId==0");
            return;
        }

        Logger.debug("New emote request: " + channelId);
        if (force) {
            synchronized (mRoomCache) {
                mRoomCache.put(channelId, mRoomCache.create(channelId));
            }
        } else {
            mRoomCache.get(channelId);
        }
    }

    public Collection<Emote> getGlobalEmotes() {
        return sGlobalEmotes.getEmotes();
    }

    public Collection<Emote> getBttvEmotes(int channelId) {
        if (channelId == 0) {
            return new ArrayList<>();
        }

        return mRoomCache.get(channelId).getBttvEmotes();
    }

    public Collection<Emote> getFfzEmotes(int channelId) {
        if (channelId == 0) {
            return new ArrayList<>();
        }

        return mRoomCache.get(channelId).getFfzEmotes();
    }

    public Emote getEmote(String code, int channelId) {
        if (TextUtils.isEmpty(code))
            return null;

        if (channelId != 0) {
            Emote emote = mRoomCache.get(channelId).findEmote(code);
            if (emote != null)
                return emote;
        }

        return sGlobalEmotes.getEmote(code);
    }
}
