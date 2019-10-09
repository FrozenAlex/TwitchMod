package tv.twitch.android.mod.models;

import java.util.List;

public interface EmoteSet {
    void addEmote(Emote emote);

    Emote getEmote(String name);

    void fetch();

    List<Emote> getEmotes();
}
