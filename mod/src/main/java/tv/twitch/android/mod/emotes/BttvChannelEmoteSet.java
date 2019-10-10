package tv.twitch.android.mod.emotes;

import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import tv.twitch.android.mod.bridges.ApiCallback;
import tv.twitch.android.mod.models.BttvEmote;
import tv.twitch.android.mod.models.Emote;
import tv.twitch.android.mod.models.EmoteSet;
import tv.twitch.android.mod.models.api.BttvEmoteResponse;
import tv.twitch.android.mod.models.api.BttvResponse;

import static tv.twitch.android.mod.net.ServiceFactory.getBttvApi;

public class BttvChannelEmoteSet extends ApiCallback<BttvResponse> implements EmoteSet {
    private static final String LOG_TAG = BttvChannelEmoteSet.class.getName();

    private final LinkedHashMap<String, Emote> mEmoteMap = new LinkedHashMap<>();
    private final String mChannelName;

    public BttvChannelEmoteSet(String channelName) {
        this.mChannelName = channelName;
    }

    @Override
    public synchronized void addEmote(Emote emote) {
        if (emote != null && !TextUtils.isEmpty(emote.getCode()))
            mEmoteMap.put(emote.getCode(), emote);
        else {
            Log.d(LOG_TAG, "Bad emote: " + emote);
        }
    }

    @Override
    public Emote getEmote(String name) {
        return mEmoteMap.get(name);
    }

    @Override
    public void fetch() {
        getBttvApi().getChannelEmotes(mChannelName).enqueue(this);
    }

    @Override
    public void onRequestSuccess(BttvResponse bttvResponse) {
        if (bttvResponse.getStatus() == null || !bttvResponse.getStatus().equals("200")) {
            Log.e(LOG_TAG, "Bad status: " + bttvResponse.getStatus());
            return;
        }

        String templateUrl = bttvResponse.getUrlTemplate();
        if (templateUrl == null || templateUrl.isEmpty()) {
            Log.e(LOG_TAG, "Bad templateUrl: " + templateUrl);
            return;
        }
        if (templateUrl.startsWith("//"))
            templateUrl = "https:" + templateUrl;

        List<BttvEmoteResponse> emoticons = bttvResponse.getBttvEmotes();
        if (emoticons == null || emoticons.isEmpty()) {
            Log.e(LOG_TAG, "Empty channel set");
            return;
        }

        for (BttvEmoteResponse emoticon : emoticons) {
            if (emoticon == null)
                continue;

            if (TextUtils.isEmpty(emoticon.getId())) {
                continue;
            }

            if (TextUtils.isEmpty(emoticon.getCode())) {
                Log.w(LOG_TAG, "Bad emote " + emoticon.getId() + ": empty code");
                continue;
            }

            addEmote(new BttvEmote(emoticon.getCode(), templateUrl, emoticon.getId(), emoticon.getImageType()));
        }

        Log.d(LOG_TAG, "res: " + mEmoteMap.toString());
    }

    @Override
    public void onRequestFail(FailReason reason) {
        Log.e(LOG_TAG, "requestError");
    }

    @Override
    public List<Emote> getEmotes() {
        return new ArrayList<>(mEmoteMap.values());
    }
}
