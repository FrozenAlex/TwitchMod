package tv.twitch.android.adapters.f;

import android.app.Activity;
import android.text.Spanned;

import tv.twitch.android.mod.utils.Helper;

// Source: MessageRecyclerItem
public class d {

    public d(Activity fragmentActivity, String str, int i2, String str2, String str3, int i3, Spanned spanned, Object fVar, float f2, int i4, float f3, boolean z) {
        spanned = Helper.injectTimestamp(spanned);
    }
}