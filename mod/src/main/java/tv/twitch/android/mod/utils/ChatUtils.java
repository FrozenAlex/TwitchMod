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

    private static SpannableString getEmoteSpan(final String word, final int channelID, final ChatMessageFactory factory) {
        Emote emote = sEmotesManager.getEmote(word, channelID);
        if (emote != null) {
            if (emote.isGif() && PrefManager.isDontLoadGifs())
                return null;

            return (SpannableString) factory.getSpannedEmote(emote.getUrl());
        }

        return null;
    }

    private static List<Pair<Integer, Integer>> getPositions(final Spanned message) {
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

    public static SpannedString injectEmotesSpan(SpannedString orgMessage, int channelID, ChatMessageFactory factory) {
        if (TextUtils.isEmpty(orgMessage)) {
            Logger.warning("empty orgMessage");
            return orgMessage;
        }
        if (channelID == 0) {
            Logger.error("Bad channelID");
            return orgMessage;
        }

        SpannableStringBuilder ssb = null;

        for(Pair<Integer, Integer> pos : getPositions(orgMessage)) {
            SpannableString ss = getEmoteSpan(String.valueOf(orgMessage.subSequence(pos.first, pos.second)), channelID, factory);
            if (ss != null) {
                if (ssb == null)
                    ssb = new SpannableStringBuilder(orgMessage);

                ssb.replace(pos.first, pos.second, ss);
            }
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
        roomEmoticonSet.emoticonSetId = -106;
        roomEmoticonSet.emoticons = roomEmoticons != null ? roomEmoticons : new ChatEmoticon[0];
        newSet[newSet.length-1] = roomEmoticonSet;

        ChatEmoticonSet globalEmoticonSet = new ChatEmoticonSet();
        globalEmoticonSet.emoticonSetId = -107;
        globalEmoticonSet.emoticons = globalEmoticons != null ? globalEmoticons : new ChatEmoticon[0];
        newSet[newSet.length-2] = globalEmoticonSet;

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
