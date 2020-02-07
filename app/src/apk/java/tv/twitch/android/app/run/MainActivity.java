package tv.twitch.android.app.run;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannedString;
import android.util.Log;

import tv.twitch.android.app.R;
import tv.twitch.android.mod.activities.Settings;
import tv.twitch.android.mod.bridges.ChatMessageFactory;
import tv.twitch.android.mod.emotes.EmoteManager;
import tv.twitch.android.mod.settings.PrefManager;
import tv.twitch.android.mod.utils.Helper;
import tv.twitch.android.mod.utils.LoaderLS;
import tv.twitch.android.mod.utils.Logger;
import tv.twitch.android.models.channel.ChannelInfo;

import static tv.twitch.android.mod.utils.ChatUtils.injectBadges;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Settings.XML_ID = R.xml.mod_settings;
        startActivity(new Intent(getApplicationContext(), Settings.class));
    }
}
