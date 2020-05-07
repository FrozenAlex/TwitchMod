package tv.twitch.android.mod.emotes;


import android.text.TextUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import retrofit2.Call;
import tv.twitch.android.mod.bridges.ApiCallback;
import tv.twitch.android.mod.models.Emote;
import tv.twitch.android.mod.models.EmoteSet;
import tv.twitch.android.mod.utils.Logger;


public abstract class BaseEmoteSet<T> extends ApiCallback<T> implements EmoteSet {
    private final Map<String, Emote> mEmoteMap = Collections.synchronizedMap(new LinkedHashMap<String, Emote>());
    private int retryCount = 0;

    @Override
    public synchronized void addEmote(Emote emote) {
        if (emote == null) {
            Logger.error("emote is null");
            return;
        }
        if (TextUtils.isEmpty(emote.getCode())) {
            Logger.error("Empty code");
            return;
        }

        mEmoteMap.put(emote.getCode(), emote);
    }

    @Override
    public Emote getEmote(String name) {
        return mEmoteMap.get(name);
    }

    @Override
    public Collection<Emote> getEmotes() {
        return mEmoteMap.values();
    }

    @Override
    public boolean isEmpty() {
        return mEmoteMap.isEmpty();
    }

    @Override
    public void onRequestFail(Call<T> call, FailReason reason) {
        if (reason == FailReason.NOT_FOUND) {
            Logger.debug("NOT_FOUND: " + call.request().toString());
            return;
        }

        if (retryCount < 3) {
            retryCount++;
            Logger.debug("Retrying... retryCount = " + retryCount);
            retry(call);
        }
    }
}
