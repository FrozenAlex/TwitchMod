package tv.twitch.android.mod.swipper;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


import tv.twitch.android.mod.swipper.util.BrightnessHelper;
import tv.twitch.android.mod.swipper.view.SwipperOverlay;

public class Swipper implements GestureDetector.OnGestureListener {
    private final static int DELAY_TIMEOUT = 500;
    private final static float H = 0.66f;

    private final SwipperOverlay mSwipperOverlay;
    private final GestureDetector mGestureDetector;

    private final Activity mContext;
    private final AudioManager mAudioManager;

    private final Handler mHandler;
    private Runnable mProgressHide;

    private boolean mIsLeftArea = false;

    private int mOldVolume;
    private int mOldBrightness;


    public void initialize(ViewGroup viewGroup) {
        RelativeLayout.LayoutParams overlayParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        RelativeLayout relativeLayout = new RelativeLayout(mContext);
        relativeLayout.setGravity(Gravity.CENTER);
        relativeLayout.addView(mSwipperOverlay);

        mSwipperOverlay.setMaxVolume(getSystemMaxVolume());
        mSwipperOverlay.setVolume(getSystemVolume());
        mSwipperOverlay.setBrightness(BrightnessHelper.getWindowBrightness(mContext));

        viewGroup.addView(relativeLayout, overlayParams);
        mSwipperOverlay.requestLayout();
        mSwipperOverlay.setVisibility(View.VISIBLE);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getActionMasked()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                delayHide();
        }

        return mGestureDetector.onTouchEvent(motionEvent);
    }

    private void delayHide() {
        if (mProgressHide != null) {
            mHandler.removeCallbacks(mProgressHide);
            mProgressHide = null;
        }

        mProgressHide = new Runnable() {
            @Override
            public void run() {
                hideAll();
            }
        };

        mHandler.postDelayed(mProgressHide, DELAY_TIMEOUT);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        mOldBrightness = BrightnessHelper.getWindowBrightness(mContext);
        mOldVolume = getSystemVolume();
        mIsLeftArea = e.getX() < mSwipperOverlay.getWidth() / 2.0f;

        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        final float diff = e1.getY()-e2.getY();

        if (mIsLeftArea) {
            updateVolumeProgress(diff);
        } else {
            updateBrightnessProgress(diff);
        }

        return true;
    }

    public Swipper(Activity activity) {
        mContext = activity;
        mHandler = new Handler();

        mGestureDetector = new GestureDetector(mContext, this);
        mAudioManager = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);

        mSwipperOverlay = new SwipperOverlay(mContext);
    }

    public int getSystemMaxVolume() {
        return mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    }

    public int getSystemVolume() {
        return mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    public void setSystemVolume(int index) {
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, index, 0);
    }

    private int getOverlayHeight() {
        return mSwipperOverlay.getHeight();
    }

    private int getMaxVolume() {
        return mSwipperOverlay.getMaxVolume();
    }

    private int getMaxBrightness() {
        return mSwipperOverlay.getMaxBrightness();
    }

    private int calculate(float delta, int oldStep, int max) {
        float height = getOverlayHeight() * H;

        float step = height / max;
        int diff = (int) (delta / step);

        return Math.max(0, Math.min(max, oldStep + diff));
    }

    private void updateVolumeProgress(float delta) {
        updateVolume(calculate(delta, mOldVolume, getMaxVolume()));
    }

    private void updateBrightnessProgress(float delta) {
        updateBrightness(calculate(delta, mOldBrightness, getMaxBrightness()));
    }

    private void updateBrightness(int val) {
        BrightnessHelper.setWindowBrightness(mContext, val);
        mSwipperOverlay.setBrightness(val);
        mSwipperOverlay.showBrightness();
    }

    public void updateVolume(int index) {
        setSystemVolume(index);
        mSwipperOverlay.setVolume(index);
        mSwipperOverlay.showVolume();
    }

    public void hideAll() {
        if (mProgressHide != null) {
            mHandler.removeCallbacks(mProgressHide);
            mProgressHide = null;
        }

        mSwipperOverlay.hideVolume();
        mSwipperOverlay.hideBrightness();
    }

    @Override
    public void onShowPress(MotionEvent e) {}

    @Override
    public void onLongPress(MotionEvent e) {}

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }
}
