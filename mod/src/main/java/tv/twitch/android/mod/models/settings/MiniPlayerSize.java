package tv.twitch.android.mod.models.settings;

import tv.twitch.android.mod.models.PreferenceItem;
import tv.twitch.android.mod.settings.PrefManager;

public enum MiniPlayerSize implements PreferenceItem {
    DEFAULT("Default", "1.0"),
    SIZE1("1.25", "1.25"),
    SIZE2("1.35", "1.35"),
    SIZE3("1.5", "1.5"),
    SIZE4("1.65", "1.65"),
    SIZE5("1.75", "1.75"),
    SIZE6("1.85", "1.85"),
    SIZE7("2.0", "2.0");

    private static final String PREFERENCE_KEY = PrefManager.PREF_KEY_MINIPLAYER_SIZE;

    public final String name;
    public final String value;


    MiniPlayerSize(String name, String preferenceKey) {
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
