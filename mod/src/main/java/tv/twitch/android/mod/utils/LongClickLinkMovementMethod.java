package tv.twitch.android.mod.utils;

import android.os.Handler;
import android.text.Layout;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.widget.TextView;

public class LongClickLinkMovementMethod extends LinkMovementMethod {
    private static final int LONG_CLICK_TIME = 700;

    private static LongClickLinkMovementMethod sInstance;

    private Handler mLongClickHandler;
    private boolean bItsLongPress = false;

    @Override
    public boolean onTouchEvent(final TextView widget, Spannable buffer, MotionEvent event) {
        int action = event.getAction();

        if (action == MotionEvent.ACTION_CANCEL) {
            if (mLongClickHandler != null) {
                mLongClickHandler.removeCallbacksAndMessages(null);
            }
        }

        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            x -= widget.getTotalPaddingLeft();
            y -= widget.getTotalPaddingTop();

            x += widget.getScrollX();
            y += widget.getScrollY();

            Layout layout = widget.getLayout();
            int line = layout.getLineForVertical(y);
            int off = layout.getOffsetForHorizontal(line, x);

            final ClickableSpan[] link = buffer.getSpans(off, off, ClickableSpan.class);

            if (link.length > 0) {
                LongClickableMessage longClickableMessage = null;
                ClickableSpan childClickableSpan = null;

                for (ClickableSpan clickableSpan : link) {
                    if (clickableSpan instanceof LongClickableMessage) {
                        if (longClickableMessage == null)
                            longClickableMessage = (LongClickableMessage) clickableSpan;
                    } else if (childClickableSpan == null){
                        childClickableSpan = clickableSpan;
                    }
                }

                if (action == MotionEvent.ACTION_UP) {
                    if (mLongClickHandler != null) {
                        mLongClickHandler.removeCallbacksAndMessages(null);
                    }
                    if (!bItsLongPress) {
                        if (childClickableSpan != null) {
                            childClickableSpan.onClick(widget);
                        }
                    }
                    bItsLongPress = false;
                } else {
                    if (longClickableMessage != null) {
                        final LongClickableMessage cm = longClickableMessage;
                        mLongClickHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                cm.onLongClick(widget);
                                bItsLongPress = true;
                            }
                        }, LONG_CLICK_TIME);
                    }
                }
                return true;
            }
        }

        return super.onTouchEvent(widget, buffer, event);
    }

    public static MovementMethod getInstance() {
        if (sInstance == null) {
            synchronized (LongClickLinkMovementMethod.class) {
                if (sInstance == null) {
                    sInstance = new LongClickLinkMovementMethod();
                    sInstance.mLongClickHandler = new Handler();
                }
            }
        }

        return sInstance;
    }
}
