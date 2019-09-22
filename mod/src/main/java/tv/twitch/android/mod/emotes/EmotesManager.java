package tv.twitch.android.mod.emotes;

import android.util.Log;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import tv.twitch.android.mod.models.Emote;

public class EmotesManager {
    private final static String LOG_TAG = EmotesManager.class.getName();

    private BttvGlobalEmoteSet mBttvGlobal;
    private FfzGlobalEmoteSet mFfzGlobal;

    private final ConcurrentHashMap<Long, Room> mRooms = new ConcurrentHashMap<>();
    private final Set<Long> mCurrentRequests = Collections.newSetFromMap(new ConcurrentHashMap<Long, Boolean>());

    private EmotesManager() {
        fetchGlobalEmotes();
    }

    private static class Holder {
        static final EmotesManager instance = new EmotesManager();
    }

    public static EmotesManager getInstance() {
        return Holder.instance;
    }

    private void fetchGlobalEmotes() {
        try {
            Log.i(LOG_TAG, "Fetching global emoticons...");
            if (mBttvGlobal == null) {
                mBttvGlobal = new BttvGlobalEmoteSet();
                mBttvGlobal.fetch();
            }
            if (mFfzGlobal == null) {
                mFfzGlobal = new FfzGlobalEmoteSet();
                mFfzGlobal.fetch();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Emote getEmote(String code, long channelId) {
        Emote emote = null;

        if (!mRooms.containsKey(channelId))
            request(channelId, false);
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

    public void request(long channelId) {
        request(channelId, true);
    }

    private void request(long channelId, boolean forced) {
        if (!forced && mRooms.containsKey(channelId))
            return;

        if (mCurrentRequests.contains(channelId))
            return;

        mCurrentRequests.add(channelId);
        Log.i(LOG_TAG, "New request for " + channelId);
        mRooms.put(channelId, new Room(channelId));
        mCurrentRequests.remove(channelId);
    }
}
