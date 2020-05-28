package tv.twitch.android.mod.utils;

import android.graphics.drawable.Drawable;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.TextView;

import com.bumptech.glide.load.n.g.c;

import java.util.List;

import tv.twitch.a.k.d0.b.s.a;
import tv.twitch.android.core.adapters.t;
import tv.twitch.android.mod.bridges.IDrawable;
import tv.twitch.android.mod.bridges.IMessageRecyclerItem;


public class GifHelper {
    public static void recycle(Object item, boolean force) {
        if (item == null) {
            Logger.debug("item is null");
            return;
        }

        if (item instanceof IMessageRecyclerItem) {
            recycleText(((IMessageRecyclerItem) item).getSpanned(), force);
        } else if (item instanceof TextView) {
            recycleText(((TextView) item).getText(), force);
        } else {
            Logger.debug("Check item=" + item.toString() + ", class=" + item.getClass().toString());
        }
    }

    public static void recycleText(CharSequence sequence, boolean force) {
        if (TextUtils.isEmpty(sequence)) {
            Logger.debug("empty sequence");
            return;
        }

        if (!(sequence instanceof Spanned)) {
            Logger.debug("Bad sequence: " + sequence);
            return;
        }

        Spanned message = (Spanned) sequence;

        a[] imageSpans = message.getSpans(0, message.length(), a.class);

        int gifsRecycled = 0;
        for (a image: imageSpans) {
            if (image == null) {
                Logger.debug("image is null");
                continue;
            }

            Drawable drawable = image.a();

            if (!(drawable instanceof IDrawable)) {
                Logger.debug("Skip drawable="+drawable.toString() + ", class="+drawable.getClass().toString());
                continue;
            }

            IDrawable drawableContainer = (IDrawable) drawable;

            if (drawableContainer.getDrawable() instanceof c) {
                c gifDrawable = (c) drawableContainer.getDrawable();
                // Logger.debug("GIF drawable=" + gifDrawable.toString());
                gifDrawable.stop();
                if (force) {
                    gifDrawable.setCallback(null);
                }
                gifsRecycled++;
            }

            if (gifsRecycled > 0)
                Logger.debug("gifsRecycled=" + gifsRecycled);
        }
    }

    public static void recycleAdapter(List<t> list) {
        if (list == null || list.size() == 0) {
            Logger.debug("Empty list");
            return;
        }

        for (t item : list) {
            recycle(item, true);
        }
    }

    public static void recycleAdapterRange(List<t> list, int range) {
        if (range == 0)
            return;

        if (list == null || list.size() == 0) {
            Logger.debug("Empty list");
            return;
        }

        if (list.size() <= range) {
            Logger.debug("Bad range: " + range + " [list size=" + list.size() + "]");
            return;
        }

        for (int i = 0; i < range; i++) {
            recycle(list.get(i), true);
        }
    }
}
