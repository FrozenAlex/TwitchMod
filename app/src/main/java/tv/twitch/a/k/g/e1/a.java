package tv.twitch.a.k.g.e1;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

import tv.twitch.a.k.g.g;
import tv.twitch.a.k.c0.b.s.d;
import tv.twitch.a.k.g.m0;
import tv.twitch.android.mod.bridges.Hooks;
import tv.twitch.android.mod.bridges.IChatMessageFactory;
import tv.twitch.android.mod.bridges.LoaderLS;
import tv.twitch.android.mod.emotes.EmoteManager;
import tv.twitch.android.mod.settings.PrefManager;

import static tv.twitch.android.mod.utils.ChatUtils.injectCopySpan;
import static tv.twitch.android.mod.utils.ChatUtils.injectEmotesSpan;

/**
 * Source: ChatMessageFactory
 */
public class a implements IChatMessageFactory { // TODO: __IMPLEMENT
    static CharSequence a(a factory, String url, tv.twitch.a.k.c0.b.s.d mediaSpan, String text, m0 urlImageClickableProvider, boolean z, int i2, Object obj) {
        return null;
    }

    private final CharSequence a(Object chatMessageInterface, int color, Object clickableUsernameSpanListener, boolean z, String str, String str2) {
        color = Hooks.hookUsernameSpanColor(color); // TODO: __HOOK_PARAM
        return null;
    }

    private final Spanned a(g chatMessageInterface, boolean z, boolean z2, boolean z3, int i2, int channelId, Object iClickableUsernameSpanListener, Object twitchUrlSpanClickListener, Object webViewSource, String str, boolean z4, Object censoredMessageTrackingInfo, Integer num, Object eventDispatcher) {
        try {
// TODO: HOOK MESSAGE FACTORY RESULT
//  invoke-static/range {v8 .. v22}, Ltv/twitch/a/k/g/e1/a;->a(Ltv/twitch/a/k/g/e1/a;Ltv/twitch/a/k/g/g;ZZZLtv/twitch/android/models/webview/WebViewSource;Ltv/twitch/a/k/c0/b/s/g;Ljava/lang/String;Ltv/twitch/a/k/g/e1/a$c;ILtv/twitch/a/k/g/p1/c;Ljava/lang/Integer;Ltv/twitch/android/core/mvp/viewdelegate/EventDispatcher;ILjava/lang/Object;)Landroid/text/SpannedString;
//  move-result-object v11
//  move-object/from16 v10, p1
//  move/from16 v12, p6
//  invoke-direct {v8, v10, v11, v12}, Ltv/twitch/a/k/g/e1/a;->hookMessageMethodResult(Ltv/twitch/a/k/g/g;Landroid/text/SpannedString;I)Landroid/text/SpannedString;
//  move-result-object v10

            SpannedString message = new SpannedString("KEKW");
            message = hookMessageMethodResult(chatMessageInterface, message, channelId);

            return message;
        } catch (Throwable th) {
            th.printStackTrace();

            SpannableString error = new SpannableString("<Error processing message>");
            error.setSpan(new ForegroundColorSpan(Color.RED), 0, error.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            return error;
        }
    }

    @Override
    public CharSequence getSpannedEmote(String url, String emoteText, boolean isGif) { // TODO: __INJECT_METHOD
        return a(this, url, isGif ? d.valueOf("AnimatedBit") : d.valueOf("Emote"), emoteText, null, false, 24, null);
    }

    private SpannedString hookMessageMethodResult(g chatMessageInterface, SpannedString orgMessage, int channelId) { // TODO: __INJECT_METHOD
        try {
            if (TextUtils.isEmpty(orgMessage))
                return orgMessage;

            // if message deleted
            if (chatMessageInterface.a())
                return orgMessage;

            PrefManager manager = LoaderLS.getInstance().getPrefManager();

            SpannedString res = new SpannedString(orgMessage);
            if (manager.isEmotesOn()) {
                EmoteManager emoteManager = LoaderLS.getInstance().getEmoteManager();
                emoteManager.requestChannelEmoteSet(channelId, false);
                res = injectEmotesSpan(this, emoteManager, orgMessage, channelId, manager.getEmoteSize());
            }

            if (manager.isCopyMsgOn()) {
                res = injectCopySpan(res, chatMessageInterface.e());
            }

            return res;

        } catch (Throwable th) {
            th.printStackTrace();
        }

        return orgMessage;
    }
}