package tv.twitch.android.mod.models.settings;

import tv.twitch.android.mod.models.PreferenceItem;
import tv.twitch.android.mod.settings.PrefManager;

public enum ExoPlayerSpeed implements PreferenceItem {
    DEFAULT("Default", "1.0"),
    SPEED1("1.25", "1.25"),
    SPEED2("1.5", "1.5"),
    SPEED3("1.75", "1.75"),
    SPEED4("2.0", "2.0");

    private static final String PREFERENCE_KEY = PrefManager.PREF_KEY_EXOPLAYER_SPEED;

    public final String name;
    public final String value;


    ExoPlayerSpeed(String name, String preferenceKey) {
        this.name = name;
        this.value = preferenceKey;
    }

    @Override
    public String toString() {
        return getPreferenceName();
    }

    @Override
    public String getPreferenceValue() {
        return value;
    }

    @Override
    public String getPreferenceKey() {
        return PREFERENCE_KEY;
    }

    @Override
    public String getPreferenceName() {
        return name;
    }

    @Override
    public PreferenceItem getPreference(String value) {
        for (PreferenceItem item : values()) {
            if (item.getPreferenceValue().equals(value))
                return item;
        }

        return this;
    }
}
