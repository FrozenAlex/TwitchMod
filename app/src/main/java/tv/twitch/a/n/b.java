package tv.twitch.a.n;

import android.text.Spanned;
import android.text.SpannedString;

import tv.twitch.a.l.m.b.c.d;
import tv.twitch.android.mod.bridges.ChatMessageFactory;
import tv.twitch.android.mod.utils.MessageUtils;

import static tv.twitch.android.mod.utils.MessageUtils.printMessage;

public class b implements ChatMessageFactory {

    static /* synthetic */ CharSequence a(b bVar, String str, tv.twitch.a.l.m.b.c.d.b bVar2, tv.twitch.a.l.d.C c2, boolean z, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            c2 = null;
        }
        if ((i2 & 8) != 0) {
            z = false;
        }
        return null;
    }

    @Override
    public CharSequence getSpannedEmote(String url) {
        return a(this, url, d.b.b, null, false, 12, null);
    }

    private final Spanned a(tv.twitch.a.l.d.f fVar, boolean z, boolean z2, boolean z3, int i2, int i3, Object bVar, Object aVar, Object aVar2, String str, boolean z4, Object cVar, Integer num) {

        SpannedString a = new SpannedString("");

        printMessage(a);
        return MessageUtils.injectEmotes(a, i3, this);
    }
}
