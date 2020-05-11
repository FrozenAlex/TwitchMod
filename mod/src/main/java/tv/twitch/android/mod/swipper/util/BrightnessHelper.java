package tv.twitch.android.mod.swipper.util;

import android.app.Activity;
import android.view.WindowManager;

public class BrightnessHelper {
    public static int getWindowBrightness(Activity context) {
        WindowManager.LayoutParams layoutParams = context.getWindow().getAttributes();

        return Math.max((int) (layoutParams.screenBrightness * 100), 0);
    }

    public static void setWindowBrightness(Activity context, int val){
        WindowManager.LayoutParams layoutParams = context.getWindow().getAttributes();
        layoutParams.screenBrightness = ((float) val) / 100;
        context.getWindow().setAttributes(layoutParams);
    }
}
