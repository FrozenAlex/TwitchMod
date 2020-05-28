package tv.twitch.android.mod.bridges;


public interface IChatMessageFactory {
    CharSequence getSpannedEmote(String url, String emoteText);
}
