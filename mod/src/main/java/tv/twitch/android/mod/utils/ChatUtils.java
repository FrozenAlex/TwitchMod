package tv.twitch.android.mod.utils;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.util.Pair;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import tv.twitch.android.mod.bridges.ChatMessageFactory;
import tv.twitch.android.mod.bridges.LoaderLS;
import tv.twitch.android.mod.emotes.EmoteManager;
import tv.twitch.android.mod.models.Badge;
import tv.twitch.android.mod.models.Emote;
import tv.twitch.android.models.chat.MessageToken;
import tv.twitch.chat.ChatEmoticon;
import tv.twitch.chat.ChatEmoticonSet;
import tv.twitch.chat.ChatEmoticonUrl;

public class ChatUtils {
    public enum EmoteSet {
        GLOBAL("-110"),
        FFZ("-109"),
        BTTV("-108");

        private final String mId;

        EmoteSet(String id) {
            this.mId = id;
        }

        public String getId() {
            return mId;
        }
    }

    private static ChatEmoticon[] emotesToChatEmoticonArr(List<Emote> emoteList) {
        if (emoteList == null)
            return null;

        ChatEmoticon[] chatEmoticons = new ChatEmoticon[emoteList.size()];

        int i = 0;
        for (Emote emote : emoteList) {
            chatEmoticons[i++] = new ChatEmoticonUrl("-1", emote.getCode(), false, emote.getUrl());
        }

        return chatEmoticons;
    }

