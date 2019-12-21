package tv.twitch.a.o;

import android.content.Context;

import android.text.Spanned;
import android.text.SpannedString;


import androidx.fragment.app.FragmentActivity;

import tv.twitch.android.mod.bridges.ChatMessageFactory;
import tv.twitch.android.mod.bridges.ContextHelper;
import tv.twitch.android.mod.settings.PrefManager;
import tv.twitch.android.models.webview.WebViewSource;

import static tv.twitch.android.mod.utils.ChatUtils.injectCopySpan;
import static tv.twitch.android.mod.utils.ChatUtils.injectEmotesSpan;

// Source: ChatMessageFactory
public class a implements ChatMessageFactory, ContextHelper { // TODO: __IMPLEMENT
    public final FragmentActivity d = null;

    public enum b {
        b, // CHAT_MESSAGE
        c, // ACTION
        d, // SYSTEM_MESSAGE
        e  // HIGHLIGHTED_MESSAGE
    }

    static CharSequence a(a aVar, String str, tv.twitch.a.m.w.b.p.d dVar, tv.twitch.a.m.e.a0 a0Var, boolean z, int i2, Object obj) {
        return null;
    }


    @Override
    public CharSequence getSpannedEmote(String url) { // TODO: __ADD
        return a(this, url, tv.twitch.a.m.w.b.p.d.c, null, false, 12, null);
    }

    @Override
    public Context getAppContext() { // TODO: __ADD
        if (d != null) {
            return d.getApplicationContext();
        }

        return null;
    }

    private final Spanned a(tv.twitch.a.m.e.e eVar, boolean z, boolean z2, boolean z3, int i2, int i3, tv.twitch.a.m.e.h0.a aVar, tv.twitch.a.m.w.b.p.g gVar, WebViewSource webViewSource, String str, boolean z4, tv.twitch.a.m.e.w0.c cVar, Integer num) {
        // TODO: __REPLACE_METHOD_RESULT
//        invoke-static/range {v8 .. v21}, Ltv/twitch/a/o/a;->a(Ltv/twitch/a/o/a;Ltv/twitch/a/m/e/e;ZZZLtv/twitch/android/models/webview/WebViewSource;Ltv/twitch/a/m/w/b/p/g;Ljava/lang/String;Ltv/twitch/a/o/a$b;ILtv/twitch/a/m/e/w0/c;Ljava/lang/Integer;ILjava/lang/Object;)Landroid/text/SpannedString;
//
//        move-result-object v4
//
//        move-object/from16 v5, p1
//
//        move/from16 v6, p6
//
//        invoke-direct {v8, v4, v5, v6}, Ltv/twitch/a/o/a;->hook(Landroid/text/SpannedString;Ltv/twitch/a/m/e/e;I)Landroid/text/SpannedString;
//
//        move-result-object v4

        SpannedString a = new SpannedString("KEKW");
        a = hook(a, eVar, i3);

        return null;
    }

    private final SpannedString hook(SpannedString spannedString, tv.twitch.a.m.e.e chatMessageInterface, int channelId) {
        if (chatMessageInterface.isDeleted())
            return spannedString;

        if (PrefManager.isEmotesOn())
            spannedString = injectEmotesSpan(spannedString, channelId, this);

        if (PrefManager.isCopyMsgOn())
            spannedString = injectCopySpan(spannedString, chatMessageInterface.getTokens(), this);

        return spannedString;
    }
}