package tv.twitch.android.adapters.a;

import android.content.Context;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

import tv.twitch.android.mod.utils.ChatUtils;
import tv.twitch.android.mod.utils.LongClickLinkMovementMethod;

// Source: MessageRecyclerItem
public class b {

    public b(Context context, String str, int i2, String str2, String str3, int i3, Spanned message, Object systemMessageType, float f2, int i4, float f3, boolean z) {
        message = ChatUtils.addTimestampToMessage(message); // TODO: __ADD_START
    }

    public static final class a {
        private final TextView t = null;

        public a(View view) {
            this.t.setMovementMethod(LongClickLinkMovementMethod.getInstance()); // TODO: __REPLACE
        }
    }
}