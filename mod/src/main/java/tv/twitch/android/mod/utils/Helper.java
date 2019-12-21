package tv.twitch.android.mod.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.widget.Toast;

import tv.twitch.android.api.i1.f1;
import tv.twitch.android.app.core.f0;
import tv.twitch.android.mod.activities.Settings;
import tv.twitch.android.mod.emotes.EmotesManager;
import tv.twitch.android.models.Playable;
import tv.twitch.android.models.channel.ChannelInfo;
import tv.twitch.android.models.clips.ClipModel;

public class Helper {
    private int currentChannel = 0;

    private static final EmotesManager sEmotesManager = EmotesManager.getInstance();

    private Helper() {
    }

    private static class Holder {
        static final Helper mInstance = new Helper();
    }

    public static Helper getInstance() {
        return Holder.mInstance;
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
        this.currentChannel = channelInfo.getId();
    }

    public void setCurrentChannel(int channelId) {
        this.currentChannel = channelId;
    }

    public int getCurrentChannel() {
        return this.currentChannel;
    }

    public void newRequest(final f1 playableModelParser, final Playable playable) {
        Logger.debug("New playable request...");
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

        sEmotesManager.request(channelName, channelId);
        setCurrentChannel(channelId);
    }

    public void newRequest(ChannelInfo channelInfo) {
        if (channelInfo == null) {
            Logger.error("channelInfo is null");
            return;
        }
        Logger.debug(String.format("New request for %s...", channelInfo.getName()));
        sEmotesManager.request(channelInfo);
        setCurrentChannel(channelInfo);
    }

    public static void openSettings(Activity fragmentActivity) {
        if (fragmentActivity != null) {
            Context context = fragmentActivity.getApplicationContext();
            if (context != null)
                startActivity(context, Settings.class);
            else {
                Logger.error("context is null");
            }
        } else {
            Logger.error("fragmentActivity is null");
        }
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

    public static boolean isLowDensity() {
        Context context = f0.c().a();
        if (context == null) {
            Logger.error("context is null");
            return false;
        }
        Resources resources = context.getResources();
        return resources.getDisplayMetrics().density < 2.0f;
    }
}
