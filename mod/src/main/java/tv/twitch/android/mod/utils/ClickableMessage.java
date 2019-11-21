package tv.twitch.android.mod.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.List;

import tv.twitch.android.mod.bridges.ContextHelper;
import tv.twitch.android.models.chat.MessageToken;

public class ClickableMessage extends ClickableSpan {
    private final ContextHelper contextHelper;
    private final List<MessageToken> tokens;

    public ClickableMessage(ContextHelper ch, List<MessageToken> tokens) {
        this.contextHelper = ch;
        this.tokens = tokens;
    }

    @Override
    public void onClick(@NonNull View view) {
        Context context = contextHelper.getAppContext();
        if (context != null) {
            String message = ChatUtils.getMessage(tokens);
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            if (clipboard != null) {
                ClipData clip = ClipData.newPlainText("text", message);
                clipboard.setPrimaryClip(clip);
                Helper.showToast(context, message);
            }
        } else {
            Logger.debug("context is null");
        }
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setUnderlineText(false);
    }
}
