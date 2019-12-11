package tv.twitch.android.mod.utils;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import tv.twitch.a.m.e.e;
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

    public static int findMsgStartPos(Spanned orgMessage) {
        int startMessagePos = -1;

        Object[] spans = orgMessage.getSpans(0, orgMessage.length(), tv.twitch.a.m.e.y0.f.class);
        if (spans != null && spans.length >= 1) {
            startMessagePos = orgMessage.getSpanEnd(spans[0]);
        }

        if (startMessagePos != -1) {
            if (startMessagePos + 3 < orgMessage.length()) {
                if (orgMessage.charAt(startMessagePos) == ':' && orgMessage.charAt(startMessagePos+1) == ' ')
                    startMessagePos += 2;
                else if (orgMessage.charAt(startMessagePos) == ' ')
                    startMessagePos += 1;
            }
        }

        return startMessagePos;
    }

    public static ChatEmoticon[] emotesToChatEmoticonArr(List<Emote> emoteList) {
        if (emoteList == null)
            return null;

        ChatEmoticon[] chatEmoticons = new ChatEmoticon[emoteList.size()];

        int i = 0;
        for (Emote emote : emoteList) {
            chatEmoticons[i++] = new ChatEmoticonUrl(emote.getCode(), emote.getUrl());
        }

        return chatEmoticons;
    }

    public static Spanned addTimestamp(Spanned spanned) {
        SpannableString dateString = SpannableString.valueOf(new SimpleDateFormat("HH:mm ", Locale.UK).format(new Date()));
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
                Logger.debug("unToken: " + messageToken.toString());
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
        spannableStringBuilder.setSpan(new ClickableMessage(contextHelper, tokens), 0, spannedString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

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
            Logger.error("Bad claim id");
            return;
        }

        synchronized (mClaims) {
            if (mClaims.contains(claimModel.getId())) {
                return;
            }
            mClaims.add(claimModel.getId());
        }

        Logger.info(String.format(Locale.ENGLISH, "Click! Got %d points", claimModel.getPointsEarned()));
        view.performClick();
    }

    public static SpannableStringBuilder checkAndInjectEmote(SpannableStringBuilder ssb, final Spanned orgMessage, final int startWordPos, final int endWordPos, final int channelID, final EmotesManager manager, final ChatMessageFactory factory) {
        Emote emote = manager.getEmote(TextUtils.substring(orgMessage, startWordPos, endWordPos), channelID);
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


    public static Spanned injectEmotesSpan(Spanned orgMessage, int channelID, ChatMessageFactory factory, e chatMessageInterface) {
        // Logger.debug(String.format(Locale.ENGLISH, "msg: {{%s}}", orgMessage));

        if (orgMessage == null) {
            Logger.error("orgMessage is null");
            return orgMessage;
        }
        if (factory == null) {
            Logger.error("factory is null");
            return orgMessage;
        }

        EmotesManager emotesManager = EmotesManager.getInstance();
        if (emotesManager == null) {
            Logger.error("emotesManager is null");
            return orgMessage;
        }

        SpannableStringBuilder ssb = null;

        if (TextUtils.isEmpty(orgMessage))
            return orgMessage;

        int startPos = findMsgStartPos(orgMessage);
        if (startPos == -1) {
            Logger.debug(String.format(Locale.ENGLISH, "Msg: {{%s}}, isAction: %s", orgMessage, chatMessageInterface.isAction()));
            startPos = 0;
        }

        boolean inWord = false;
        int endPos = orgMessage.length();
        for (int i = endPos-1; i>=startPos; i--) {
            final boolean isSpace = orgMessage.charAt(i) == ' ';

            if (isSpace) {
                if (inWord) {
                    inWord = false;
                    ssb = checkAndInjectEmote(ssb, orgMessage, i+1, endPos, channelID, emotesManager, factory);
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
                    ssb = checkAndInjectEmote(ssb, orgMessage, startPos, endPos, channelID, emotesManager, factory);
                }
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
            Logger.warning("orgSet is empty");
            return orgSet;
        }

        List<Emote> globalEmotes = EmotesManager.getInstance().getGlobalEmotes();
        List<Emote> roomEmotes = EmotesManager.getInstance().getRoomEmotes(Helper.getInstance().getCurrentChannel());

        ChatEmoticonSet[] newSet = new ChatEmoticonSet[orgSet.length+2];
        java.lang.System.arraycopy(orgSet, 0, newSet, 1, orgSet.length);

        ChatEmoticon[] globalEmoticons = emotesToChatEmoticonArr(globalEmotes);
        ChatEmoticon[] roomEmoticons = emotesToChatEmoticonArr(roomEmotes);

        ChatEmoticonSet roomEmoticonSet = new ChatEmoticonSet();
        roomEmoticonSet.emoticonSetId = -102;
        roomEmoticonSet.emoticons = roomEmoticons != null ? roomEmoticons : new ChatEmoticon[0];
        newSet[0] = roomEmoticonSet;

        ChatEmoticonSet globalEmoticonSet = new ChatEmoticonSet();
        globalEmoticonSet.emoticonSetId = -103;
        globalEmoticonSet.emoticons = globalEmoticons != null ? globalEmoticons : new ChatEmoticon[0];
        newSet[newSet.length-1] = globalEmoticonSet;

        return newSet;
    }

    public static Spanned addTimestampToMessage(Spanned spanned) {
        if (!PrefManager.isTimestampsOn()) {
            return spanned;
        }

        if (spanned == null)
            return spanned;

        return addTimestamp(spanned);
    }
}
