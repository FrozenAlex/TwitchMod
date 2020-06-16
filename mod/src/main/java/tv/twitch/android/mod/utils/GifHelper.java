package tv.twitch.android.mod.utils;

import android.graphics.drawable.Drawable;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.TextView;

import com.bumptech.glide.load.n.g.c;

import java.util.List;

import tv.twitch.a.k.e0.b.r.a;
import tv.twitch.android.core.adapters.t;
import tv.twitch.android.mod.bridges.IDrawable;
import tv.twitch.android.mod.bridges.IMessageRecyclerItem;


public class GifHelper {
    public static void recycleObject(Object item, boolean force) {
        if (item == null)
            return;

        if (item instanceof IMessageRecyclerItem) {
            recycleGifsInText(((IMessageRecyclerItem) item).getSpanned(), force);
        } else if (item instanceof TextView) {
            recycleGifsInText(((TextView) item).getText(), force);
        } else {
            Logger.debug("Check item=" + item.toString() + ", " + item.getClass().toString());
        }
    }

    public static void recycleGifsInText(CharSequence sequence, boolean force) {
        if (TextUtils.isEmpty(sequence))
            return;

        Spanned message = (Spanned) sequence;

        a[] imageSpans = message.getSpans(0, message.length(), a.class);
        if (imageSpans.length == 0)
            return;

        for (a image: imageSpans) {
            if (image == null)
                continue;

            Drawable drawable = image.a();

            if (!(drawable instanceof IDrawable))
                continue;

            IDrawable drawableContainer = (IDrawable) drawable;

            if (drawableContainer.getDrawable() instanceof c) {
                c gifDrawable = (c) drawableContainer.getDrawable();
                gifDrawable.stop();
                if (force)
                    gifDrawable.setCallback(null);
            }
        }
    }

    public static void recycleAdapterItems(List<t> list) {
        if (list == null || list.size() == 0)
            return;

        for (t item : list) {
            recycleObject(item, true);
        }
    }

    public static void recycleAdapterItems(List<t> list, int range) {
        if (range == 0)
            return;

        if (list == null || list.size() == 0)
            return;

        if (list.size() <= range) {
            Logger.debug("Bad range: " + range + " [list size=" + list.size() + "]");
            return;
        }

        for (int i = 0; i < range; i++) {
            recycleObject(list.get(i), true);
        }
    }
}
