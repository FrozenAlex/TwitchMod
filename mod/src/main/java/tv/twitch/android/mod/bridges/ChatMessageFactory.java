package tv.twitch.android.mod.bridges;

import android.content.Context;

public interface ChatMessageFactory {
    CharSequence getSpannedEmote(String url);

    Context getContext();
}
