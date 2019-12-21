package tv.twitch.android.mod.emotes;

import android.text.TextUtils;

import java.util.List;


import tv.twitch.android.mod.models.BttvEmote;
import tv.twitch.android.mod.models.api.BttvEmoteResponse;
import tv.twitch.android.mod.models.api.BttvResponse;
import tv.twitch.android.mod.utils.Logger;

import static tv.twitch.android.mod.net.ServiceFactory.getBttvApi;

public class BttvGlobalEmoteSet extends BaseEmoteSet<BttvResponse> {
    @Override
    public void fetch() {
        getBttvApi().getGlobalEmotes().enqueue(this);
    }

    @Override
    public void onRequestSuccess(BttvResponse bttvResponse) {
        if (bttvResponse.getStatus() == null || !bttvResponse.getStatus().equals("200")) {
            Logger.error("Bad bttv server status code: " + bttvResponse.getStatus());
            return;
        }

        String templateUrl = bttvResponse.getUrlTemplate();
        if (TextUtils.isEmpty(templateUrl)) {
            Logger.error("Empty templateUrl");
            return;
        }
        if (templateUrl.startsWith("//"))
            templateUrl = "https:" + templateUrl;

        List<BttvEmoteResponse> emoticons = bttvResponse.getBttvEmotes();
        if (emoticons == null || emoticons.isEmpty()) {
            Logger.error("Empty set");
            return;
        }

        for (BttvEmoteResponse emoticon : emoticons) {
            if (emoticon == null)
                continue;

            if (TextUtils.isEmpty(emoticon.getId())) {
                continue;
            }

            if (TextUtils.isEmpty(emoticon.getCode())) {
                continue;
            }

            addEmote(new BttvEmote(emoticon.getCode(), templateUrl, emoticon.getId(), emoticon.getImageType()));
        }
    }
}
