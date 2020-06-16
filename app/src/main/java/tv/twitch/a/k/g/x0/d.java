package tv.twitch.a.k.g.x0;


import android.text.Spanned;
import android.widget.TextView;

import tv.twitch.android.mod.bridges.IMessageRecyclerItem;

/**
 * Source: ChommentRecyclerItem
 */
public class d implements IMessageRecyclerItem { // TODO: __IMPLEMENT
    private Spanned c;

    @Override
    public Spanned getSpanned() { // TODO: __INJECT_METHOD
        return c;
    }

    public static final class b implements IMessageRecyclerItem { // TODO: __IMPLEMENT
        private TextView u;

        @Override
        public Spanned getSpanned() { // TODO: __INJECT_METHOD
            return (Spanned) u.getText();
        }

    }
}
