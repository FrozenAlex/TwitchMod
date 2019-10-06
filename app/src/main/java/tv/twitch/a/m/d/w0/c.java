package tv.twitch.a.m.d.w0;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;

// Source: ChatUtil
public class c {
    private final Spannable a(Spanned spanned, String str, g gVar) { // TODO: replace
        e[] clickableUsernameSpans = spanned.getSpans(0, spanned.length(), e.class);
        if (clickableUsernameSpans == null)
            return null;
        if (clickableUsernameSpans.length == 0) {
            return null;
        }

        int spanEnd = spanned.getSpanEnd(clickableUsernameSpans[0]);
        int length = 2 + spanEnd;
        if (length < spanned.length() && spanned.subSequence(spanEnd, length).toString().equals(": ")) {
            spanEnd = length;
        }
        SpannableStringBuilder ssb = new SpannableStringBuilder(spanned, 0, spanEnd);
        SpannableStringBuilder ssb2 = new SpannableStringBuilder(spanned, spanEnd, spanned.length());
        ssb.append(ssb2);
        ssb.setSpan(new StrikethroughSpan(), ssb.length() - ssb2.length(), ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssb;
    }
}
