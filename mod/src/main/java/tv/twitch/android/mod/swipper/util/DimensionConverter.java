package tv.twitch.android.mod.swipper.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class DimensionConverter {
    public static int dipToPix(Context context, int dip) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, displayMetrics);
    }
}
