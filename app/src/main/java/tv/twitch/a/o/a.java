package tv.twitch.a.o;

import android.content.Context;

import android.text.Spanned;
import android.text.SpannedString;


import androidx.fragment.app.FragmentActivity;

import tv.twitch.a.m.x.b.p.i;
import tv.twitch.android.mod.bridges.ChatMessageFactory;
import tv.twitch.android.mod.bridges.ContextHelper;
import tv.twitch.android.mod.settings.PrefManager;
import tv.twitch.android.models.webview.WebViewSource;

import static tv.twitch.android.mod.utils.ChatUtils.injectCopySpan;
import static tv.twitch.android.mod.utils.ChatUtils.injectEmotesSpan;

// Source: ChatMessageFactory
public class a implements ChatMessageFactory, ContextHelper { // TODO: __IMPLEMENT
    public final FragmentActivity c = null;

    public enum b {
        b, // CHAT_MESSAGE
        c, // ACTION
        d, // SYSTEM_MESSAGE
        e  // HIGHLIGHTED_MESSAGE
    }

    static CharSequence a(a aVar, String str, i.b bVar, tv.twitch.a.m.e.a0 a0Var, boolean z, int i2, Object obj) {
        return null;
    }


    @Override
    public CharSequence getSpannedEmote(String url) { // TODO: __ADD
        return a(this, url, i.b.c, null, false, 12, null);
    }

    @Override
    public Context getAppContext() { // TODO: __ADD
        if (c != null) {
            return c.getApplicationContext();
        }

        return null;
    }

    public final SpannedString a(final tv.twitch.a.m.e.e eVar, boolean z, boolean z2, boolean z3, WebViewSource webViewSource, tv.twitch.a.m.x.b.p.h hVar, String str, b bVar, int i2, tv.twitch.a.m.e.w0.c cVar, Integer num) { // TODO: __ADD
        SpannedString spannableString = org(eVar, z, z2, z3, webViewSource, hVar, str, bVar, i2, cVar, num);
        if (PrefManager.isCopyMsgOn())
            spannableString = injectCopySpan(spannableString, eVar.getTokens(), this);

        return spannableString;
    }

    private final Spanned a(tv.twitch.a.m.e.e eVar, boolean z, boolean z2, boolean z3, int i2, int i3, tv.twitch.a.m.e.h0.a aVar, tv.twitch.a.m.x.b.p.h hVar, WebViewSource webViewSource, String str, boolean z4, tv.twitch.a.m.e.w0.c cVar, Integer num) { // TODO: __RENAME
        Spanned ret = org(eVar, z, z2, z3, i2, i3, aVar, hVar, webViewSource, str, z4, cVar, num);

        if (eVar.isDeleted() || eVar.isSystemMessage())
            return ret;

        if (PrefManager.isEmotesOn())
            ret = injectEmotesSpan(ret, i3, this, eVar);

        return ret;
    }

    public final SpannedString org(tv.twitch.a.m.e.e eVar, boolean z, boolean z2, boolean z3, WebViewSource webViewSource, tv.twitch.a.m.x.b.p.h hVar, String str, b bVar, int i2, tv.twitch.a.m.e.w0.c cVar, Integer num) { // TODO: __RENAME
        return null;
    }


    private final Spanned org(tv.twitch.a.m.e.e eVar, boolean z, boolean z2, boolean z3, int i2, int i3, tv.twitch.a.m.e.h0.a aVar, tv.twitch.a.m.x.b.p.h hVar, WebViewSource webViewSource, String str, boolean z4, tv.twitch.a.m.e.w0.c cVar, Integer num) { // TODO: __RENAME
        return null;
    }

}