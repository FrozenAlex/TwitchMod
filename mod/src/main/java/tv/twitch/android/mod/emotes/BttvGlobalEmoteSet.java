package tv.twitch.android.mod.emotes;

import android.text.TextUtils;

import java.util.List;

import tv.twitch.android.mod.models.api.BttvEmoteResponse;
import tv.twitch.android.mod.utils.Logger;

import static tv.twitch.android.mod.net.ServiceFactory.getBttvApi;

public class BttvGlobalEmoteSet extends BaseEmoteSet<List<BttvEmoteResponse>> {
    @Override
    public void fetch() {
        getBttvApi().getGlobalEmotes().enqueue(this);
    }

    @Override
    public void onRequestSuccess(List<BttvEmoteResponse> bttvResponse) {
        if (bttvResponse == null) {
            Logger.error("bttvResponse is null");
            return;
        }

        for (BttvEmoteResponse emoticon : bttvResponse) {
            if (emoticon == null)
                continue;

            if (TextUtils.isEmpty(emoticon.getId())) {
                continue;
            }

            if (TextUtils.isEmpty(emoticon.getCode())) {
                continue;
            }

            addEmote(new tv.twitch.android.mod.models.BttvEmote(emoticon.getCode(), emoticon.getId(), emoticon.getImageType()));
        }
    }
}
