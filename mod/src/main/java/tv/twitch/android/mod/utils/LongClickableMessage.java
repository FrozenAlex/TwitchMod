package tv.twitch.android.mod.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Locale;

import tv.twitch.android.mod.bridges.LoaderLS;
import tv.twitch.android.models.chat.MessageToken;

public class LongClickableMessage extends ClickableSpan {
    private final List<MessageToken> tokens;

    public LongClickableMessage(List<MessageToken> tokens) {
        this.tokens = tokens;
    }

    void onLongClick(@NonNull View view) {
        Context context = LoaderLS.getInstance().getApplicationContext();
        if (context != null) {
            String message = ChatUtils.getMessage(tokens);
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            if (clipboard != null) {
                ClipData clip = ClipData.newPlainText("text", message);
                clipboard.setPrimaryClip(clip);
                Helper.showToast(String.format(Locale.ENGLISH, "«%s» copied to clipboard", message));
            }
        } else {
            Logger.debug("context is null");
        }
    }

    @Override
    public void onClick(@NonNull View view) {
        Logger.debug("click!");
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setUnderlineText(false);
    }
}
