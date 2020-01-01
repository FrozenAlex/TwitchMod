package tv.twitch.android.app.run;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import tv.twitch.android.app.R;
import tv.twitch.android.mod.activities.Settings;
import tv.twitch.android.mod.emotes.EmotesManager;
import tv.twitch.android.mod.settings.PrefManager;
import tv.twitch.android.mod.utils.Helper;
import tv.twitch.android.mod.utils.Loader;
import tv.twitch.android.models.channel.ChannelInfo;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Loader.init(this); // TODO: __ADD_ONCREATE
        ChannelInfo channelInfo = new ChannelInfo() {
            @Override
            public String getDisplayName() {
                return null;
            }

            @Override
            public String getGame() {
                return null;
            }

            @Override
            public int getId() {
                return 22484632;
            }

            @Override
            public String getName() {
                return null;
            }

            @Override
            public boolean isPartner() {
                return false;
            }

            @Override
            public boolean isRecommendation() {
                return false;
            }

            @Override
            public void setRecommendation(boolean z) {

            }
        };
        Helper.getInstance().newRequest(channelInfo);
        final String[] emotes = {"PedoBear", "pajaDank", "forsenShuffle", "LULW"};
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
                    Log.i("settings", "gifs=" + PrefManager.isDisableGifs());
                    Log.i("emote", String.valueOf(EmotesManager.getInstance().getEmote(emotes[i++%emotes.length], 22484632)));
                }
            }
        });
        th.setDaemon(true);
        th.start();

        Settings.XML_ID = R.xml.mod_settings;
        startActivity(new Intent(getApplicationContext(), Settings.class));
    }
}
