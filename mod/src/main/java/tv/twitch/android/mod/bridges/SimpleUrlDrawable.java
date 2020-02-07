package tv.twitch.android.mod.bridges;


import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import tv.twitch.a.l.v.b.q.h;

public class SimpleUrlDrawable extends h {
    public SimpleUrlDrawable(h org) {
        super(org.b, org.c);
        a(org.a);
    }

    public void draw(Canvas canvas) {
        Drawable drawable = this.a;
        if (drawable != null && canvas != null) {
            drawable.draw(canvas);
        }
    }
}
