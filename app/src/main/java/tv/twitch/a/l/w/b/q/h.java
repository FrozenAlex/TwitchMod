package tv.twitch.a.l.w.b.q;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.load.n.g.c;

import tv.twitch.android.mod.settings.PrefManager;

// Source: UrlDrawable
public class h {
    private Drawable a;

    public void draw(Canvas canvas) { // TODO: __REPLACE
        Drawable drawable = this.a;
        if (drawable != null && canvas != null) {
            drawable.draw(canvas);
            if (!PrefManager.isDisableGifs()) {
                if (drawable instanceof c) {
                    ((c) drawable).start();
                }
            }
        }
    }
}
