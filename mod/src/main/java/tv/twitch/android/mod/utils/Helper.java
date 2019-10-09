package tv.twitch.android.mod.utils;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.SpannedString;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tv.twitch.android.mod.bridges.ChatMessageFactory;
import tv.twitch.android.mod.emotes.EmotesManager;
import tv.twitch.android.mod.models.Emote;
import tv.twitch.chat.ChatEmoticon;
import tv.twitch.chat.ChatEmoticonSet;
import tv.twitch.chat.ChatEmoticonUrl;

public class Helper {
    private static final String LOG_TAG = Helper.class.getName();

    private int currentChannel = 0;

    private Helper() {
    }

    public static Spanned injectEmotes(Spanned orgMessage, int channelID, ChatMessageFactory factory) {
        EmotesManager emotesManager = EmotesManager.getInstance();
        if (emotesManager == null) {
            Log.e(LOG_TAG, "emotesManger is null");
            return orgMessage;
        }
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
                        final Emote emote = emotesManager.getEmote(msg.substring(startWordPos, endWordPos), channelID);
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

    private static ChatEmoticon[] convert(List<Emote> emoteList) {
        if (emoteList == null)
            return null;

        ChatEmoticon[] chatEmoticons = new ChatEmoticon[emoteList.size()];

        int i = 0;
        for (Emote emote : emoteList) {
            chatEmoticons[i++] = new ChatEmoticonUrl(emote.getCode(), emote.getUrl());
        }

        return chatEmoticons;
    }

    public static ChatEmoticonSet[] injectEmotes(ChatEmoticonSet[] orgArr) {
        Log.d(LOG_TAG, "widget");
        if (orgArr == null || orgArr.length == 0)
            return orgArr;

        Log.d(LOG_TAG, "orgArr length: " + orgArr.length);
        List<ChatEmoticonSet> sets = new ArrayList<>(Arrays.asList(orgArr));

        try {
            List<Emote> globalEmotes = EmotesManager.getInstance().getGlobalEmotes();
            if (globalEmotes != null && globalEmotes.size() > 0) {
                Log.d(LOG_TAG, "G-emotes:" + globalEmotes.toString());
                ChatEmoticonSet set = new ChatEmoticonSet();
                set.emoticonSetId = -799;
                ChatEmoticon[] arr = convert(globalEmotes);
                set.emoticons = arr != null ? arr : new ChatEmoticon[0];
                sets.add(set);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            int channelId = getInstance().getCurrentChannel();
            Log.d(LOG_TAG, "channelId: " + channelId);
            if (channelId != 0) {
                List<Emote> roomEmotes = EmotesManager.getInstance().getEmotes(channelId);
                if (roomEmotes != null && roomEmotes.size() > 0) {
                    Log.d(LOG_TAG, "R-emotes:" + roomEmotes.toString());
                    ChatEmoticonSet set = new ChatEmoticonSet();
                    set.emoticonSetId = -800;
                    ChatEmoticon[] arr = convert(roomEmotes);
                    set.emoticons = arr != null ? arr : new ChatEmoticon[0];
                    sets.add(set);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ChatEmoticonSet[] ret = sets.toArray(new ChatEmoticonSet[0]);
        Log.d(LOG_TAG, String.format("ret size: %d", ret.length));
        return ret;
    }

    private static class Holder {
        static final Helper instance = new Helper();
    }

    public static Helper getInstance() {
        return Holder.instance;
    }

    public void setCurrentChannel(int currentChannel) {
        Log.d(LOG_TAG, "Set currentChannel=" + currentChannel);
        this.currentChannel = currentChannel;
    }

    public int getCurrentChannel() {
        return this.currentChannel;
    }
}
