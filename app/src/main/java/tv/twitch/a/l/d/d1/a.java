package tv.twitch.a.l.d.d1;

import android.content.Context;

import android.content.ContextWrapper;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;

import tv.twitch.a.l.d.g;
import tv.twitch.a.l.d.m0;
import tv.twitch.android.mod.bridges.ChatMessageFactory;
import tv.twitch.android.mod.bridges.ContextHelper;
import tv.twitch.android.mod.settings.PrefManager;
import tv.twitch.android.mod.utils.Logger;
import tv.twitch.android.models.webview.WebViewSource;

import static tv.twitch.android.mod.utils.ChatUtils.injectBadges;
import static tv.twitch.android.mod.utils.ChatUtils.injectCopySpan;
import static tv.twitch.android.mod.utils.ChatUtils.injectEmotesSpan;

// Source: ChatMessageFactory
public class a implements ChatMessageFactory, ContextHelper { // TODO: __IMPLEMENT
    public final ContextWrapper d = null;


    public enum b {
        b, // CHAT_MESSAGE
        c, // ACTION
        d, // SYSTEM_MESSAGE
        e  // HIGHLIGHTED_MESSAGE
    }


    static /* synthetic */ CharSequence a(a aVar, String str, tv.twitch.a.l.w.b.q.d dVar, String str2, m0 m0Var, boolean z, int i2, Object obj) {
        // str - url
        // str2 - text

        return null;
    }

    @Override
    public CharSequence getSpannedEmote(String url, String emoteText) { // TODO: __ADD
        return a(this, url, tv.twitch.a.l.w.b.q.d.c, emoteText, null, false, 24, null);
    }

    @Override
    public CharSequence getSpannedBadge(String url, String badgeName) {
        return a(this, url, tv.twitch.a.l.w.b.q.d.d, badgeName, null, true, 8, null);
    }


    @Override
    public Context getAppContext() { // TODO: __ADD
        if (d != null) {
            return d.getApplicationContext();
        }

        return null;
    }

    private final Spanned a(tv.twitch.a.l.d.g gVar, boolean z, boolean z2, boolean z3, int i2, int i3, tv.twitch.a.l.d.t0.a aVar, tv.twitch.a.l.w.b.q.g gVar2, WebViewSource webViewSource, String str, boolean z4, tv.twitch.a.l.d.l1.c cVar, Integer num) {
        // TODO: HOOK BADGE FACTORY RESULT
        // SpannedString a2 = a(i3, f2, z5);
        // a2 = hookBadgeMethod(a2, gVar);

//        invoke-direct {v6, v3, v0, v7}, Ltv/twitch/a/l/d/d1/a;->a(ILjava/util/List;Z)Landroid/text/SpannedString;
//
//        move-result-object v0
//
//        move-object/from16 v8, p0
//
//        move-object/from16 v9, p1
//
//        invoke-direct {v8, v0, v9}, Ltv/twitch/a/l/d/d1/a;->hookBadgeMethod(Landroid/text/SpannedString;Ltv/twitch/a/l/d/g;)Landroid/text/SpannedString;
//

        SpannedString a2 = new SpannedString("FFZ");
        a2 = hookBadgeMethod(a2, gVar);

        // TODO: HOOK MESSAGE FACTORY RESULT
        // SpannedString a3 = a(this, gVar, z, z2, z3, webViewSource, gVar2, (String) null, cVar3, i2, cVar, num, 64, (Object) null);
        // a3 = hookMessageMethod(a3, gVar, i3));

//        invoke-static/range {v8 .. v21}, Ltv/twitch/a/l/d/d1/a;->a(Ltv/twitch/a/l/d/d1/a;Ltv/twitch/a/l/d/g;ZZZLtv/twitch/android/models/webview/WebViewSource;Ltv/twitch/a/l/w/b/q/g;Ljava/lang/String;Ltv/twitch/a/l/d/d1/a$c;ILtv/twitch/a/l/d/l1/c;Ljava/lang/Integer;ILjava/lang/Object;)Landroid/text/SpannedString;
//
//        move-result-object v10
//
//        move-object/from16 v11, p1
//
//        move/from16 v12, p6
//
//        invoke-direct {v8, v10, v11, v12}, Ltv/twitch/a/l/d/d1/a;->hookMessageMethod(Landroid/text/SpannedString;Ltv/twitch/a/l/d/g;I)Landroid/text/SpannedString;


        SpannedString a3 = new SpannedString("KEKW");
        a3 = hookMessageMethod(a3, gVar, i3);


        return null;
    }

    private SpannedString hookBadgeMethod(SpannedString spannedString, g chatMessageInterface) {
        if (spannedString == null) {
            Logger.warning("spannedString is null");
            return spannedString;
        }

        String userName = chatMessageInterface.b();
        if (TextUtils.isEmpty(userName)) {
            Logger.warning("userName is empty");
            return spannedString;
        }

        spannedString = injectBadges(spannedString, userName.toLowerCase(), this);

        return spannedString;
    }


    private SpannedString hookMessageMethod(SpannedString spannedString, g chatMessageInterface, int channelId) {
        if (spannedString == null) {
            Logger.warning("spannedString is null");
            return spannedString;
        }

        // If message deletes return org
        if (chatMessageInterface.a())
            return spannedString;

        // Inject bttv emotes
        if (PrefManager.isEmotesOn())
            spannedString = injectEmotesSpan(spannedString, channelId, this);

        // Inject copy span
        if (PrefManager.isCopyMsgOn())
            spannedString = injectCopySpan(spannedString, chatMessageInterface.e(), this);

        return spannedString;
    }
}