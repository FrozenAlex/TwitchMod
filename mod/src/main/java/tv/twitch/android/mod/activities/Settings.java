package tv.twitch.android.mod.activities;


import android.os.Bundle;
import android.preference.PreferenceFragment;

import tv.twitch.android.core.activities.BaseActivity;

import static tv.twitch.android.mod.bridges.LoaderLS.PREFERENCES_ID;


public class Settings extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }

    public static class SettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(PREFERENCES_ID);
        }
    }
}
