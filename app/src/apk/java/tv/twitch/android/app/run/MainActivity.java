package tv.twitch.android.app.run;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import tv.twitch.android.app.R;
import tv.twitch.android.mod.activities.Settings;
import tv.twitch.android.mod.bridges.LoaderLS;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoaderLS.XML_ID = R.xml.mod_preferences;
        startActivity(new Intent(getApplicationContext(), Settings.class));
    }
}
