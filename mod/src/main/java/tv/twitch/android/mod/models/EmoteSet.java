package tv.twitch.android.mod.models;

public interface EmoteSet {
    void addEmote(Emote emote);

    Emote getEmote(String name);

    void fetch();
}
