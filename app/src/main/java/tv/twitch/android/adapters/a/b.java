package tv.twitch.android.adapters.a;

import android.content.Context;
import android.text.Spanned;
import android.view.MotionEvent;
import android.widget.TextView;

import tv.twitch.android.mod.bridges.Hooks;
import tv.twitch.android.mod.bridges.IMessageRecyclerItem;

/**
 * Source: MessageRecyclerItem
 */
public class b implements IMessageRecyclerItem { // TODO: __IMPLEMENT
    private boolean j;
    private Spanned f;

    public String l; // chat msg


    public b(Context context, String messageId, int i2, String str2, String str3, int i3, Spanned message, Object gVar, float f2, int i4, float f3, boolean z, boolean z2, String str4, Object eventDispatcher) {
        message = Hooks.addTimestampToMessage(message, messageId); // TODO: __HOOK_PARAM
    }

    public void j() {
        this.j = true; // TODO: __INJECT_CODE
    }

    @Override
    public Spanned getSpanned() { // TODO: __INJECT_METHOD
        return f;
    }

    public static final class a implements IMessageRecyclerItem { // TODO: __IMPLEMENT
        public final TextView Q() {
            return null;
        }

        @Override
        public Spanned getSpanned() { // TODO: __INJECT_METHOD
            return (Spanned) Q().getText();
        }
    }

    static final class c {
        final b c = null;

        public final boolean d(MotionEvent motionEvent) {
            Hooks.tryCopyMsg(c.l); // TODO: __INJECT_CODE
            return true;
        }
    }

}