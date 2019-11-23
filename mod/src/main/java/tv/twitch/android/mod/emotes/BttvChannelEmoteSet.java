package tv.twitch.android.mod.emotes;

import android.text.TextUtils;

import java.util.List;

import tv.twitch.android.mod.models.BttvEmote;
import tv.twitch.android.mod.models.api.BttvEmoteResponse;
import tv.twitch.android.mod.models.api.BttvResponse;
import tv.twitch.android.mod.utils.Logger;

import static tv.twitch.android.mod.net.ServiceFactory.getBttvApi;

public class BttvChannelEmoteSet extends BaseEmoteSet<BttvResponse> {
    public BttvChannelEmoteSet(String channelName) {
        super(channelName);
    }

    @Override
    public void fetch() {
        getBttvApi().getChannelEmotes(getChannelName()).enqueue(this);
    }

    @Override
    public void onRequestSuccess(BttvResponse bttvResponse) {
        if (bttvResponse.getStatus() == null || !bttvResponse.getStatus().equals("200")) {
            Logger.error("Bad bttv server status code: " + bttvResponse.getStatus());
            return;
        }

        String templateUrl = bttvResponse.getUrlTemplate();
        if (templateUrl == null || templateUrl.isEmpty()) {
            Logger.error("Bad templateUrl: " + templateUrl);
            return;
        }
        if (templateUrl.startsWith("//"))
            templateUrl = "https:" + templateUrl;

        List<BttvEmoteResponse> emoticons = bttvResponse.getBttvEmotes();
        if (emoticons == null || emoticons.isEmpty()) {
            Logger.error("Empty channel set");
            return;
        }

        for (BttvEmoteResponse emoticon : emoticons) {
            if (emoticon == null)
                continue;

            if (emoticon.getId() == null || TextUtils.isEmpty(emoticon.getId())) {
                continue;
            }

            if (emoticon.getCode() == null || TextUtils.isEmpty(emoticon.getCode())) {
                continue;
            }

            addEmote(new BttvEmote(emoticon.getCode(), templateUrl, emoticon.getId(), emoticon.getImageType()));
        }
    }
}
