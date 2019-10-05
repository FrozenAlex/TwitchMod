package tv.twitch.android.mod.utils;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.SpannedString;
import android.util.Log;

import tv.twitch.android.mod.emotes.EmotesManager;
import tv.twitch.android.mod.bridges.ChatMessageFactory;
import tv.twitch.android.mod.models.Emote;

public class MessageUtils {

    public static Spanned injectEmotes(Spanned orgMessage, int channelID, ChatMessageFactory factory) {
        try {
            final SpannableStringBuilder ssb = new SpannableStringBuilder(orgMessage);

            final String msg = ssb.toString();

            int startMessagePos = msg.indexOf(": ");
            if (startMessagePos == -1) {
                return orgMessage;
            }

            boolean newWord = false;
            int endWordPos = msg.length();
            for (int i = msg.length() - 1; i >= startMessagePos; i--) {
                if (msg.charAt(i) != ' ') {
                    if (!newWord) {
                        newWord = true;
                        endWordPos = i + 1;
                    }
                } else {
                    if (newWord) {
                        newWord = false;
                        int startWordPos = i + 1;
                        final Emote emote = EmotesManager.getInstance().getEmote(msg.substring(startWordPos, endWordPos), channelID);
                        if (emote != null) {
                            CharSequence emoteSpan = factory.getSpannedEmote(emote.getUrl());
                            ssb.replace(startWordPos, endWordPos, emoteSpan);
                        }
                    }
                }
            }

            return SpannedString.valueOf(ssb);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return orgMessage;
    }

    public static void printMessage(CharSequence charSequence) {
        Log.i("message", String.valueOf(charSequence));
    }
}
