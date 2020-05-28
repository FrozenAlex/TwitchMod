package tv.twitch.a.k.d0.b.s;


import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;

import androidx.annotation.NonNull;


/**
 * Source: CenteredImageSpan
 */
public class a extends ImageSpan {
    private Drawable b;

    public a(@NonNull Drawable drawable) {
        super(drawable);
    }

    /**
     * @return UrlDrawable
     */
    public final Drawable a() {
        return b;
    }

}
