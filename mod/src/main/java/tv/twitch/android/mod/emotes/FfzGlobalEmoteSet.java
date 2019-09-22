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
import tv.twitch.android.mod.models.api.FfzGlobalResponse;
import tv.twitch.android.mod.models.api.FfzSet;
import tv.twitch.android.mod.bridges.ApiCallback;

import static tv.twitch.android.mod.net.ServiceFactory.getFfzApi;

public class FfzGlobalEmoteSet extends ApiCallback<FfzGlobalResponse> implements EmoteSet {
    private static final String LOG_TAG = FfzGlobalEmoteSet.class.getName();

    private final LinkedHashMap<String, Emote> mRoute = new LinkedHashMap<>();

    @Override
    public void onRequestSuccess(FfzGlobalResponse ffzGlobalResponse) {
        if (ffzGlobalResponse == null) {
            Log.e(LOG_TAG, "Body is null");
            return;
        }

        List<Integer> ids = ffzGlobalResponse.getDefaultSets();

        if (ids == null || ids.isEmpty()) {
            Log.e(LOG_TAG, "No ids. API error?");
            return;
        }

        HashMap<Integer, FfzSet> sets = ffzGlobalResponse.getSets();
        if (sets == null || sets.isEmpty()) {
            Log.e(LOG_TAG, "No sets. API error?");
            return;
        }

        for (Integer id : ids) {
            FfzSet set = sets.get(id);
            if (set == null)
                continue;

            List<FfzEmoticon> emoticons = set.getEmoticons();
            if (emoticons == null || emoticons.isEmpty())
                continue;

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

    public void fetch() {
        getFfzApi().getGlobalEmotes().enqueue(this);
    }
}
