package tv.twitch.a.k.g.i1;

import android.text.SpannedString;

import tv.twitch.a.k.g.g;
import tv.twitch.a.k.e0.b.r.d;
import tv.twitch.a.k.g.n0;
import tv.twitch.android.mod.bridges.Hooks;
import tv.twitch.android.mod.bridges.IChatMessageFactory;


/**
 * Source: ChatMessageFactory
 */
public class a implements IChatMessageFactory { // TODO: __IMPLEMENT
    static CharSequence F(a factory, String url, tv.twitch.a.k.e0.b.r.d mediaSpan, String text, n0 urlImageClickableProvider, boolean z, int i2, Object obj) {
        return null;
    }

    private final CharSequence Q(tv.twitch.a.k.g.g gVar, int color, Object aVar, boolean z, String str, String str2) {
        color = Hooks.hookUsernameSpanColor(color); // TODO: __HOOK_PARAM

        return null;
    }

    public enum c {
        b, // CHAT_MESSAGE
        c, // ACTION
        d, // SYSTEM_MESSAGE
        e // HIGHLIGHTED_MESSAGE
    }

    private final e j(g chatMessageInterface, boolean z, boolean z2, boolean z3, int i2, int channelId, Object iClickableUsernameSpanListener, Object twitchUrlSpanClickListener, Object webViewSource, String str, boolean z4, Object censoredMessageTrackingInfo, Integer num, Object eventDispatcher) {
        try {
            // TODO: HOOK EXAMPLE
            // invoke-static/range {v6 .. v20}, Ltv/twitch/a/k/g/i1/a;->I(Ltv/twitch/a/k/g/i1/a;Ltv/twitch/a/k/g/g;ZZZLtv/twitch/android/models/webview/WebViewSource;Ltv/twitch/a/k/e0/b/r/g;Ljava/lang/String;Ltv/twitch/a/k/g/i1/a$c;ILtv/twitch/a/k/g/t1/c;Ljava/lang/Integer;Ltv/twitch/android/core/mvp/viewdelegate/EventDispatcher;ILjava/lang/Object;)Landroid/text/SpannedString;
            // move-result-object v8
            // move-object/from16 v7, p1
            // move/from16 v9, p6
            // invoke-static {v6, v7, v8, v9}, Ltv/twitch/android/mod/bridges/Hooks;->hookChatMessage(Ltv/twitch/android/mod/bridges/IChatMessageFactory;Ltv/twitch/a/k/g/g;Landroid/text/SpannedString;I)Landroid/text/SpannedString;
            // move-result-object v8

            SpannedString message = new SpannedString("KEKW");
            message = Hooks.hookChatMessage(this, chatMessageInterface, message, channelId);

            return null;
        } catch (Throwable th) {
            th.printStackTrace();

            return e.d;
        }
    }

    @Override
    public CharSequence getSpannedEmote(String url, String emoteText) { // TODO: __INJECT_METHOD
        return F(this, url, d.c, emoteText, null, false, 0, null);
    }
}