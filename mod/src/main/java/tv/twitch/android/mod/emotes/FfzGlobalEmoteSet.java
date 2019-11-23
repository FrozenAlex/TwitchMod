package tv.twitch.android.mod.emotes;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.List;

import tv.twitch.android.mod.models.FfzEmote;
import tv.twitch.android.mod.models.api.FfzEmoticon;
import tv.twitch.android.mod.models.api.FfzGlobalResponse;
import tv.twitch.android.mod.models.api.FfzSet;
import tv.twitch.android.mod.utils.Logger;

import static tv.twitch.android.mod.net.ServiceFactory.getFfzApi;

public class FfzGlobalEmoteSet extends BaseEmoteSet<FfzGlobalResponse> {
    public FfzGlobalEmoteSet() {
        super(null);
    }

    public void fetch() {
        getFfzApi().getGlobalEmotes().enqueue(this);
    }

    @Override
    public void onRequestSuccess(FfzGlobalResponse ffzGlobalResponse) {
        List<Integer> defaultSetsId = ffzGlobalResponse.getDefaultSets();

        if (defaultSetsId == null || defaultSetsId.isEmpty()) {
            Logger.error("No ids. API error?");
            return;
        }

        HashMap<Integer, FfzSet> ffzSets = ffzGlobalResponse.getSets();
        if (ffzSets == null || ffzSets.isEmpty()) {
            Logger.error("Empty or null sets. API error?");
            return;
        }

        for (Integer setId : defaultSetsId) {
            FfzSet set = ffzSets.get(setId);
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
                    Logger.warning("Bad emote " + emoticon.getId() + ": empty emote name");
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

}
