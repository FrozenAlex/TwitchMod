package tv.twitch.android.mod.emotes;


import android.text.TextUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import tv.twitch.android.mod.bridges.ApiCallback;
import tv.twitch.android.mod.models.Emote;
import tv.twitch.android.mod.models.EmoteSet;


public abstract class BaseEmoteSet<T> extends ApiCallback<T> implements EmoteSet {
    private final Map<String, Emote> mEmoteMap = Collections.synchronizedMap(new LinkedHashMap<String, Emote>());


    @Override
    public void addEmote(Emote emote) {
        if (emote == null)
            return;

        if (TextUtils.isEmpty(emote.getCode()))
            return;

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
}
