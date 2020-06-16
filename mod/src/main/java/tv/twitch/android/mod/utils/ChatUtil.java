package tv.twitch.android.mod.utils;


import android.graphics.Color;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.util.LruCache;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import tv.twitch.android.mod.bridges.IChatMessageFactory;
import tv.twitch.android.mod.bridges.LoaderLS;
import tv.twitch.android.mod.emotes.EmoteManager;
import tv.twitch.android.mod.models.Emote;
import tv.twitch.android.mod.models.settings.Gifs;
import tv.twitch.android.mod.settings.PrefManager;
import tv.twitch.android.models.chat.MessageToken;


public class ChatUtil {
    private static final String TIMESTAMP_DATE_FORMAT = "HH:mm ";


    private static final LruCache<Integer, Integer> mDarkThemeCache = new LruCache<Integer, Integer>(500) {
        @Override
        protected Integer create(Integer color) {
            float[] hsv = new float[3];
            Color.colorToHSV(color, hsv);
            if (hsv[2] >= .3) {
                return color;
            }

            hsv[2] = (float) .4;
            return Color.HSVToColor(hsv);
        }
    };

    private static final LruCache<Integer, Integer> mWhiteThemeCache = new LruCache<Integer, Integer>(500) {
        @Override
        protected Integer create(Integer color) {
            float[] hsv = new float[3];
            Color.colorToHSV(color, hsv);
            if (hsv[2] <= 0.9) {
                return color;
            }

            hsv[2] = (float) .8;
            return Color.HSVToColor(hsv);
        }
    };

    public enum EmoteSet {
        GLOBAL("-110", "BetterTTV Global Emotes"),
        FFZ( "-109", "FFZ Channel Emotes"),
        BTTV( "-108", "BetterTTV Channel Emotes");

        public final String description;
        public final String setId;

        public static EmoteSet findById(String id) {
            for (EmoteSet set : values()) {
                if (set.getId().equals(id))
                    return set;
            }

            return null;
        }

        EmoteSet(String id, String description) {
            this.description = description;
            this.setId = id;
        }

        public String getId() {
            return setId;
        }

        public String getDescription() {
            return description;
        }
    }

    public static Spanned addTimestamp(Spanned message, Date date) {
        final String dateFormat = new SimpleDateFormat(TIMESTAMP_DATE_FORMAT, Locale.UK).format(date);
        SpannableString dateString = SpannableString.valueOf(dateFormat);
        dateString.setSpan(new RelativeSizeSpan(0.75f), 0, dateString.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        SpannableStringBuilder formatted = new SpannableStringBuilder(dateString);
        formatted.append(new SpannableStringBuilder(message));
        return SpannableString.valueOf(formatted);
    }

    public static Integer fixUsernameColor(int color) {
        if (LoaderLS.getPrefManagerInstance().isDarkThemeEnabled()) {
            return mDarkThemeCache.get(color);
        } else {
            return mWhiteThemeCache.get(color);
        }
    }

    public static String getRawMessage(List<MessageToken> tokens) {
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

    public static SpannedString injectEmotesSpan(IChatMessageFactory factory, EmoteManager emoteManager, SpannedString messageSpan, int channelID, PrefManager manager) {
        if (TextUtils.isEmpty(messageSpan)) {
            Logger.warning("Empty messageSpan");
            return messageSpan;
        }

        if (emoteManager == null) {
            Logger.error("emoteManager is null");
            return messageSpan;
        }

        if (factory == null) {
            Logger.error("factory is null");
            return messageSpan;
        }

        boolean newWord = false;
        int startPos = 0;
        int messageLength = messageSpan.length();

        SpannableStringBuilder ssb = null;

        for (int currentPos = 0; currentPos <= messageLength; currentPos++) {
            if (currentPos != messageLength && messageSpan.charAt(currentPos) != ' ') {
                if (!newWord) {
                    newWord = true;
                    startPos = currentPos;
                }
            } else {
                if (newWord) {
                    newWord = false;

                    String code = String.valueOf(messageSpan.subSequence(startPos, currentPos));
                    Emote emote = emoteManager.getEmote(code, channelID);
                    if (emote != null) {
                        if (emote.isGif() && manager.getGifsStrategy() == Gifs.DISABLED)
                            continue;

                        SpannableString emoteSpan = (SpannableString) factory.getSpannedEmote(emote.getUrl(manager.getEmoteSize()), emote.getCode());
                        if (emoteSpan != null) {
                            if (ssb == null)
                                ssb = new SpannableStringBuilder(messageSpan);

                            ssb.replace(startPos, currentPos, emoteSpan);
                        }
                    }
                }
            }
        }

        if (ssb != null)
            return SpannedString.valueOf(ssb);

        return messageSpan;
    }
}
