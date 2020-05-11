package tv.twitch.android.mod.bridges;


import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import tv.twitch.android.mod.swipper.Swipper;

public class PlayerWrapper extends RelativeLayout {
    private static final String TAG = "PlayerWrapper";

    private static final int PLAYER_OVERLAY_ID = 0x7f0b05ec;

    private static int TOP_PADDING_IGNORE = 25;

    private ViewGroup mPlayerOverlayContainer;
    private Swipper mSwipper;

    private int mTouchSlop;
    private boolean mIsScrolling = false;
    private boolean mInScrollArea = false;;
    private int mStartPosY = 0;
    private int mStartPosX = 0;

    public PlayerWrapper(Context context) {
        super(context);
    }

    public PlayerWrapper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlayerWrapper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        TOP_PADDING_IGNORE = Math.round(25 * this.getResources().getDisplayMetrics().density);
        mTouchSlop = ViewConfiguration.get(this.getContext()).getScaledTouchSlop();
        mPlayerOverlayContainer = findViewById(PLAYER_OVERLAY_ID);
        mSwipper = new Swipper((Activity) getContext());
        mSwipper.initialize(mPlayerOverlayContainer);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        final int action = event.getAction();

        Log.d("!onInterceptTouchEvent", event.toString());

        switch (action) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mIsScrolling = false;
                mStartPosY = -1;
                mStartPosX = -1;

                mSwipper.onTouchEvent(event);

                return false;
            case MotionEvent.ACTION_DOWN:
                mStartPosY = Math.round(event.getY());
                mStartPosX = Math.round(event.getX());

                mInScrollArea = checkArea(event);
                mIsScrolling = false;

                mSwipper.onTouchEvent(event);

                return false;
            case MotionEvent.ACTION_MOVE: {
                if (!mInScrollArea)
                    return false;

                if (mIsScrolling)
                    return true;

                if (event.getPointerCount() > 1) {
                    Log.d(TAG, "Ignore scrolling: multi touch, val="+event.getPointerCount());
                    mInScrollArea = false;
                    mIsScrolling = false;
                    return false;
                }

                int diff = getDistance(event);
                if (diff > mTouchSlop) {
                    Log.d(TAG, "SCROLLING");
                    mIsScrolling = true;
                    return true;
                }
                break;
            }
            case MotionEvent.ACTION_POINTER_DOWN:
                mInScrollArea = false;
                mIsScrolling = false;
                return false;
        }

        return false;
    }

    private boolean checkArea(MotionEvent event) {
        Rect hitRect = new Rect();
        mPlayerOverlayContainer.getHitRect(hitRect);

        if (mStartPosY <= TOP_PADDING_IGNORE) {
            Log.d(TAG, "Ignore scrolling: TOP_PADDING_IGNORE=" + TOP_PADDING_IGNORE +", val="+ mStartPosY);
            return false;
        } else if (!hitRect.contains(mStartPosX, mStartPosY)) {
            Log.d(TAG, "Ignore scrolling: Wrong area: x=" + mStartPosX + ", y="+ mStartPosY);
            return false;
        } else if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            Log.d(TAG, "Ignore scrolling: wrong orientation");
            return false;
        } else if (event.getPointerCount() > 1) {
            Log.d(TAG, "Ignore scrolling: multi touch, val="+event.getPointerCount());
            return false;
        }

        return true;
    }

    private int getDistance(MotionEvent moveEvent) {
        if (moveEvent == null) {
            Log.d(TAG, "moveEvent is null");
            return 0;
        }

        if (mStartPosY == -1) {
            Log.d(TAG, "startPosY == -1");
            return 0;
        }

        return Math.abs(mStartPosY - Math.round(moveEvent.getY()));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, event.toString());
        return mSwipper.onTouchEvent(event);
    }
}