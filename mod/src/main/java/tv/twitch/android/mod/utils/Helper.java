package tv.twitch.android.mod.utils;

import android.os.Handler;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;


import com.google.android.exoplayer2.PlaybackParameters;

import java.util.Collections;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import tv.twitch.a.k.z.b.r.h;
import tv.twitch.android.api.i1.g1;
import tv.twitch.android.mod.activities.Settings;
import tv.twitch.android.mod.bridges.LoaderLS;
import tv.twitch.android.mod.bridges.StaticUrlDrawable;
import tv.twitch.android.mod.emotes.EmoteManager;
import tv.twitch.android.mod.settings.PrefManager;
import tv.twitch.android.models.Playable;
import tv.twitch.android.models.channel.ChannelInfo;
import tv.twitch.android.models.clips.ClipModel;
import tv.twitch.android.models.communitypoints.ActiveClaimModel;
import tv.twitch.android.shared.chat.communitypoints.models.CommunityPointsModel;

public class Helper {
    private static final ConcurrentHashMap<Integer, Integer> sFixedColors = new ConcurrentHashMap<>();
    private static final Set<Integer> sFineColors = Collections.newSetFromMap(new ConcurrentHashMap<Integer, Boolean>());

    private final Handler mHandler;

    private static String sLastClaimId;

    private int mCurrentChannel = 0;

    private final PrefManager mPrefManager;
    private final EmoteManager mEmoteManager;

    public Helper(PrefManager prefManager, EmoteManager emoteManager) {
        mPrefManager = prefManager;
        mEmoteManager = emoteManager;
        mHandler = new Handler();
    }

    private static boolean checkClaim(CommunityPointsModel pointsModel) {
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

    public void clicker(final View pointButtonView, CommunityPointsModel pointsModel) {
        if (!mPrefManager.isClickerOn())
            return;

        if (pointButtonView == null) {
            Logger.error("pointButtonView is null");
            return;
        }

        if (checkClaim(pointsModel)) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (pointButtonView != null)
                        pointButtonView.performClick();
                }
            }, 1000);
        }
    }

    public void saveToClipboard(String text) {
        ClipboardManager clipboard = (ClipboardManager) LoaderLS.getInstance().getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard != null) {
            ClipData clip = ClipData.newPlainText("text", text);
            clipboard.setPrimaryClip(clip);
            showToast(String.format(Locale.ENGLISH, "«%s» copied to clipboard", text));
        } else {
            Logger.error("clipboard is null");
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

    public static int getChannelId(g1 playableModelParser, Playable playable) {
        if (playableModelParser == null) {
            Logger.error("playableModelParser is null");
            return 0;
        }
        if (playable == null) {
            Logger.error("playable is null");
            return 0;
        }

        int channelId;
        if (playable instanceof ClipModel) {
            channelId = ((ClipModel) playable).getBroadcasterId();
        } else {
            channelId = playableModelParser.a(playable);
        }

        return channelId;
    }

    public void newRequest(final g1 playableModelParser, final Playable playable) {
        int channelId = getChannelId(playableModelParser, playable);

        if (mPrefManager.isEmotesOn())
            mEmoteManager.requestChannelEmoteSet(channelId, true);
    }

    public void newRequest(ChannelInfo channelInfo) {
        if (channelInfo == null) {
            Logger.error("channelInfo is null");
            return;
        }

        if (mPrefManager.isEmotesOn())
            mEmoteManager.requestChannelEmoteSet(channelInfo.getId(), false);
    }

    public static void openSettings() {
        startActivity(Settings.class);
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
        Toast.makeText(LoaderLS.getInstance().getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    public static String getSetName(String org, String setId) {
        if (!TextUtils.isEmpty(setId)) {
            if (setId.equals(ChatUtils.EmoteSet.GLOBAL.getId()))
                return "BetterTTV Global Emotes";
            else if (setId.equals(ChatUtils.EmoteSet.FFZ.getId()))
                return "FFZ Emotes";
            else if (setId.equals(ChatUtils.EmoteSet.BTTV.getId()))
                return "BetterTTV Emotes";
        }

        return org;
    }

    // TODO: __REPLACE_INIT_RES
    public h getUrlDrawableObject(h org) {
        if (org == null)
            return null;

        if (mPrefManager.isDisableGifs()) {
            return new StaticUrlDrawable(org);
        }

        return org;
    }

    public boolean isDisableRecentSearch() {
        return mPrefManager.isDisableRecentSearch();
    }

    public int hookUsernameSpanColor(int color) {
        if (!mPrefManager.isFixBrightness())
            return color;

        if (!mPrefManager.isDarkTheme())
            return color;

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

    private float getPlayerSpeed() {
        return mPrefManager.getExoplayerSpeed();
    }

    public PlaybackParameters hookStandaloneMediaClockInit() {
        final float speed = getPlayerSpeed();
        if (speed == 1.0f)
            return PlaybackParameters.e;

        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : stackTraceElements) {
            if (element == null)
                continue;
            if ((!TextUtils.isEmpty(element.getClassName()) && element.getClassName().equals("tv.twitch.a.k.q.j0.v") || (!TextUtils.isEmpty(element.getFileName()) && element.getFileName().equals("VodPlayerPresenter.kt"))))
                return new PlaybackParameters(speed);
        }

        return PlaybackParameters.e;
    }

    public boolean hookPrev(boolean org) {
        return mPrefManager.isPreventMsg() || org;
    }

    public void hook_helper() {
        Object o = hookStandaloneMediaClockInit();  // TODO: __HOOK
        o = getUrlDrawableObject(null);  // TODO: __HOOK
    }
}
