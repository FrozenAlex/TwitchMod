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
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import tv.twitch.android.mod.bridges.ChatMessageFactory;
import tv.twitch.android.mod.bridges.ContextHelper;
import tv.twitch.android.mod.emotes.EmotesManager;
import tv.twitch.android.mod.models.Emote;
import tv.twitch.android.mod.settings.PrefManager;
import tv.twitch.android.models.chat.MessageToken;
import tv.twitch.android.models.communitypoints.ActiveClaimModel;
import tv.twitch.android.shared.chat.communitypoints.models.CommunityPointsModel;
import tv.twitch.chat.ChatEmoticon;
import tv.twitch.chat.ChatEmoticonSet;
import tv.twitch.chat.ChatEmoticonUrl;

public class ChatUtils {
    private static final Set<String> mClaims = Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());

    private static final EmotesManager sEmotesManager = EmotesManager.getInstance();
    private static final Helper sHelper = Helper.getInstance();
    private static final Class clickableUsernameSpan = tv.twitch.a.m.e.y0.f.class;


    public static int findMsgStartPos(Spanned orgMessage) {
        int startMessagePos = -1;

        Object[] spans = orgMessage.getSpans(0, orgMessage.length(), clickableUsernameSpan);
        if (spans != null && spans.length >= 1) {
            startMessagePos = orgMessage.getSpanEnd(spans[0]);
        }

        if (startMessagePos == -1) {
            startMessagePos = TextUtils.indexOf(orgMessage, ": ");
        }

        if (startMessagePos != -1) {
            if (startMessagePos + 3 < orgMessage.length()) {
                if (orgMessage.charAt(startMessagePos) == ':' && orgMessage.charAt(startMessagePos + 1) == ' ')
                    startMessagePos += 2;
                else if (orgMessage.charAt(startMessagePos) == ' ')
                    startMessagePos += 1;
            }
        }

        return startMessagePos;
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
            else {
                Logger.debug("Check token: " + messageToken.toString());
            }
        }

        return stringBuilder.toString();
    }

    public static SpannedString injectCopySpan(SpannedString spannedString, final List<MessageToken> tokens, final ContextHelper contextHelper) {
        if (TextUtils.isEmpty(spannedString)) {
            Logger.warning("spannedString is null");
            return spannedString;
        }
        if (tokens == null) {
            Logger.warning("tokens is null");
            return spannedString;
        }
        if (contextHelper == null) {
            Logger.error("contextHelper is null");
            return spannedString;
        }

        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(spannedString);
        spannableStringBuilder.setSpan(new LongClickableMessage(contextHelper, tokens), 0, spannedString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return SpannedString.valueOf(spannableStringBuilder);
    }

    public static void clicker(final View view, CommunityPointsModel communityPointsModel) {
        if (!PrefManager.isClickerOn())
            return;

        if (view == null) {
            Logger.error("view is null");
            return;
        }

        if (communityPointsModel == null) {
            Logger.error("communityPointsModel is null");
            return;
        }

        ActiveClaimModel claimModel = communityPointsModel.getClaim();
        if (claimModel == null) {
            Logger.error("claimModel is null");
            return;
        }
        if (TextUtils.isEmpty(claimModel.getId())) {
            Logger.error("Bad id");
            return;
        }

        synchronized (mClaims) {
            if (mClaims.contains(claimModel.getId())) {
                return;
            }
            mClaims.add(claimModel.getId());
        }

        view.performClick();
        Logger.info(String.format(Locale.ENGLISH, "Click! Got %d points", claimModel.getPointsEarned()));
    }

    private static SpannableStringBuilder checkAndInjectEmote(SpannableStringBuilder ssb, final Spanned orgMessage, final int startWordPos, final int endWordPos, final int channelID, final ChatMessageFactory factory) {
        Emote emote = sEmotesManager.getEmote(TextUtils.substring(orgMessage, startWordPos, endWordPos), channelID);
        if (emote != null) {
            if (emote.isGif() && PrefManager.isDontLoadGifs())
                return ssb;
            SpannableString emoteSpan = (SpannableString) factory.getSpannedEmote(emote.getUrl());
            if (ssb == null) {
                ssb = new SpannableStringBuilder(orgMessage);
            }
            ssb.replace(startWordPos, endWordPos, emoteSpan);
        }

        return ssb;
    }

    private static List<Pair<Integer, Integer>> getPositions(final Spanned message, int startPos) {
        List<Pair<Integer, Integer>> res = new ArrayList<>();

        boolean inWord = false;
        int endPos = message.length();
        for (int i = endPos-1; i>=startPos; i--) {
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
            if (i == startPos) {
                if (inWord) {
                    inWord = false;
                    res.add(new Pair<>(startPos, endPos));
                }
            }
        }

        return res;
    }

    public static Spanned injectEmotesSpan(Spanned orgMessage, int channelID, ChatMessageFactory factory) {
        // Logger.debug(String.format(Locale.ENGLISH, "msg: {{%s}}", orgMessage));

        if (orgMessage == null) {
            Logger.error("orgMessage is null");
            return orgMessage;
        }

        SpannableStringBuilder ssb = null;

        if (TextUtils.isEmpty(orgMessage))
            return orgMessage;

        int startPos = findMsgStartPos(orgMessage);
        if (startPos == -1) {
            Logger.debug(String.format(Locale.ENGLISH, "Message for debug: {{%s}}", orgMessage));
            startPos = 0;
        }

        for(Pair<Integer, Integer> pos : getPositions(orgMessage, startPos)) {
            ssb = checkAndInjectEmote(ssb, orgMessage, pos.first, pos.second, channelID, factory);
        }

        if (ssb != null)
            return SpannedString.valueOf(ssb);

        return orgMessage;
    }

    public static ChatEmoticonSet[] hookChatEmoticonSet(ChatEmoticonSet[] orgSet) {
        if (!PrefManager.isHookEmoticonSetOn()) {
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

        List<Emote> globalEmotes = sEmotesManager.getGlobalEmotes();
        List<Emote> roomEmotes = sEmotesManager.getRoomEmotes(sHelper.getCurrentChannel());

        ChatEmoticonSet[] newSet = new ChatEmoticonSet[orgSet.length+2];
        java.lang.System.arraycopy(orgSet, 0, newSet, 0, orgSet.length);

        ChatEmoticon[] globalEmoticons = emotesToChatEmoticonArr(globalEmotes);
        ChatEmoticon[] roomEmoticons = emotesToChatEmoticonArr(roomEmotes);

        ChatEmoticonSet roomEmoticonSet = new ChatEmoticonSet();
        roomEmoticonSet.emoticonSetId = -104;
        roomEmoticonSet.emoticons = roomEmoticons != null ? roomEmoticons : new ChatEmoticon[0];
        newSet[newSet.length-2] = roomEmoticonSet;

        ChatEmoticonSet globalEmoticonSet = new ChatEmoticonSet();
        globalEmoticonSet.emoticonSetId = -105;
        globalEmoticonSet.emoticons = globalEmoticons != null ? globalEmoticons : new ChatEmoticon[0];
        newSet[newSet.length-1] = globalEmoticonSet;

        return newSet;
    }

    public static Spanned addTimestampToMessage(Spanned spanned) {
        if (TextUtils.isEmpty(spanned))
            return spanned;

        if (!PrefManager.isTimestampsOn()) {
            return spanned;
        }

        return addTimestamp(spanned, new Date());
    }
}
