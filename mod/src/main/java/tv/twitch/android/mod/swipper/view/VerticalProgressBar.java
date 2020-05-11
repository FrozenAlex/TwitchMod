package tv.twitch.android.mod.swipper.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;

public class VerticalProgressBar extends ProgressBar {
    private static final int BACKGROUND_COLOR = Color.WHITE;
    private static final int PROGRESS_COLOR = Color.parseColor("#6441A5");

    public VerticalProgressBar(Context context) {
        super(context,null, android.R.attr.progressBarStyleHorizontal);
        this.setProgressDrawable(generateProgressDrawable());
        this.setId(View.generateViewId());
        this.setBackgroundColor(BACKGROUND_COLOR);
    }

    private static Drawable generateProgressDrawable() {
        GradientDrawable backgroundShape = new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, new int[]{BACKGROUND_COLOR, BACKGROUND_COLOR, BACKGROUND_COLOR});
        GradientDrawable progressShape = new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, new int[]{PROGRESS_COLOR, PROGRESS_COLOR, PROGRESS_COLOR});

        ClipDrawable progressClip = new ClipDrawable(progressShape, Gravity.BOTTOM, ClipDrawable.VERTICAL);
        Drawable[] layers = {backgroundShape, progressClip};

        return new LayerDrawable(layers);
    }
}
