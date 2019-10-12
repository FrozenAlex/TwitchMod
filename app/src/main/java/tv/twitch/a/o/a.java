package tv.twitch.a.o;

import android.text.Spanned;

import tv.twitch.a.m.m.b.p.e;
import tv.twitch.android.app.core.u1;
import tv.twitch.android.mod.bridges.ChatMessageFactory;
import tv.twitch.android.mod.utils.Helper;
import tv.twitch.android.util.androidUI.TwitchURLSpan;

public class a implements ChatMessageFactory { // TODO: __IMPLEMENT
    static CharSequence a(a aVar, String str, e.b bVar, tv.twitch.a.m.d.a0 a0Var, boolean z, int i2, Object obj) {
        return null;
    }


    private final Spanned hook(tv.twitch.a.m.d.e eVar, boolean z, boolean z2, boolean z3, int i2, int i3, tv.twitch.a.m.d.h0.b bVar, TwitchURLSpan.a aVar, u1.b bVar2, String str, boolean z4, tv.twitch.a.m.d.u0.c cVar, Integer num) {  // TODO: __ADD__SYNTHETIC
        Spanned ret = a(eVar, z, z2, z3, i2, i3, bVar, aVar, bVar2, str, z4, cVar, num);
        return Helper.injectEmotes(ret, i3, this);
    }

    private final Spanned a(tv.twitch.a.m.d.e eVar, boolean z, boolean z2, boolean z3, int i2, int i3, tv.twitch.a.m.d.h0.b bVar, TwitchURLSpan.a aVar, u1.b bVar2, String str, boolean z4, tv.twitch.a.m.d.u0.c cVar, Integer num) {
        return null;
    }

    @Override
    public CharSequence getSpannedEmote(String url) { // TODO: __ADD
        return a(this, url, e.b.b, null, false, 12, null);
    }
}
