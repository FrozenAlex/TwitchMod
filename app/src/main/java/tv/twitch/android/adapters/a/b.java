package tv.twitch.android.adapters.a;

import android.content.Context;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

import tv.twitch.android.mod.bridges.Hooks;
import tv.twitch.android.mod.bridges.IMessageRecyclerItem;
import tv.twitch.android.mod.utils.LongClickLinkMovementMethod;

/**
 * Source: MessageRecyclerItem
 */
public class b implements IMessageRecyclerItem { // TODO: __IMPLEMENT
    private boolean j;
    private Spanned f;


    public b(Context context, String messageId, int i2, String str2, String str3, int i3, Spanned message, Object systemMessageType, float f2, int i4, float f3, boolean z) {
        message = Hooks.addTimestampToMessage(message, messageId); // TODO: __HOOK_PARAM
    }

    @Override
    public Spanned getSpanned() { // TODO: __INJECT_METHOD
        return this.f;
    }

    public static final class a {
        private final TextView t = null;

        public a(View view) {
            this.t.setMovementMethod(LongClickLinkMovementMethod.getInstance()); // TODO: __REPLACE
        }

        public final TextView E() {
            return this.t;
        }
    }

    public void g() {
        this.j = Hooks.hookMsgRemover(this.j); // TODO: __HOOK
    }
}