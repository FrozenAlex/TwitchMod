package tv.twitch.a.l.d.n1;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;

import tv.twitch.android.mod.settings.PrefManager;

// Source: ChatUtil
public class d {
    private final Spannable org(Spanned spanned, String str, h hVar) { // TODO: __RENAME
        return null;
    }

    private final Spannable a(Spanned spanned, String str, h hVar) { // TODO: __ADD
        if (!PrefManager.isPreventMsg())
            return org(spanned, str, hVar);

        f[] clickableUsernameSpans = spanned.getSpans(0, spanned.length(), f.class);
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
