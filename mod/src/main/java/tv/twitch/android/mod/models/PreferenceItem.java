package tv.twitch.android.mod.models;

public interface PreferenceItem {
    String getPreferenceValue();

    String getPreferenceKey();

    String getPreferenceName();

    PreferenceItem getPreference(String name);
}
