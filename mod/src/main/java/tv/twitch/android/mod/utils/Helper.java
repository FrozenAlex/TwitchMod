package tv.twitch.android.mod.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;


import com.google.android.material.snackbar.Snackbar;

import java.util.Collections;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import tv.twitch.a.l.v.b.q.h;
import tv.twitch.android.api.f1.f1;
import tv.twitch.android.app.core.x1;
import tv.twitch.android.mod.activities.Settings;
import tv.twitch.android.mod.bridges.LoaderLS;
import tv.twitch.android.mod.bridges.SimpleUrlDrawable;
import tv.twitch.android.models.Playable;
import tv.twitch.android.models.channel.ChannelInfo;
import tv.twitch.android.models.clips.ClipModel;
import tv.twitch.android.models.communitypoints.ActiveClaimModel;
import tv.twitch.android.shared.chat.communitypoints.models.CommunityPointsModel;

public class Helper {
    private static final Set<String> mReceivedClaims = Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());

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

    public static void clicker(final View pointButtonView, CommunityPointsModel communityPointsModel) {
        if (!LoaderLS.getInstance().getPrefManager().isClickerOn())
            return;

        if (pointButtonView == null) {
            Logger.error("pointButtonView is null");
            return;
        }

        if (communityPointsModel == null) {
            Logger.error("communityPointsModel is null");
            return;
        }

        ActiveClaimModel claimModel = communityPointsModel.getClaim();
        if (claimModel == null) {
            Logger.error("claimModel is null");
            return;
        }
        if (TextUtils.isEmpty(claimModel.getId())) {
            Logger.error("Bad id");
            return;
        }

        synchronized (mReceivedClaims) {
            if (mReceivedClaims.contains(claimModel.getId())) {
                return;
            }
            mReceivedClaims.add(claimModel.getId());
        }

        pointButtonView.performClick();
        Logger.info(String.format(Locale.ENGLISH, "Click! Got %d points", claimModel.getPointsEarned()));
    }


    public void setCurrentChannel(ChannelInfo channelInfo) {
        if (channelInfo == null) {
            Logger.error("channelInfo is null");
            return;
        }
        if (channelInfo.getId() == 0) {
            Logger.error("Bad channelId");
            return;
        }

        setCurrentChannel(channelInfo.getId());
    }

    public void setCurrentChannel(int channelID) {
        if (channelID == 0) {
            Logger.warning("Bad channelID");
        }
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

        if (channelId != 0)
            setCurrentChannel(channelId);
        else {
            Logger.warning("Bad channelID");
        }

        LoaderLS.getInstance().getEmoteManager().requestChannelEmoteSet(channelName, channelId, true);
    }

    public void newRequest(ChannelInfo channelInfo) {
        if (channelInfo == null) {
            Logger.error("channelInfo is null");
            return;
        }
        Logger.debug(String.format("Chat connection controller request: %s", channelInfo.getName()));

        setCurrentChannel(channelInfo);
        LoaderLS.getInstance().getEmoteManager().requestChannelEmoteSet(channelInfo, false);
    }

    public static void openSettings(Activity fragmentActivity) {
        if (fragmentActivity == null) {
            Logger.error("fragmentActivity is null");
            return;
        }

        Context context = fragmentActivity.getApplicationContext();
        if (context == null) {
            Logger.error("context is null");
            return;
        }

        startActivity(context, Settings.class);
    }

    public static void startActivity(Context context, Class activity) {
        if (context == null) {
            Logger.error("context is null");
            return;
        }
        if (activity == null) {
            Logger.error("activity is null");
            return;
        }

        Intent intent = new Intent(context, activity);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void showToast(final Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
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
        if (org == null)
            return org;

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
}
