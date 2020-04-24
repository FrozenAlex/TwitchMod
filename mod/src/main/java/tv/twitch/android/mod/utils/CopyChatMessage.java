package tv.twitch.android.mod.utils;

import android.text.TextPaint;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.List;

import tv.twitch.android.mod.bridges.LoaderLS;
import tv.twitch.android.models.chat.MessageToken;

public class CopyChatMessage extends LongClickableMessage {
    private final List<MessageToken> tokens;

    public CopyChatMessage(List<MessageToken> tokens) {
        this.tokens = tokens;
    }

    void onLongClick(@NonNull View view) {
        String message = ChatUtils.getRawMessage(tokens);
        LoaderLS.getInstance().getHelper().saveToClipboard(message);
    }

    @Override
    public void onClick(@NonNull View widget) {}

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setUnderlineText(false);
    }
}
