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
    private static final int LONG_CLICK_TIME = 799;

    private static LongClickLinkMovementMethod instance;

    private Handler longClickHandler;
    private boolean itsLongPress = false;

    @Override
    public boolean onTouchEvent(final TextView widget, Spannable buffer, MotionEvent event) {
        int action = event.getAction();

        if (action == MotionEvent.ACTION_CANCEL) {
            if (longClickHandler != null) {
                longClickHandler.removeCallbacksAndMessages(null);
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
                ClickableMessage clickableMessage = null;
                ClickableSpan childClickableSpan = null;

                for (ClickableSpan clickableSpan : link) {
                    if (clickableSpan instanceof ClickableMessage) {
                        if (clickableMessage == null)
                            clickableMessage = (ClickableMessage) clickableSpan;
                    } else if (childClickableSpan == null){
                        childClickableSpan = clickableSpan;
                    }
                }

                if (action == MotionEvent.ACTION_UP) {
                    if (longClickHandler != null) {
                        longClickHandler.removeCallbacksAndMessages(null);
                    }
                    if (!itsLongPress) {
                        if (childClickableSpan != null) {
                            childClickableSpan.onClick(widget);
                        }
                    }
                    itsLongPress = false;
                } else {
                    if (clickableMessage != null) {
                        final ClickableMessage cm = clickableMessage;
                        longClickHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                cm.onLongClick(widget);
                                itsLongPress = true;
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
        if (instance == null) {
            instance = new LongClickLinkMovementMethod();
            instance.longClickHandler = new Handler();
        }

        return instance;
    }
}
