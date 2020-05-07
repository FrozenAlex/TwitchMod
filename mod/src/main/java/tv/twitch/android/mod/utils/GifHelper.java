package tv.twitch.android.mod.utils;

import android.graphics.drawable.Drawable;
import android.text.Spanned;
import android.text.TextUtils;

import com.bumptech.glide.load.n.g.c;

import java.util.List;

import tv.twitch.a.k.c0.b.s.a;
import tv.twitch.a.k.c0.b.s.h;
import tv.twitch.android.core.adapters.t;
import tv.twitch.android.mod.bridges.IMessageRecyclerItem;


public class GifHelper {
    public static void recycle(List<t> list) {
        if (list == null || list.size() == 0) {
            return;
        }

        for (t item : list) {
            recycle(item);
        }
    }

    public static void recycleRange(List<t> list, int range) {
        if (list == null || list.size() == 0) {
            return;
        }

        if (range == 0)
            return;

        if (list.size() <= range) {
            Logger.debug("Bad range: " + range + " [list size=" + list.size() + "]");
            return;
        }

        for (int i = 0; i < range; i++) {
            recycle(list.get(i));
        }
    }

    public static void recycle(t item) {
        if (item instanceof IMessageRecyclerItem) {
            recycle(((IMessageRecyclerItem) item).getSpanned());
        }
    }

    public static void recycle(Spanned spanned) {
        if (TextUtils.isEmpty(spanned)) {
            Logger.debug("empty spanned");
            return;
        }

        a[] spans = spanned.getSpans(0, spanned.length(), a.class);
        for (a span: spans) {
            if (span == null) {
                Logger.debug("span is null");
                continue;
            }

            Drawable spanDrawable = span.a();
            if (spanDrawable == null) {
                Logger.debug("span drawable is null");
                continue;
            }


            if (spanDrawable instanceof h) {
                Drawable drawable = ((h) spanDrawable).a;

                if (drawable == null) {
                    // FIXME:
                    Logger.debug("drawable is null");
                    continue;
                }

                if (drawable instanceof c) {
                    c gifDrawable = (c) drawable;
                    removeCallbacks(gifDrawable);
                }
            }
        }
    }

    public static void removeCallbacks(c gifDrawable) {
        if (gifDrawable == null) {
            Logger.debug("gifDrawable is null");
            return;
        }

        Logger.debug("gifDrawable=" + Integer.toHexString(gifDrawable.hashCode()));

        gifDrawable.setCallback(null);
        gifDrawable.stop();
    }
}
