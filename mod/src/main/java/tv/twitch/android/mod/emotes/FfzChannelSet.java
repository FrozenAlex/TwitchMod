package tv.twitch.android.mod.emotes;


import android.text.TextUtils;

import java.util.List;

import retrofit2.Call;
import tv.twitch.android.mod.models.FfzEmoteModel;
import tv.twitch.android.mod.models.api.FailReason;
import tv.twitch.android.mod.models.api.FfzEmoteResponse;
import tv.twitch.android.mod.utils.Logger;

import static tv.twitch.android.mod.net.ServiceFactory.getBttvApi;


public class FfzChannelSet extends BaseChannelSet<List<FfzEmoteResponse>> {

    public FfzChannelSet(int channelId) {
        super(channelId);
    }

    @Override
    public void fetch() {
        getBttvApi().getFfzEmotes(getChannelId()).enqueue(this);
    }

    @Override
    public void onRequestSuccess(List<FfzEmoteResponse> ffzResponse) {
        for (FfzEmoteResponse emoteResponse : ffzResponse) {
            if (emoteResponse == null)
                continue;

            if (emoteResponse.getImages() == null)
                continue;

            if (emoteResponse.getImages().isEmpty())
                continue;

            if (TextUtils.isEmpty(emoteResponse.getId()))
                continue;

            FfzEmoteModel emote = new FfzEmoteModel(emoteResponse.getCode(), emoteResponse.getId(), emoteResponse.getImages());
            addEmote(emote);
        }
    }


    @Override
    public void onRequestFail(Call<List<FfzEmoteResponse>> call, FailReason failReason) {
        Logger.debug("channelId=" + getChannelId() + ", reason="+failReason.toString());
    }
}
