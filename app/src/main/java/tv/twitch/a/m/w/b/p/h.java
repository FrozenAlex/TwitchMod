package tv.twitch.a.m.w.b.p;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.load.p.g.c;

import j.w.d.k;
import tv.twitch.android.mod.settings.PrefManager;

// TODO: rewrite
// Source: UrlDrawable
public class h {
    private Drawable a;

    public void draw(Canvas canvas) { // TODO: __REPLACE
        k.b(canvas, "canvas");
        Drawable drawable = this.a;
        if (drawable != null) {
            drawable.draw(canvas);
            if (!PrefManager.isDisableGifs()) {
                if (drawable instanceof c) {
                    ((c) drawable).start();
                }
            }
        }
    }
}
