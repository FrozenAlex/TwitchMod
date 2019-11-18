package tv.twitch.a.m.x.b.p;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.load.p.g.c;

import h.v.d.j;
import tv.twitch.android.mod.settings.PrefManager;

// Source: UrlDrawable
public class i {
    private Drawable a;

    public enum b {
        c(24.0f),
        d(18.0f),
        e(18.0f);

        private final float b;

        private b(float f2) {
            this.b = f2;
        }

        public final float a() {
            return this.b;
        }
    }


    public void draw(Canvas canvas) { // TODO: __REPLACE
        j.b(canvas, "canvas");
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
