package tv.twitch.android.mod.emotes;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import tv.twitch.android.mod.models.BttvEmote;
import tv.twitch.android.mod.models.Emote;
import tv.twitch.android.mod.models.EmoteSet;
import tv.twitch.android.mod.models.api.BttvEmoteResponse;
import tv.twitch.android.mod.models.api.BttvResponse;
import tv.twitch.android.mod.bridges.ApiCallback;
import tv.twitch.android.mod.utils.Logger;

import static tv.twitch.android.mod.net.ServiceFactory.getBttvApi;

public class BttvGlobalEmoteSet extends ApiCallback<BttvResponse> implements EmoteSet {
    private final Map<String, Emote> mEmoteMap = Collections.synchronizedMap(new LinkedHashMap<String, Emote>());

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
        getBttvApi().getGlobalEmotes().enqueue(this);
    }

    @Override
    public void onRequestSuccess(BttvResponse bttvResponse) {
        if (bttvResponse.getStatus() == null || !bttvResponse.getStatus().equals("200")) {
            Logger.error("Bad status: " + bttvResponse.getStatus());
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
            Logger.error("Empty global set");
            return;
        }

        for (BttvEmoteResponse emoticon : emoticons) {
            if (emoticon == null)
                continue;

            if (TextUtils.isEmpty(emoticon.getId())) {
                continue;
            }

            if (TextUtils.isEmpty(emoticon.getCode())) {
                Logger.warning("Bad emote " + emoticon.getId() + ": empty code");
                continue;
            }

            addEmote(new BttvEmote(emoticon.getCode(), templateUrl, emoticon.getId(), emoticon.getImageType()));
        }

        Logger.debug("res: " + mEmoteMap.toString());
    }

    @Override
    public void onRequestFail(FailReason reason) {
        Logger.error(reason.name());
    }

    @Override
    public List<Emote> getEmotes() {
        return new ArrayList<>(mEmoteMap.values());
    }
}
