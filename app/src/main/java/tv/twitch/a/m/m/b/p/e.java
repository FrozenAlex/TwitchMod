package tv.twitch.a.m.m.b.p;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.load.p.g.c;

import h.v.d.j;

// Source: UrlDrawable
public class e {
    private Drawable a;

    public enum b {
        b(24.0f), // Emote
        c(18.0f); // Badge

        private final float a;

        private b(float f2) {
            this.a = f2;
        }

        public final float a() {
            return this.a;
        }
    }

    public void draw(Canvas canvas) {
        j.b(canvas, "canvas");
        Drawable drawable = this.a;
        if (drawable != null) {
            drawable.draw(canvas);
            if (drawable instanceof c) {
                ((c) drawable).start(); // TODO: __REMOVE
            }
        }
    }
}
