package tv.twitch.android.mod.emotes;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import tv.twitch.android.mod.models.Emote;
import tv.twitch.android.mod.models.EmoteSet;

public class EmotesManager {
    private final static String LOG_TAG = EmotesManager.class.getName();

    private EmoteSet mBttvGlobal;
    private EmoteSet mFfzGlobal;

    private final ConcurrentHashMap<Integer, Room> mRooms = new ConcurrentHashMap<>();
    private final Set<Integer> mCurrentRequests = Collections.newSetFromMap(new ConcurrentHashMap<Integer, Boolean>());

    private EmotesManager() {
        fetchGlobalEmotes();
    }

    private static class Holder {
        static final EmotesManager instance = new EmotesManager();
    }

    public static EmotesManager getInstance() {
        return Holder.instance;
    }

    public List<Emote> getGlobalEmotes() {
        List<Emote> list = new ArrayList<>();
        if (mBttvGlobal != null)
            list.addAll(mBttvGlobal.getEmotes());
        if (mFfzGlobal != null)
            list.addAll(mFfzGlobal.getEmotes());

        return list;
    }

    public List<Emote> getEmotes(int channelId) {
        List<Emote> list = new ArrayList<>();
        Room room = mRooms.get(channelId);
        if (room == null)
            return list;

        return room.getEmotes();
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

    public Emote getEmote(String code, int channelId) {
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

    public void request(int channelId) {
        request(channelId, true);
    }

    private void request(int channelId, boolean forced) {
        if (!forced && mRooms.containsKey(channelId))
            return;

        if (mCurrentRequests.contains(channelId))
            return;

        mCurrentRequests.add(channelId);
        Log.i(LOG_TAG, String.format("New request: %d", channelId));
        mRooms.put(channelId, new Room(channelId));
        mCurrentRequests.remove(channelId);
    }
}
