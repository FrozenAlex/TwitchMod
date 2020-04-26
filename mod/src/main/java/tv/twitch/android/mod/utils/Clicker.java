package tv.twitch.android.mod.utils;

import android.view.View;

import java.lang.ref.WeakReference;

public class Clicker implements Runnable {
    final private WeakReference<View> mPointButton;

    public Clicker(View pointButton) {
        mPointButton = new WeakReference<>(pointButton);
    }

    @Override
    public void run() {
        View button = mPointButton.get();
        if (button == null) {
            Logger.warning("pointButton is null");
            return;
        }

        button.performClick();
    }
}
