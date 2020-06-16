package tv.twitch.android.mod.bridges;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import tv.twitch.android.mod.swipper.Swipper;
import tv.twitch.android.mod.utils.Logger;

public class PlayerWrapper extends RelativeLayout {
    private static final int PADDING_DEFAULT_IGNORE = 25;
    private static int PADDING_DEVICE_IGNORE = 25;

    private ViewGroup mPlayerOverlayContainer;
    private ViewGroup mDebugPanelContainer;
    private ViewGroup mFloatingChatContainer;
    private final Swipper mSwipper;

    private int mTouchSlop;

    private int mStartPosY = -1;
    private int mStartPosX = -1;
    private boolean bInScrollArea = false;


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

    private float getDensity() {
        return this.getResources().getDisplayMetrics().density;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        PADDING_DEVICE_IGNORE = Math.round(PADDING_DEFAULT_IGNORE * getDensity());
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop() * 2;

        mPlayerOverlayContainer = findViewById(IDPub.PLAYER_OVERLAY_ID);
        if (mPlayerOverlayContainer == null) {
            Logger.error("mPlayerOverlayContainer is null. Update ID?");
            return;
        }

        mFloatingChatContainer = findViewById(IDPub.FLOATING_CHAT_CONTAINER_ID);
        if (mFloatingChatContainer == null) {
            Logger.error("mFloatingChatContainer is null. Update ID?");
            return;
        }

        mDebugPanelContainer = findViewById(IDPub.DEBUG_PANEL_CONTAINER_ID);
        if (mDebugPanelContainer == null) {
            Logger.error("mDebugPanelContainer is null. Update ID?");
            return;
        }

        initializeSwipper();
    }

    private void initializeSwipper() {
        mSwipper.setOverlay(mPlayerOverlayContainer);

        if (LoaderLS.getPrefManagerInstance().isVolumeSwipeEnabled())
            mSwipper.enableVolumeSwipe();

        if (LoaderLS.getPrefManagerInstance().isBrightnessSwipeEnabled())
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
                mStartPosY = -1;
                mStartPosX = -1;

                mSwipper.onTouchEvent(event);

                return false;
            case MotionEvent.ACTION_DOWN:
                mStartPosY = Math.round(event.getY());
                mStartPosX = Math.round(event.getX());

                bInScrollArea = checkArea(event);

                mSwipper.onTouchEvent(event);

                return false;
            case MotionEvent.ACTION_MOVE: {
                if (!bInScrollArea)
                    return false;

                if (event.getPointerCount() > 1) {
                    Logger.debug("Ignore scrolling: multi touch, val="+event.getPointerCount());
                    bInScrollArea = false;
                    return false;
                }

                int diff = getDistance(event);
                if (diff > mTouchSlop) {
                    Logger.debug("SCROLLING");
                    return mSwipper.onTouchEvent(event);
                }
                break;
            }
            case MotionEvent.ACTION_POINTER_DOWN:
                bInScrollArea = false;
                return false;
        }

        return false;
    }

    private static boolean isVisible(View view) {
        if (view == null)
            return false;

        return view.getVisibility() == VISIBLE;
    }

    private static boolean isHit(ViewGroup view, int x, int y) {
        if (view == null) {
            Logger.debug("view is null");
            return false;
        }
        Rect hitRect = new Rect();
        view.getHitRect(hitRect);

        return hitRect.contains(x, y);
    }

    private static View getFirstChild(ViewGroup viewGroup) {
        if (viewGroup == null)
            return null;

        if (viewGroup.getChildCount() < 1)
            return null;

        return viewGroup.getChildAt(0);
    }

    private boolean checkCollisions() {
        if (!isHit(mPlayerOverlayContainer, mStartPosX, mStartPosY)) {
            Logger.debug("Ignore scrolling: Wrong area: x=" + mStartPosX + ", y="+ mStartPosY);
            return false;
        }

        if (isVisible(mDebugPanelContainer)) {
            ViewGroup list = mDebugPanelContainer.findViewById(IDPub.VIDEO_DEBUG_LIST_ID);
            if (isVisible(getFirstChild(mDebugPanelContainer)) && isVisible(list) && isHit(list, mStartPosX, mStartPosY)) {
                Logger.debug("Ignore scrolling: Debug panel area: x=" + mStartPosX + ", y=" + mStartPosY);
                return false;
            }
        }

        if (isVisible(mFloatingChatContainer)) {
            ViewGroup container = mFloatingChatContainer.findViewById(IDPub.MESSAGES_CONTAINER_ID);
            if (isVisible(container) && isHit(container, mStartPosX, mStartPosY)) {
                Logger.debug("Ignore scrolling: Floating chat area: x=" + mStartPosX + ", y=" + mStartPosY);
                return false;
            }
        }

        return true;
    }

    private boolean checkArea(MotionEvent event) {
        if (mStartPosY <= PADDING_DEVICE_IGNORE) {
            Logger.debug("Ignore scrolling: top PADDING_IGNORE=" + PADDING_DEVICE_IGNORE +", val="+ mStartPosY);
            return false;
        }

        float overlayBottomY = mPlayerOverlayContainer.getY()+mPlayerOverlayContainer.getHeight();
        if (mStartPosY >= (overlayBottomY - PADDING_DEVICE_IGNORE)) {
            Logger.debug("Ignore scrolling: bottom PADDING_IGNORE=" + PADDING_DEVICE_IGNORE +", val="+ overlayBottomY);
            return false;
        }

        if (event.getPointerCount() > 1) {
            Logger.debug("Ignore scrolling: multi touch, val="+event.getPointerCount());
            return false;
        }

        if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            Logger.debug("Ignore scrolling: wrong orientation");
            return false;
        }

        return checkCollisions();
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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mSwipper.onTouchEvent(event);
    }
}