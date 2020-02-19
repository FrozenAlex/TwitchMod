package tv.twitch.android.mod.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;


import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;

import tv.twitch.a.l.v.b.q.h;
import tv.twitch.android.api.f1.f1;
import tv.twitch.android.app.core.x1;
import tv.twitch.android.mod.activities.Settings;
import tv.twitch.android.mod.bridges.LoaderLS;
import tv.twitch.android.mod.bridges.SimpleUrlDrawable;
import tv.twitch.android.models.Playable;
import tv.twitch.android.models.channel.ChannelInfo;
import tv.twitch.android.models.clips.ClipModel;

public class Helper {
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
        Logger.info("Click!");
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

        String channelName;
        int channelId;
        if (playable instanceof ClipModel) {
            channelName = ((ClipModel) playable).getBroadcasterName();
            channelId = ((ClipModel) playable).getBroadcasterId();
        } else {
            channelName = playableModelParser.b(playable);
            channelId = playableModelParser.a(playable);
        }

        Logger.debug(String.format("Playable request: %s", channelName));

        setCurrentChannel(channelId);
        LoaderLS.getInstance().getEmoteManager().requestChannelEmoteSet(channelName, channelId, true);
    }

    public void newRequest(ChannelInfo channelInfo) {
        if (channelInfo == null) {
            Logger.error("channelInfo is null");
            return;
        }
        Logger.debug(String.format("Chat connection controller request: %s", channelInfo.getName()));

        setCurrentChannel(channelInfo.getId());
        LoaderLS.getInstance().getEmoteManager().requestChannelEmoteSet(channelInfo, false);
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
        
        Snackbar snack = x1.a(Snackbar.make(view, message, Snackbar.LENGTH_LONG));
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
        // Object test = getUrlDrawableObject(null);

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

    public static int fixUsernameSpanColor(int color) {
        boolean isDarkTheme = LoaderLS.getInstance().getPrefManager().isDarkTheme();

        if (!isDarkTheme)
            return color;

        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);

        if (hsv[2] < .3) {
            Logger.debug(Arrays.toString(hsv));
            hsv[2] = (float) .3;
        } else
            return color;

        return Color.HSVToColor(hsv);
    }
}
