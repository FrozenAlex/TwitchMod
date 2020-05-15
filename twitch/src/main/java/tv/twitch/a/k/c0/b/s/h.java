package tv.twitch.a.k.c0.b.s;

import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;


/**
 * Source: UrlDrawable
 */
public class h extends BitmapDrawable { // TODO: __REMOVE_FINAL
    public Drawable a; // TODO: __CHANGE_MOD
    public final String b; // TODO: __CHANGE_MOD
    public final d c; // TODO: __CHANGE_MOD


    /**
     * @param str Url
     * @param dVar MediaSpan
     */
    public h(String str, d dVar) {
        this.b = str;
        this.c = dVar;
    }

    /**
     * @param drawable emote
     */
    public final void a(Drawable drawable) {
        this.a = drawable;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }
}
