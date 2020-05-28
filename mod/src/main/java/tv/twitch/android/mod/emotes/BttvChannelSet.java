package tv.twitch.android.mod.emotes;


import android.text.TextUtils;

import java.util.List;

import retrofit2.Call;
import tv.twitch.android.mod.models.api.BttvChannelResponse;
import tv.twitch.android.mod.models.api.BttvEmoteResponse;
import tv.twitch.android.mod.models.api.FailReason;
import tv.twitch.android.mod.utils.Logger;

import static tv.twitch.android.mod.net.ServiceFactory.getBttvApi;


public class BttvChannelSet extends BaseChannelSet<BttvChannelResponse> {

    BttvChannelSet(int channelId) {
        super(channelId);
    }

    @Override
    public void fetch() {
        if (isReadyForFetch()) {
            doCall(getBttvApi().getBttvEmotes(getChannelId()));
        } else {
            Logger.debug("Skip fetching");
        }
    }

    @Override
    public void onRequestSuccess(BttvChannelResponse bttvResponse) {
        List<BttvEmoteResponse> channelEmotes = bttvResponse.getChannelEmotes();
        if (channelEmotes != null) {
            for (BttvEmoteResponse emoticon : channelEmotes) {
                if (emoticon == null)
                    continue;

                if (TextUtils.isEmpty(emoticon.getId())) {
                    continue;
                }

                if (TextUtils.isEmpty(emoticon.getCode())) {
                    continue;
                }

                if (emoticon.getImageType() == null) {
                    continue;
                }

                addEmote(new tv.twitch.android.mod.models.BttvEmote(emoticon.getCode(), emoticon.getId(), emoticon.getImageType()));
            }
        }

        List<BttvEmoteResponse> sharedEmotes = bttvResponse.getSharedEmotes();
        if (sharedEmotes != null) {
            for (BttvEmoteResponse emoticon : sharedEmotes) {
                if (emoticon == null)
                    continue;

                if (TextUtils.isEmpty(emoticon.getId())) {
                    continue;
                }

                if (TextUtils.isEmpty(emoticon.getCode())) {
                    continue;
                }

                if (emoticon.getImageType() == null) {
                    continue;
                }

                addEmote(new tv.twitch.android.mod.models.BttvEmote(emoticon.getCode(), emoticon.getId(), emoticon.getImageType()));
            }
        }
    }

    @Override
    public void onRequestFail(Call<BttvChannelResponse> call, FailReason failReason) {
        Logger.debug("channelId=" + getChannelId() + ", reason="+failReason.toString());
    }
}
