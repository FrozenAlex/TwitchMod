package tv.twitch.android.mod.models.settings;

import androidx.annotation.NonNull;

import tv.twitch.android.mod.models.PreferenceItem;
import tv.twitch.android.mod.settings.PrefManager;

public enum  Gifs implements PreferenceItem {
    DISABLED("Disabled", "DISABLED"),
    STATIC("Enabled (Static)", "STATIC"),
    ANIMATED("Enabled (Animated)", "ANIMATED");

    private static final String PREFERENCE_KEY = PrefManager.PREF_KEY_GIFS;

    public final String name;
    public final String value;


    Gifs(String name, String preferenceKey) {
        this.name = name;
        this.value = preferenceKey;
    }

    @NonNull
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
