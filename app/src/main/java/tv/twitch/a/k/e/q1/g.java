package tv.twitch.a.k.e.q1;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;

import tv.twitch.android.mod.bridges.LoaderLS;

// Source: ChatUtil
public class g {
    private final Spannable org(Spanned spanned, String str, l deletedMessageClickableSpan) { // TODO: __RENAME
        return null;
    }

    private final Spannable a(Spanned spanned, String str, l deletedMessageClickableSpan) { // TODO: __ADD
        try {
            if (!LoaderLS.getInstance().getPrefManager().isPreventMsg())
                return org(spanned, str, deletedMessageClickableSpan);

            l[] clickableUsernameSpans = spanned.getSpans(0, spanned.length(), l.class);
            if (clickableUsernameSpans == null || clickableUsernameSpans.length == 0)
                return null;

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
        } catch (Throwable th) {
            th.printStackTrace();
        }

        return org(spanned, str, deletedMessageClickableSpan);
    }
}
