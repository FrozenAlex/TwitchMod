package tv.twitch.android.mod.utils;


import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.NonNull;


public abstract class LongClickableMessage extends ClickableSpan {
    abstract void onLongClick(@NonNull View view);
}
