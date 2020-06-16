package tv.twitch.android.mod.utils;


import android.view.View;


public class Clicker implements Runnable {
    private final View.OnClickListener mListener;

    public Clicker(View.OnClickListener listener) {
        mListener = listener;
    }

    @Override
    public void run() {
        if (mListener != null) {
            mListener.onClick(null);
        }
    }
}
