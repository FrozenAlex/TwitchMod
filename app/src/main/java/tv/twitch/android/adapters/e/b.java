package tv.twitch.android.adapters.e;

import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import tv.twitch.a.l.d.n0.g;
import tv.twitch.android.mod.utils.ChatUtils;
import tv.twitch.android.mod.utils.LongClickLinkMovementMethod;

// Source: MessageRecyclerItem
public class b {

    public b(FragmentActivity fragmentActivity, String str, int i2, String str2, String str3, int i3, Spanned message, g gVar, float f2, int i4, float f3, boolean z) {
        message = ChatUtils.addTimestampToMessage(message); // TODO: __ADD_START
    }

    public static final class a {
        private final TextView t = null;

        public a(View view) {
            this.t.setMovementMethod(LongClickLinkMovementMethod.getInstance()); // TODO: __REPLACE
        }
    }
}