package tv.twitch.android.mod.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import tv.twitch.android.mod.activities.Settings;
import tv.twitch.android.mod.emotes.EmotesManager;
import tv.twitch.android.models.channel.ChannelInfo;

public class Helper {
    private int currentChannel = 0;

    private Helper() {
    }

    private static class Holder {
        static final Helper mInstance = new Helper();
    }

    public static Helper getInstance() {
        return Holder.mInstance;
    }

    public void setCurrentChannel(ChannelInfo channelInfo) {
        this.currentChannel = channelInfo.getId();
    }

    public int getCurrentChannel() {
        return this.currentChannel;
    }

    public static void newRequest(ChannelInfo channelInfo) {
        if (channelInfo == null) {
            Logger.error("channelInfo is null");
            return;
        }
        Logger.debug(String.format("New request for %s...", channelInfo.getName()));
        EmotesManager.getInstance().request(channelInfo);
        Helper.getInstance().setCurrentChannel(channelInfo);
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
        if (context == null || activity == null) {
            Logger.warning("null args");
            return;
        }
        Intent intent = new Intent(context, activity);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void showToast(final Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
