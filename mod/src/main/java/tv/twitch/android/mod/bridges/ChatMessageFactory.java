package tv.twitch.android.mod.bridges;

public interface ChatMessageFactory {
    CharSequence getSpannedEmote(String url, String emoteText);

    CharSequence getSpannedBadge(String url, String badgeName);
}
