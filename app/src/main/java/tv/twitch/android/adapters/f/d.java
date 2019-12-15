package tv.twitch.android.adapters.f;

import android.app.Activity;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import tv.twitch.a.m.e.b0.g;
import tv.twitch.android.mod.utils.ChatUtils;
import tv.twitch.android.mod.utils.LongClickLinkMovementMethod;

// Source: MessageRecyclerItem
public class d {

    public d(FragmentActivity fragmentActivity, String str, int i2, String str2, String str3, int i3, Spanned spanned, g gVar, float f2, int i4, float f3, boolean z) {
        spanned = ChatUtils.addTimestampToMessage(spanned); // TODO: __ADD_START
    }

    public static final class a {
        private final TextView t = null;

        public a(View view) {
            this.t.setMovementMethod(LongClickLinkMovementMethod.getInstance()); // TODO: __REPLACE
        }
    }
}