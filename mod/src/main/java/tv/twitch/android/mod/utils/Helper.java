package tv.twitch.android.mod.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;


import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.material.snackbar.Snackbar;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import tv.twitch.a.k.x.b.r.h;
import tv.twitch.android.api.i1.f1;
import tv.twitch.android.app.core.v1;
import tv.twitch.android.mod.activities.Settings;
import tv.twitch.android.mod.bridges.LoaderLS;
import tv.twitch.android.mod.bridges.SimpleUrlDrawable;
import tv.twitch.android.models.Playable;
import tv.twitch.android.models.channel.ChannelInfo;
import tv.twitch.android.models.clips.ClipModel;

public class Helper {
    private static final ConcurrentHashMap<Integer, Integer> sFixedColors = new ConcurrentHashMap<>();
    private static final Set<Integer> sFineColors = Collections.newSetFromMap(new ConcurrentHashMap<Integer, Boolean>());

    private int mCurrentChannel = 0;

    private static volatile Helper sInstance;

    private Helper() {
    }

    public static Helper getInstance() {
        if (sInstance == null) {
            synchronized(Helper.class) {
                if (sInstance == null)
                    sInstance = new Helper();
            }
        }
        return sInstance;
    }

    public static void clicker(final View pointButtonView) {
        if (!LoaderLS.getInstance().getPrefManager().isClickerOn())
            return;

        if (pointButtonView == null) {
            Logger.error("pointButtonView is null");
            return;
        }

        pointButtonView.performClick();
    }

    public void setCurrentChannel(int channelID) {
        if (channelID < 0)
            channelID = 0;

        this.mCurrentChannel = channelID;
    }

    public int getCurrentChannel() {
        return this.mCurrentChannel;
    }

    public void newRequest(final f1 playableModelParser, final Playable playable) {
        if (playableModelParser == null) {
            Logger.error("playableModelParser is null");
            return;
        }
        if (playable == null) {
            Logger.error("playable is null");
            return;
        }

        int channelId;
        if (playable instanceof ClipModel) {
            channelId = ((ClipModel) playable).getBroadcasterId();
        } else {
            channelId = playableModelParser.a(playable);
        }

        setCurrentChannel(channelId);
        LoaderLS.getInstance().getEmoteManager().requestChannelEmoteSet(channelId, false);
    }

    public void newRequest(ChannelInfo channelInfo) {
        if (channelInfo == null) {
            Logger.error("channelInfo is null");
            return;
        }

        setCurrentChannel(channelInfo.getId());
        LoaderLS.getInstance().getEmoteManager().requestChannelEmoteSet(channelInfo.getId(), false);
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

    public static void showSnackbar(View view, final String message, final String url) {
        if (view == null) {
            Logger.error("view is null");
            return;
        }

        if (TextUtils.isEmpty(message)) {
            Logger.warning("Empty message");
        }

        if (url != null && TextUtils.isEmpty(message)) {
            Logger.warning("Empty url");
        }
        
        Snackbar snack = v1.a(Snackbar.make(view, message, Snackbar.LENGTH_LONG));
        if (snack == null) {
            Logger.error("snack is null");
            return;
        }
        if (url != null) {
            snack.setAction("Open", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    LoaderLS.getInstance().startActivity(intent);
                }
            });
        }
        snack.show();
    }

    // TODO: __REPLACE_INIT_RES
    public static h getUrlDrawableObject(h org) {
        if (org == null)
            return null;

        if (LoaderLS.getInstance().getPrefManager().isDisableGifs()) {
            return new SimpleUrlDrawable(org);
        }

        return org;
    }

    public static boolean isDisableRecentSearch() {
        return LoaderLS.getInstance().getPrefManager().isDisableRecentSearch();
    }

    public static boolean isDisableAutoplay() {
        return LoaderLS.getInstance().getPrefManager().isDisableAutoplay();
    }

    public static int hookUsernameSpanColor(int color) {
        if (!LoaderLS.getInstance().getPrefManager().isFixBrightness())
            return color;

        if (!LoaderLS.getInstance().getPrefManager().isDarkTheme())
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

    private static float getPlayerSpeed() {
        final String speed = LoaderLS.getInstance().getPrefManager().getExoplayerSpeed();
        if (TextUtils.isEmpty(speed)) {
            Logger.warning("Empty speed");
            return 1.0f;
        }
        switch (speed) {
            case "0":
                return 1.0f;
            case "1":
                return 1.25f;
            case "2":
                return 1.5f;
            case "3":
                return 1.75f;
            case "4":
                return 2.0f;
            default:
                Logger.warning("speed = " + speed);
                return 1.0f;
        }
    }

    // TODO: find better way to change playback speed
    public static PlaybackParameters hookStandaloneMediaClockInit() {
        if (LoaderLS.getInstance().getPrefManager().getExoplayerSpeed().equals("0"))
            return PlaybackParameters.e;

        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : stackTraceElements) {
            if (element == null)
                continue;
            if ((!TextUtils.isEmpty(element.getClassName()) && element.getClassName().equals("tv.twitch.a.k.q.j0.v") || (!TextUtils.isEmpty(element.getFileName()) && element.getFileName().equals("VodPlayerPresenter.kt"))))
                return new PlaybackParameters(getPlayerSpeed());
        }

        return PlaybackParameters.e;
    }

    public static void hook_helper() {
        Object o = hookStandaloneMediaClockInit();  // TODO: __HOOK
        o = getUrlDrawableObject(null);  // TODO: __HOOK
    }
}
