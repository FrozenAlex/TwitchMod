package tv.twitch.android.mod.emotes;

import android.text.TextUtils;

import java.util.List;

import tv.twitch.android.mod.models.api.BttvChannelResponse;
import tv.twitch.android.mod.models.api.BttvEmoteResponse;
import tv.twitch.android.mod.utils.Logger;

import static tv.twitch.android.mod.net.ServiceFactory.getBttvApi;

public class BttvChannelEmoteSet extends BaseChannelEmoteSet<BttvChannelResponse> {
    BttvChannelEmoteSet(int channelId) {
        super(channelId);
    }

    @Override
    public void fetch() {
        getBttvApi().getChannelEmotes(getChannelId()).enqueue(this);
    }

    @Override
    public void onRequestSuccess(BttvChannelResponse bttvResponse) {
        if (bttvResponse == null)  {
            Logger.error("bttvResponse is null");
            return;
        }

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
                    Logger.debug("getImageType() is null");
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

                addEmote(new tv.twitch.android.mod.models.BttvEmote(emoticon.getCode(), emoticon.getId(), emoticon.getImageType()));
            }
        }
    }
}
