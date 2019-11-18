package tv.twitch.android.mod.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import tv.twitch.android.mod.activities.Settings;
import tv.twitch.android.mod.bridges.ChatMessageFactory;
import tv.twitch.android.mod.emotes.EmotesManager;
import tv.twitch.android.mod.models.Emote;
import tv.twitch.android.mod.settings.PrefManager;
import tv.twitch.android.models.communitypoints.ActiveClaimModel;
import tv.twitch.android.shared.chat.communitypoints.models.CommunityPointsModel;
import tv.twitch.chat.ChatEmoticon;
import tv.twitch.chat.ChatEmoticonSet;
import tv.twitch.chat.ChatEmoticonUrl;

public class Helper {
    private int currentChannel = 0; // TODO: rewrite
    private static final Set<String> mClaims = Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());

    private Helper() {
    }

    public static int findMsgStartPos(Spanned orgMessage) {
        int startMessagePos = -1;
        Object[] spans = orgMessage.getSpans(0, orgMessage.length(), tv.twitch.a.m.e.y0.f.class);
        if (spans != null && spans.length == 1) {
            startMessagePos = orgMessage.getSpanEnd(spans[0]);
        }
        if (startMessagePos != -1) {
            if (startMessagePos + 2 < orgMessage.length()) {
                if (orgMessage.charAt(startMessagePos) == ':' && orgMessage.charAt(startMessagePos + 1) == ' ') {
                    startMessagePos++;
                }
            } else {
                return -1;
            }
        }

        if (startMessagePos == -1) {
            Logger.debug(String.format(Locale.ENGLISH, "Debug msg: {{%s}}", orgMessage));
            startMessagePos = TextUtils.indexOf(orgMessage, ' ');
        }

        return startMessagePos;
    }

    public static Spanned injectEmotes(Spanned orgMessage, int channelID, ChatMessageFactory factory) {
        // Logger.debug(String.format(Locale.ENGLISH, "msg: {{%s}}", orgMessage));
        if (!PrefManager.isEmotesOn()) {
            return orgMessage;
        }

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

        try {
            SpannableStringBuilder ssb = null;

            if (TextUtils.isEmpty(orgMessage))
                return orgMessage;

            int startMessagePos = findMsgStartPos(orgMessage);
            if (startMessagePos == -1) {
                return orgMessage;
            }

            boolean newWord = false;
            int endWordPos = orgMessage.length();
            for (int i = orgMessage.length() - 1; i >= startMessagePos; i--) {
                if (orgMessage.charAt(i) != ' ') {
                    if (!newWord) {
                        newWord = true;
                        endWordPos = i + 1;
                    }
                } else {
                    if (newWord) {
                        newWord = false;
                        int startWordPos = i + 1;
                        final Emote emote = emotesManager.getEmote(TextUtils.substring(orgMessage, startWordPos, endWordPos), channelID);
                        if (emote != null) {
                            SpannableString emoteSpan = (SpannableString) factory.getSpannedEmote(emote.getUrl());
                            if (ssb == null) {
                                ssb = new SpannableStringBuilder(orgMessage);
                            }
                            ssb.replace(startWordPos, endWordPos, emoteSpan);
                        }
                    }
                }
            }

            if (ssb != null)
                return SpannedString.valueOf(ssb);

            return orgMessage;
        } catch (Exception e) {
            Logger.debug(String.format(Locale.ENGLISH, "msg: {{%s}}", orgMessage));
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
        if (!PrefManager.isEmotePickerOn()) {
            return orgArr;
        }
        Logger.info("Injecting emotes in widget...");
        if (orgArr == null || orgArr.length == 0)
            return orgArr;


        List<ChatEmoticonSet> chatEmoticonSetList = new ArrayList<>(Arrays.asList(orgArr));
        try {
            List<Emote> globalEmotes = EmotesManager.getInstance().getGlobalEmotes();
            if (globalEmotes != null && globalEmotes.size() > 0) {
                ChatEmoticonSet chatEmoticonSet = new ChatEmoticonSet();
                chatEmoticonSet.emoticonSetId = -799;
                ChatEmoticon[] arr = convert(globalEmotes);
                chatEmoticonSet.emoticons = arr != null ? arr : new ChatEmoticon[0];
                chatEmoticonSetList.add(chatEmoticonSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            int channelId = getInstance().getCurrentChannel();
            if (channelId != 0) {
                List<Emote> roomEmotes = EmotesManager.getInstance().getRoomEmotes(channelId);
                if (roomEmotes != null && roomEmotes.size() > 0) {
                    ChatEmoticonSet chatEmoticonSet = new ChatEmoticonSet();
                    chatEmoticonSet.emoticonSetId = -800;
                    ChatEmoticon[] arr = convert(roomEmotes);
                    chatEmoticonSet.emoticons = arr != null ? arr : new ChatEmoticon[0];
                    chatEmoticonSetList.add(chatEmoticonSet);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ChatEmoticonSet[] ret = chatEmoticonSetList.toArray(new ChatEmoticonSet[0]);
        return ret;
    }

    private static class Holder {
        static final Helper mInstance = new Helper();
    }

    public static Helper getInstance() {
        return Holder.mInstance;
    }

    public void setCurrentChannel(int currentChannel) {
        Logger.debug("Set currentChannel=" + currentChannel);
        this.currentChannel = currentChannel;
    }

    public int getCurrentChannel() {
        return this.currentChannel;
    }

    public static void newRequest(int channelId) {
        EmotesManager.getInstance().request(channelId);
        Helper.getInstance().setCurrentChannel(channelId);
    }

    public static void openSettings(Activity fragmentActivity) {
        if (fragmentActivity != null) {
            Context context = fragmentActivity.getApplicationContext();
            if (context != null)
                startActivity(context, Settings.class);
        }
    }

    public static void startActivity(Context context, Class activity) {
        if (context == null || activity == null) {
            Logger.warning("null args");
            return;
        }
        Intent intent = new Intent(context, activity);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static Spanned injectTimestamp(Spanned spanned) {
        if (!PrefManager.isTimestampsOn()) {
            return spanned;
        }

        if (spanned == null)
            return spanned;

        return addTimestamp(spanned);
    }

    private static Spanned addTimestamp(Spanned spanned) {
        SpannableString dateString = SpannableString.valueOf(new SimpleDateFormat("HH:mm ", Locale.UK).format(new Date()));
        dateString.setSpan(new RelativeSizeSpan(0.75f), 0, dateString.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        SpannableStringBuilder message = new SpannableStringBuilder(dateString);
        message.append(new SpannableStringBuilder(spanned));
        return SpannableString.valueOf(message);
    }

    public static void clicker(final View view, CommunityPointsModel communityPointsModel) {
        if (!PrefManager.isClickerOn())
            return;

        if (view == null) {
            Logger.error("view is null");
            return;
        }
        Logger.debug("View:" + view.toString());

        if (communityPointsModel == null) {
            Logger.error("communityPointsModel is null");
            return;
        }

        Logger.debug("CommunityPointsModel -> " + communityPointsModel.toString());
        ActiveClaimModel claimModel = communityPointsModel.getClaim();

        if (claimModel == null) {
            Logger.error("claimModel is null");
            return;
        }
        Logger.debug("ActiveClaimModel -> " + claimModel.toString());

        if (TextUtils.isEmpty(claimModel.getId())) {
            Logger.error("Bad claim id");
            return;
        }

        synchronized (mClaims) {
            if (mClaims.contains(claimModel.getId())) {
                Logger.debug("Skip claim");
                return;
            }
            mClaims.add(claimModel.getId());
        }

        Logger.info("Click! Get: " + claimModel.getPointsEarned());
        view.performClick();
    }
}
