package tv.twitch.android.mod.models.settings;

import tv.twitch.android.mod.models.PreferenceItem;
import tv.twitch.android.mod.settings.PrefManager;

public enum UserMessagesFiltering implements PreferenceItem {
    DISABLED("Disabled", "DISABLED"),
    PLEBS("Plebs", "PLEBS"),
    SUBS("Subs", "SUBS"),
    MODS("Mods", "MODS"),
    BROADCASTER("Broadcaster", "BROADCASTER");

    private static final String PREFERENCE_KEY = PrefManager.PREF_KEY_USER_MESSAGES_FILTERING;

    public final String name;
    public final String value;


    UserMessagesFiltering(String name, String preferenceKey) {
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
