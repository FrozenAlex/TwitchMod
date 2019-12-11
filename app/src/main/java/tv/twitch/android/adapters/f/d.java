package tv.twitch.android.adapters.f;

import android.app.Activity;
import android.text.Spanned;

import androidx.fragment.app.FragmentActivity;

import tv.twitch.a.m.e.b0.g;
import tv.twitch.android.mod.utils.ChatUtils;

// Source: MessageRecyclerItem
public class d {

    public d(FragmentActivity fragmentActivity, String str, int i2, String str2, String str3, int i3, Spanned spanned, g gVar, float f2, int i4, float f3, boolean z) {
        spanned = ChatUtils.addTimestampToMessage(spanned); // TODO: __ADD_START
    }
}