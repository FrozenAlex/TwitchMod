package tv.twitch.a.m.e.y0;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

// Source: ClickableUsernameSpan
public class f extends ClickableSpan {
    private final int b;
    private final String c;
    private final String d;
    private final String e;
    private final Object f;

    public f(int i2, String str, String str2, String str3, Object aVar) {
        this.b = i2;
        this.c = str;
        this.d = str2;
        this.e = str3;
        this.f = aVar;
    }

    public void onClick(View view) {
        //this.f.a(this.b, this.c, this.d, this.e);
    }

    public void updateDrawState(TextPaint textPaint) {
        super.updateDrawState(textPaint);
        textPaint.setUnderlineText(false);
    }
}