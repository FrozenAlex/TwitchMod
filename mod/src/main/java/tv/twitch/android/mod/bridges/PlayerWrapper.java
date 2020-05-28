package tv.twitch.android.mod.bridges;


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

import tv.twitch.android.mod.settings.PrefManager;
import tv.twitch.android.mod.swipper.Swipper;
import tv.twitch.android.mod.utils.Logger;

public class PlayerWrapper extends RelativeLayout {

    private static final int STATUS_BAR_HEIGHT = 25;
    private static int PADDING_IGNORE = 25;

    private ViewGroup mPlayerOverlayContainer;
    private ViewGroup mDebugPanelContainer;
    private ViewGroup mFloatingChatContainer;
    private final Swipper mSwipper;

    private int mTouchSlop;
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

        PADDING_IGNORE = Math.round(STATUS_BAR_HEIGHT * this.getResources().getDisplayMetrics().density);

        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop() * 2;
        mPlayerOverlayContainer = findViewById(LoaderLS.PLAYER_OVERLAY_ID);
        mFloatingChatContainer = findViewById(LoaderLS.FLOATING_CHAT_CONTAINER_ID);
        mDebugPanelContainer = findViewById(LoaderLS.DEBUG_PANEL_CONTAINER_ID);

        if (mPlayerOverlayContainer == null) {
            Logger.error("mPlayerOverlayContainer is null");
            return;
        }
        if (mFloatingChatContainer == null) {
            Logger.error("mFloatingChatContainer is null");
            return;
        }
        if (mDebugPanelContainer == null) {
            Logger.error("mDebugPanelContainer is null");
            return;
        }

        initializeSwipper();
    }

    private void initializeSwipper() {
        mSwipper.setOverlay(mPlayerOverlayContainer);

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

        int childCount = viewGroup.getChildCount();
        if (childCount < 1)
            return null;

        return viewGroup.getChildAt(0);
    }

    private boolean checkCollisions() {
        if (!isHit(mPlayerOverlayContainer, mStartPosX, mStartPosY)) {
            Logger.debug("Ignore scrolling: Wrong area: x=" + mStartPosX + ", y="+ mStartPosY);
            return false;
        }

        if (isVisible(mDebugPanelContainer)) {
            ViewGroup list = mDebugPanelContainer.findViewById(LoaderLS.VIDEO_DEBUG_LIST_ID);
            if (isVisible(getFirstChild(mDebugPanelContainer)) && isVisible(list) && isHit(list, mStartPosX, mStartPosY)) {
                Logger.debug("Ignore scrolling: Debug panel area: x=" + mStartPosX + ", y=" + mStartPosY);
                return false;
            }
        }

        if (isVisible(mFloatingChatContainer)) {
            ViewGroup container = mFloatingChatContainer.findViewById(LoaderLS.MESSAGES_CONTAINER_ID);
            if (isVisible(container) && isHit(container, mStartPosX, mStartPosY)) {
                Logger.debug("Ignore scrolling: Floating chat area: x=" + mStartPosX + ", y=" + mStartPosY);
                return false;
            }
        }

        return true;
    }

    private boolean checkArea(MotionEvent event) {
        if (mStartPosY <= PADDING_IGNORE) {
            Logger.debug("Ignore scrolling: top PADDING_IGNORE=" + PADDING_IGNORE +", val="+ mStartPosY);
            return false;
        }

        float overlayBottomY = mPlayerOverlayContainer.getY()+mPlayerOverlayContainer.getHeight();
        if (mStartPosY >= (overlayBottomY - PADDING_IGNORE)) {
            Logger.debug("Ignore scrolling: bottom PADDING_IGNORE=" + PADDING_IGNORE +", val="+ overlayBottomY);
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mSwipper.onTouchEvent(event);
    }
}