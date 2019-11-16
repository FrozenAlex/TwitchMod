package tv.twitch.android.mod.emotes;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import tv.twitch.android.mod.models.Emote;
import tv.twitch.android.mod.models.EmoteSet;
import tv.twitch.android.mod.models.FfzEmote;
import tv.twitch.android.mod.models.api.FfzEmoticon;
import tv.twitch.android.mod.models.api.FfzResponse;
import tv.twitch.android.mod.models.api.FfzRoom;
import tv.twitch.android.mod.models.api.FfzSet;
import tv.twitch.android.mod.bridges.ApiCallback;
import tv.twitch.android.mod.utils.Logger;

import static tv.twitch.android.mod.net.ServiceFactory.getFfzApi;

public class FfzChannelEmoteSet extends ApiCallback<FfzResponse> implements EmoteSet {
    private final Map<String, Emote> mEmoteMap = Collections.synchronizedMap(new LinkedHashMap<String, Emote>());
    private final String mChannelName;

    public FfzChannelEmoteSet(String channelName) {
        this.mChannelName = channelName;
    }

    @Override
    public void onRequestSuccess(FfzResponse ffzResponse) {
        FfzRoom room = ffzResponse.getRoom();

        if (room == null) {
            Logger.error("room==null");
            return;
        }

        int setId = room.getSet();
        HashMap<Integer, FfzSet> ffzSets = ffzResponse.getSets();

        if (ffzSets == null || ffzSets.isEmpty()) {
            Logger.error("No sets in room");
            return;
        }

        FfzSet set = ffzSets.get(setId);
        if (set == null) {
            Logger.error("Set not found: " + setId);
            return;
        }

        List<FfzEmoticon> emoticons = set.getEmoticons();
        if (emoticons == null || emoticons.isEmpty()) {
            Logger.warning("Empty set");
            return;
        }

        for (FfzEmoticon emoticon : emoticons) {
            if (emoticon == null)
                continue;

            if (emoticon.getUrls() == null)
                continue;

            if (TextUtils.isEmpty(emoticon.getName())) {
                Logger.warning("Bad emote " + emoticon.getId() + ": empty name");
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
        Logger.info("res: " + mEmoteMap.toString());
    }

    @Override
    public void onRequestFail(FailReason reason) {
        Logger.error(reason.name());
    }

    @Override
    public synchronized void addEmote(Emote emote) {
        if (emote != null && !TextUtils.isEmpty(emote.getCode()))
            mEmoteMap.put(emote.getCode(), emote);
        else {
            Logger.debug("Bad emote: " + emote);
        }
    }

    @Override
    public Emote getEmote(String name) {
        return mEmoteMap.get(name);
    }

    @Override
    public void fetch() {
        getFfzApi().getChannelEmotes(this.mChannelName).enqueue(this);
    }

    @Override
    public List<Emote> getEmotes() {
        return new ArrayList<>(mEmoteMap.values());
    }
}
