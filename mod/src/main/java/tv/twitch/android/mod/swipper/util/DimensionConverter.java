package tv.twitch.android.mod.swipper.util;

import android.content.Context;
import android.util.TypedValue;

public class DimensionConverter {
    public static int dipToPix(Context context, int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
    }
}
