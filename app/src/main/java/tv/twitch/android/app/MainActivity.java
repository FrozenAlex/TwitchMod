package tv.twitch.android.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import tv.twitch.android.mod.activities.Settings;
import tv.twitch.android.mod.emotes.EmotesManager;
import tv.twitch.android.mod.settings.PrefManager;
import tv.twitch.android.mod.utils.Helper;
import tv.twitch.android.mod.utils.Loader;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Loader.init(this); // TODO: __ADD_ONCREATE
        Helper.newRequest(22484632);
        final String[] emotes = {"PedoBear", "YooHoo", "forsenShuffle", "LULW"};
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.i("settings", "adblock=" + PrefManager.isAdblockOn() + "; gifs=" + PrefManager.isDisableGifs());
                    Log.i("emote", String.valueOf(EmotesManager.getInstance().getEmote(emotes[i++%emotes.length], 22484632)));
                }
            }
        });
        th.setDaemon(true);
        th.start();
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }
}
