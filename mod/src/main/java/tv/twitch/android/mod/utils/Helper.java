package tv.twitch.android.mod.utils;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import java.util.Collections;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import tv.twitch.android.api.i1.f1;
import tv.twitch.android.mod.activities.Settings;
import tv.twitch.android.mod.bridges.LoaderLS;
import tv.twitch.android.models.Playable;
import tv.twitch.android.models.clips.ClipModel;
import tv.twitch.android.models.communitypoints.ActiveClaimModel;
import tv.twitch.android.shared.chat.communitypoints.models.CommunityPointsModel;


public class Helper {
    private static final ConcurrentHashMap<Integer, Integer> sFixedColors = new ConcurrentHashMap<>();
    private static final Set<Integer> sFineColors = Collections.newSetFromMap(new ConcurrentHashMap<Integer, Boolean>());

    private final Handler mHandler;
    private String sLastClaimId;

    private int mCurrentChannel = 0;

    public Helper() {
        mHandler = new Handler();
    }

    public static int getChannelId(f1 playableModelParser, Playable playable) {
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

    public static void startActivity(Class activity) {
        if (activity == null) {
            Logger.error("activity is null");
            return;
        }

        Context context = LoaderLS.getInstance().getApplicationContext();
        Intent intent = new Intent(context, activity);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void showToast(String message) {
        Context context = LoaderLS.getInstance().getApplicationContext();
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void openSettings() {
        startActivity(Settings.class);
    }

    public void setClicker(final View pointButtonView, CommunityPointsModel pointsModel) {
        if (checkClaim(pointsModel)) {
            mHandler.postDelayed(new Clicker(pointButtonView), 1000);
        }
    }

    private boolean checkClaim(CommunityPointsModel pointsModel) {
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

        synchronized (Helper.class) {
            if (TextUtils.isEmpty(sLastClaimId) || !sLastClaimId.equals(claimId)) {
                sLastClaimId = claimId;
                return true;
            } else {
                Logger.debug("Same claim");
                return false;
            }
        }
    }

    public void setCurrentChannel(int channelID) {
        if (channelID < 0)
            channelID = 0;

        this.mCurrentChannel = channelID;
    }

    public int getCurrentChannel() {
        return this.mCurrentChannel;
    }

    public int getFineColor(int color) {
        if (sFineColors.contains(color))
            return color;

        if (sFixedColors.contains(color)) {
            Integer fixedColor = sFixedColors.get(color);
            if (fixedColor != null)
                return fixedColor;
        }

        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);

        if (hsv[2] >= .3) {
            if (!sFineColors.contains(color)) {
                synchronized (sFineColors) {
                    sFineColors.add(color);
                }
            }

            return color;
        }

        hsv[2] = (float) .3;
        int fixedColor = Color.HSVToColor(hsv);

        if (!sFixedColors.containsKey(fixedColor)) {
            synchronized (sFixedColors) {
                if (!sFixedColors.containsKey(fixedColor)) {
                    sFixedColors.put(color, fixedColor);
                }
            }
        }

        return fixedColor;
    }
}
