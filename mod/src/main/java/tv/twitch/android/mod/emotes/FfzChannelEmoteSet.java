package tv.twitch.android.mod.emotes;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.List;

import tv.twitch.android.mod.models.FfzEmote;
import tv.twitch.android.mod.models.api.FfzEmoticon;
import tv.twitch.android.mod.models.api.FfzResponse;
import tv.twitch.android.mod.models.api.FfzRoom;
import tv.twitch.android.mod.models.api.FfzSet;
import tv.twitch.android.mod.utils.Logger;

import static tv.twitch.android.mod.net.ServiceFactory.getFfzApi;

public class FfzChannelEmoteSet extends BaseChannelEmoteSet<FfzResponse> {
    public FfzChannelEmoteSet(int channelId) {
        super(channelId);
    }

    @Override
    public void fetch() {
        getFfzApi().getChannelEmotes(getChannelId()).enqueue(this);
    }

    @Override
    public void onRequestSuccess(FfzResponse ffzResponse) {
        FfzRoom room = ffzResponse.getRoom();

        if (room == null) {
            Logger.warning("Room is null");
            return;
        }

        int setId = room.getSet();
        HashMap<Integer, FfzSet> ffzSets = ffzResponse.getSets();

        if (ffzSets == null || ffzSets.isEmpty()) {
            Logger.warning("Empty sets");
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

}
