package tv.twitch.android.mod.utils;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import tv.twitch.android.app.core.v1;
import tv.twitch.android.mod.bridges.LoaderLS;

public class SnackbarHelper {
    public static void showText(View view, final String message) {
        if (view == null) {
            Logger.error("view is null");
            return;
        }

        if (TextUtils.isEmpty(message)) {
            Logger.warning("Empty message");
        }

        Snackbar snack = v1.a(Snackbar.make(view, message, Snackbar.LENGTH_LONG));
        if (snack == null) {
            Logger.error("snack is null");
            return;
        }

        snack.show();
    }

    public static void showUrl(View view, final String message, final String url) {
        if (view == null) {
            Logger.error("view is null");
            return;
        }

        if (TextUtils.isEmpty(message)) {
            Logger.warning("Empty message");
        }

        Snackbar snack = v1.a(Snackbar.make(view, message, Snackbar.LENGTH_LONG));
        if (snack == null) {
            Logger.error("snack is null");
            return;
        }

        snack.setAction("Open", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                LoaderLS.getInstance().startActivity(intent);
            }
        });
        snack.show();
    }
}
