package tv.twitch.android.mod.emotes;


import android.text.TextUtils;

import java.util.List;

import retrofit2.Call;
import tv.twitch.android.mod.models.BttvEmoteModel;
import tv.twitch.android.mod.models.api.BttvEmoteResponse;
import tv.twitch.android.mod.models.api.FailReason;
import tv.twitch.android.mod.utils.Logger;

import static tv.twitch.android.mod.net.ServiceFactory.getBttvApi;


public class BttvGlobalSet extends BaseEmoteSet<List<BttvEmoteResponse>> {

    @Override
    public synchronized void fetch() {
        getBttvApi().getGlobalEmotes().enqueue(this);
    }

    @Override
    public void onRequestSuccess(List<BttvEmoteResponse> bttvResponse) {
        for (BttvEmoteResponse emoticon : bttvResponse) {
            if (emoticon == null)
                continue;

            if (TextUtils.isEmpty(emoticon.getId())) {
                continue;
            }

            if (TextUtils.isEmpty(emoticon.getCode())) {
                continue;
            }

            if (emoticon.getImageType() == null) {
                Logger.debug("getImageType() is null");
                continue;
            }

            addEmote(new BttvEmoteModel(emoticon.getCode(), emoticon.getId(), emoticon.getImageType()));
        }
    }

    @Override
    public void onRequestFail(Call<List<BttvEmoteResponse>> call, FailReason failReason) {
        Logger.debug("reason="+failReason.toString());
    }
}
