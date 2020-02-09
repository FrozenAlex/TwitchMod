package tv.twitch.android.mod.activities;


import android.os.Bundle;
import android.preference.PreferenceFragment;

import androidx.appcompat.app.AppCompatActivity;

import tv.twitch.android.mod.utils.Helper;


public class Settings extends AppCompatActivity {
    public static int XML_ID = 0x7f160001;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();

        Helper.showSnackbar(this.findViewById(android.R.id.content), "github.com/nopbreak/TwitchMod", "https://github.com/nopbreak/TwitchMod");
    }

    public static class SettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(XML_ID);
        }
    }
}
