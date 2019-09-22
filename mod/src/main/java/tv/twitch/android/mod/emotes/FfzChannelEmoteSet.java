package tv.twitch.android.mod.emotes;

import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import tv.twitch.android.mod.models.Emote;
import tv.twitch.android.mod.models.EmoteSet;
import tv.twitch.android.mod.models.FfzEmote;
import tv.twitch.android.mod.models.api.FfzEmoticon;
import tv.twitch.android.mod.models.api.FfzResponse;
import tv.twitch.android.mod.models.api.FfzRoom;
import tv.twitch.android.mod.models.api.FfzSet;
import tv.twitch.android.mod.bridges.ApiCallback;

import static tv.twitch.android.mod.net.ServiceFactory.getFfzApi;

public class FfzChannelEmoteSet extends ApiCallback<FfzResponse> implements EmoteSet {
    private static final String LOG_TAG = FfzChannelEmoteSet.class.getName();
    private final LinkedHashMap<String, Emote> mRoute = new LinkedHashMap<>();
    private final long mChannelId;

    public FfzChannelEmoteSet(long channelId) {
        this.mChannelId = channelId;
    }

    @Override
    public void onRequestSuccess(FfzResponse ffzResponse) {
        if (ffzResponse == null) {
            Log.e(LOG_TAG, "Body is null");
            return;
        }

        FfzRoom room = ffzResponse.getRoom();

        if (room == null) {
            Log.e(LOG_TAG, "Room is null. API error?");
            return;
        }

        int id = room.getSet();
        HashMap<Integer, FfzSet> sets = ffzResponse.getSets();

        if (sets == null || sets.isEmpty()) {
            Log.w(LOG_TAG, "No sets in room");
            return;
        }

        FfzSet set = sets.get(id);
        if (set == null) {
            Log.e(LOG_TAG, "Set not found: " + id);
            return;
        }

        List<FfzEmoticon> emoticons = set.getEmoticons();
        if (emoticons == null || emoticons.isEmpty()) {
            Log.w(LOG_TAG, "Empty set");
            return;
        }

        for (FfzEmoticon emoticon : emoticons) {
            if (emoticon == null)
                continue;

            if (emoticon.getUrls() == null)
                continue;

            if (TextUtils.isEmpty(emoticon.getName())) {
                Log.w(LOG_TAG, "Bad emote " + emoticon.getId() + ": empty name");
                continue;
            }

            HashMap<Integer, String> urls = emoticon.getUrls();
            String url;
            if (urls.containsKey(4))
                url = urls.get(4);
            else if (urls.containsKey(2))
                url = urls.get(2);
            else if (urls.containsKey(1))
                url = urls.get(1);
            else
                continue;
            if (url == null || url.isEmpty())
                continue;

            if (url.startsWith("//"))
                url = "https:" + url;

            FfzEmote emote = new FfzEmote(emoticon.getName(), String.valueOf(emoticon.getId()), url);
            addEmote(emote);
        }

        Log.i(LOG_TAG, "res: " + mRoute.toString());
    }

    @Override
    public void onRequestFail(FailReason reason) {
        Log.e(LOG_TAG, "requestError");
    }

    @Override
    public synchronized void addEmote(Emote emote) {
        if (emote != null && emote.getCode() != null)
            mRoute.put(emote.getCode(), emote);
    }

    @Override
    public Emote getEmote(String name) {
        return mRoute.get(name);
    }

    @Override
    public void fetch() {
        getFfzApi().getChannelEmotes(this.mChannelId).enqueue(this);
    }
}
