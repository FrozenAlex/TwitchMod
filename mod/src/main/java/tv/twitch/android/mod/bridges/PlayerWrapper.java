package tv.twitch.android.mod.bridges;


import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import tv.twitch.android.mod.settings.PrefManager;
import tv.twitch.android.mod.swipper.Swipper;
import tv.twitch.android.mod.utils.Logger;

public class PlayerWrapper extends RelativeLayout {

    private static final int STATUS_BAR_HEIGHT = 25;
    private static int TOP_PADDING_IGNORE = 25;

    private ViewGroup mPlayerOverlayContainer;
    private final Swipper mSwipper;

    private int mTouchSlop;
    private boolean bIsScrolling = false;
    private boolean bInScrollArea = false;
    private int mStartPosY = 0;
    private int mStartPosX = 0;

    public PlayerWrapper(Context context) {
        super(context);
        mSwipper = new Swipper((Activity) context);
    }

    public PlayerWrapper(Context context, AttributeSet attrs) {
        super(context, attrs);
        mSwipper = new Swipper((Activity) context);
    }

    public PlayerWrapper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mSwipper = new Swipper((Activity) context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        TOP_PADDING_IGNORE = Math.round(STATUS_BAR_HEIGHT * this.getResources().getDisplayMetrics().density);

        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        mPlayerOverlayContainer = findViewById(LoaderLS.PLAYER_OVERLAY_ID);

        if (mPlayerOverlayContainer == null) {
            Logger.error("mPlayerOverlayContainer is null");
            return;
        }

        initializeSwipper();
    }

    private void initializeSwipper() {
        mSwipper.setTwitchOverlay(mPlayerOverlayContainer);

        PrefManager prefManager = LoaderLS.getInstance().getPrefManager();
        if (prefManager.isVolumeSwipeEnabled())
            mSwipper.enableVolumeSwipe();

        if (prefManager.isBrightnessSwipeEnabled())
            mSwipper.enableBrightnessSwipe();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        final int action = event.getAction();

        if (!mSwipper.isEnabled())
            return false;

        switch (action) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                bIsScrolling = false;
                mStartPosY = -1;
                mStartPosX = -1;

                mSwipper.onTouchEvent(event);

                return false;
            case MotionEvent.ACTION_DOWN:
                mStartPosY = Math.round(event.getY());
                mStartPosX = Math.round(event.getX());

                bInScrollArea = checkArea(event);
                bIsScrolling = false;

                mSwipper.onTouchEvent(event);

                return false;
            case MotionEvent.ACTION_MOVE: {
                if (!bInScrollArea)
                    return false;

                if (bIsScrolling)
                    return true;

                if (event.getPointerCount() > 1) {
                    Logger.debug("Ignore scrolling: multi touch, val="+event.getPointerCount());
                    bInScrollArea = false;
                    bIsScrolling = false;
                    return false;
                }

                int diff = getDistance(event);
                if (diff > mTouchSlop) {
                    Logger.debug("SCROLLING");
                    bIsScrolling = true;
                    return true;
                }
                break;
            }
            case MotionEvent.ACTION_POINTER_DOWN:
                bInScrollArea = false;
                bIsScrolling = false;
                return false;
        }

        return false;
    }

    private boolean checkArea(MotionEvent event) {
        Rect hitRect = new Rect();
        mPlayerOverlayContainer.getHitRect(hitRect);

        if (mStartPosY <= TOP_PADDING_IGNORE) {
            Logger.debug("Ignore scrolling: TOP_PADDING_IGNORE=" + TOP_PADDING_IGNORE +", val="+ mStartPosY);
            return false;
        } else if (!hitRect.contains(mStartPosX, mStartPosY)) {
            Logger.debug("Ignore scrolling: Wrong area: x=" + mStartPosX + ", y="+ mStartPosY);
            return false;
        } else if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            Logger.debug("Ignore scrolling: wrong orientation");
            return false;
        } else if (event.getPointerCount() > 1) {
            Logger.debug("Ignore scrolling: multi touch, val="+event.getPointerCount());
            return false;
        }

        return true;
    }

    private int getDistance(MotionEvent moveEvent) {
        if (moveEvent == null) {
            Logger.debug("moveEvent is null");
            return 0;
        }

        if (mStartPosY == -1) {
            Logger.debug("startPosY == -1");
            return 0;
        }

        return Math.abs(mStartPosY - Math.round(moveEvent.getY()));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mSwipper.onTouchEvent(event);
    }
}