    private static Spanned addTimestamp(Spanned spanned, Date date) {
        SpannableString dateString = SpannableString.valueOf(new SimpleDateFormat("HH:mm ", Locale.UK).format(date));
        dateString.setSpan(new RelativeSizeSpan(0.75f), 0, dateString.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        SpannableStringBuilder message = new SpannableStringBuilder(dateString);
        message.append(new SpannableStringBuilder(spanned));
        return SpannableString.valueOf(message);
    }

    public static String getMessage(List<MessageToken> tokens) {
        StringBuilder stringBuilder = new StringBuilder();
        for (MessageToken messageToken : tokens) {
            if (messageToken instanceof MessageToken.TextToken)
                stringBuilder.append(((MessageToken.TextToken) messageToken).getText());
            else if (messageToken instanceof MessageToken.EmoticonToken)
                stringBuilder.append(((MessageToken.EmoticonToken) messageToken).getText());
            else if (messageToken instanceof MessageToken.MentionToken)
                stringBuilder.append(((MessageToken.MentionToken) messageToken).getText());
            else if (messageToken instanceof MessageToken.UrlToken)
                stringBuilder.append(((MessageToken.UrlToken) messageToken).getUrl());
        }

        return stringBuilder.toString();
    }

    public static SpannedString injectBadges(SpannedString badgeSpan, final String userName, final ChatMessageFactory factory) {
        if (TextUtils.isEmpty(userName)) {
            Logger.warning("Empty userName");
            return badgeSpan;
        }

        if (factory == null) {
            Logger.error("factory is null");
            return badgeSpan;
        }

        List<Badge> badges = LoaderLS.getInstance().getBadgeManager().getBadges(userName);
        if (badges == null || badges.size() == 0)
            return badgeSpan;

        SpannableStringBuilder ssb = new SpannableStringBuilder(badgeSpan);
        for (Badge badge : badges) {
            if (badge == null)
                continue;

            String url = badge.getUrl();
            if (TextUtils.isEmpty(url)) {
                Logger.warning("Empty url");
                continue;
            }

            String badgeName = badge.getName();
            if (TextUtils.isEmpty(badgeName)) {
                Logger.warning("Empty badge name");
                continue;
            }

            if (ssb.charAt(ssb.length()-1) != ' ')
                ssb.append(' ');
            ssb.append(badgeName);

            ssb.replace(ssb.length()-badgeName.length(), ssb.length(), factory.getSpannedBadge(url, badgeName));
        }

        return SpannedString.valueOf(ssb);
    }

    public static SpannedString injectCopySpan(SpannedString messageSpan, final List<MessageToken> tokens) {
        if (TextUtils.isEmpty(messageSpan)) {
            Logger.warning("Empty messageSpan");
            return messageSpan;
        }
        if (tokens == null) {
            Logger.warning("tokens is null");
            return messageSpan;
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(messageSpan);
        spannableStringBuilder.setSpan(new LongClickableMessage(tokens), 0, messageSpan.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return SpannedString.valueOf(spannableStringBuilder);
    }

    private static SpannableString getEmoteSpan(String word, int channelID, ChatMessageFactory factory) {
        if (TextUtils.isEmpty(word)) {
            Logger.warning("Empty word");
            return null;
        }

        if (factory == null) {
            Logger.error("factory is null");
            return null;
        }

        Emote emote = LoaderLS.getInstance().getEmoteManager().getEmote(word, channelID);
        if (emote != null) {
            if (emote.isGif() && LoaderLS.getInstance().getPrefManager().isDontLoadGifs())
                return null;

            return (SpannableString) factory.getSpannedEmote(emote.getUrl(), word);
        }

        return null;
    }

    private static List<Pair<Integer, Integer>> getPositions(Spanned message) {
        if (message == null) {
            Logger.warning("message is null");
            return new ArrayList<>();
        }

        List<Pair<Integer, Integer>> res = new ArrayList<>();

        boolean inWord = false;
        int endPos = message.length();
        for (int i = endPos-1; i >= 0; i--) {
            final boolean isSpace = message.charAt(i) == ' ';

            if (isSpace) {
                if (inWord) {
                    inWord = false;
                    res.add(new Pair<>(i+1, endPos));
                }
            } else {
                if (!inWord) {
                    inWord = true;
                    endPos = i + 1;
                }
            }
            if (i == 0) {
                if (inWord) {
                    inWord = false;
                    res.add(new Pair<>(0, endPos));
                }
            }
        }

        return res;
    }

    public static SpannedString injectEmotesSpan(SpannedString messageSpan, int channelID, ChatMessageFactory factory) {
        if (TextUtils.isEmpty(messageSpan)) {
            Logger.warning("Empty messageSpan");
            return messageSpan;
        }

        if (factory == null) {
            Logger.error("factory is null");
            return null;
        }

        SpannableStringBuilder ssb = null;

        for(Pair<Integer, Integer> pos : getPositions(messageSpan)) {
            SpannableString emoteSpan = getEmoteSpan(String.valueOf(messageSpan.subSequence(pos.first, pos.second)), channelID, factory);
            if (emoteSpan != null) {
                if (ssb == null)
                    ssb = new SpannableStringBuilder(messageSpan);

                ssb.replace(pos.first, pos.second, emoteSpan);
            }
        }

        if (ssb != null)
            return SpannedString.valueOf(ssb);

        return messageSpan;
    }

    public static ChatEmoticonSet[] hookChatEmoticonSet(ChatEmoticonSet[] orgSet) {
        if (!LoaderLS.getInstance().getPrefManager().isHookEmoticonSetOn()) {
            return orgSet;
        }

        if (orgSet == null) {
            Logger.warning("orgSet is null");
            return orgSet;
        }
        if (orgSet.length == 0) {
            Logger.warning("empty orgSet");
            return orgSet;
        }

        EmoteManager emoteManager = LoaderLS.getInstance().getEmoteManager();
        Helper helper = LoaderLS.getInstance().getHelper();
        List<Emote> globalEmotes = emoteManager.getGlobalEmotes();
        List<Emote> bttvEmotes = emoteManager.getBttvEmotes(helper.getCurrentChannel());
        List<Emote> ffzEmotes = emoteManager.getFfzEmotes(helper.getCurrentChannel());

        ChatEmoticonSet[] newSet = new ChatEmoticonSet[orgSet.length+3];
        java.lang.System.arraycopy(orgSet, 0, newSet, 0, orgSet.length);

        ChatEmoticon[] globalEmoticons = emotesToChatEmoticonArr(globalEmotes);
        ChatEmoticon[] bttvEmoticons = emotesToChatEmoticonArr(bttvEmotes);
        ChatEmoticon[] ffzEmoticons = emotesToChatEmoticonArr(ffzEmotes);

        ChatEmoticonSet bttvEmoticonSet = new ChatEmoticonSet();
        bttvEmoticonSet.emoticonSetId = EmoteSet.BTTV.getId();
        bttvEmoticonSet.emoticons = bttvEmoticons != null ? bttvEmoticons : new ChatEmoticon[0];
        newSet[newSet.length-1] = bttvEmoticonSet;

        ChatEmoticonSet ffzEmoticonSet = new ChatEmoticonSet();
        ffzEmoticonSet.emoticonSetId = EmoteSet.FFZ.getId();
        ffzEmoticonSet.emoticons = ffzEmoticons != null ? ffzEmoticons : new ChatEmoticon[0];
        newSet[newSet.length-2] = ffzEmoticonSet;

        ChatEmoticonSet globalEmoticonSet = new ChatEmoticonSet();
        globalEmoticonSet.emoticonSetId = EmoteSet.GLOBAL.getId();
        globalEmoticonSet.emoticons = globalEmoticons != null ? globalEmoticons : new ChatEmoticon[0];
        newSet[newSet.length-3] = globalEmoticonSet;

        return newSet;
    }

    public static Spanned addTimestampToMessage(Spanned spanned) {
        if (!LoaderLS.getInstance().getPrefManager().isTimestampsOn()) {
            return spanned;
        }

        if (TextUtils.isEmpty(spanned)) {
            Logger.warning("Empty spanned");
            return spanned;
        }

        return addTimestamp(spanned, new Date());
    }
}
