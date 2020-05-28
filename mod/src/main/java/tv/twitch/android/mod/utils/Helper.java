package tv.twitch.android.mod.utils;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import java.util.Locale;
import java.util.Random;

import tv.twitch.android.api.p1.i1;
import tv.twitch.android.mod.bridges.LoaderLS;
import tv.twitch.android.models.Playable;
import tv.twitch.android.models.clips.ClipModel;
import tv.twitch.android.models.communitypoints.ActiveClaimModel;
import tv.twitch.android.shared.chat.communitypoints.models.CommunityPointsModel;


public class Helper {
    private static final int MIN_CLICK_DELAY = 500;
    private static final int MAX_CLICK_DELAY = 1000;

    private final Handler mHandler;
    private String sLastClaimId;

    private int mCurrentChannel = 0;

    public Helper() {
        mHandler = new Handler();
    }

    public static void openUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            Logger.error("Empty url");
            return;
        }

        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "https://" + url;

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        LoaderLS.getInstance().startActivity(intent);
    }

    public static int getChannelId(i1 playableModelParser, Playable playable) {
        if (playableModelParser == null) {
            Logger.error("playableModelParser is null");
            return 0;
        }
        if (playable == null) {
            Logger.error("playable is null");
            return 0;
        }

        if (playable instanceof ClipModel) {
            return ((ClipModel) playable).getBroadcasterId();
        }

        return playableModelParser.a(playable);
    }

    public static void showToast(String message) {
        Context context = LoaderLS.getInstance().getApplicationContext();
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    private static int getClickDelay() {
        return new Random().nextInt(MAX_CLICK_DELAY-MIN_CLICK_DELAY) + MIN_CLICK_DELAY;
    }

    public void setClicker(final View pointButtonView, CommunityPointsModel pointsModel) {
        if (isActiveClaim(pointsModel)) {
            mHandler.postDelayed(new Clicker(pointButtonView), getClickDelay());
        }
    }

    private boolean isActiveClaim(CommunityPointsModel pointsModel) {
        if (pointsModel == null) {
            Logger.warning("pointsModel == null");
            return false;
        }

        ActiveClaimModel claimModel = pointsModel.getClaim();

        if (claimModel == null) {
            Logger.warning("claimModel == null");
            return false;
        }

        String claimId = claimModel.getId();
        if (TextUtils.isEmpty(claimId)) {
            Logger.warning("claimId == null");
            return false;
        }

        synchronized (this) {
            if (TextUtils.isEmpty(sLastClaimId) || !sLastClaimId.equals(claimId)) {
                sLastClaimId = claimId;
                return true;
            } else {
                return false;
            }
        }
    }

    public static void saveToClipboard(String text) {
        Context context = LoaderLS.getInstance().getApplicationContext();
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard == null) {
            Logger.error("clipboard is null");
            return;
        }

        clipboard.setPrimaryClip(ClipData.newPlainText("text", text));
        showToast(String.format(Locale.ENGLISH, "«%s» copied to clipboard", text));
    }

    public void setCurrentChannel(int channelID) {
        if (channelID < 0)
            channelID = 0;

        this.mCurrentChannel = channelID;
    }

    public int getCurrentChannel() {
        return this.mCurrentChannel;
    }
}
