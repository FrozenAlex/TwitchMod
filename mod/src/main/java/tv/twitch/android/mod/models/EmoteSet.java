package tv.twitch.android.mod.models;


import java.util.Collection;


public interface EmoteSet {
    void addEmote(Emote emote);

    Emote getEmote(String name);

    Collection<Emote> getEmotes();

    boolean isEmpty();

    void clear();
}
