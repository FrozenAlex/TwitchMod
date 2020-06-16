package tv.twitch.android.settings.z;


import tv.twitch.android.mod.settings.SettingsController;
import tv.twitch.android.shared.ui.menus.j;
import tv.twitch.android.shared.ui.menus.s.b;


/**
 * Source: SystemSettingsPresenter
 */
public class d extends tv.twitch.android.settings.l.b {
    public static final class ToggleMenuChangeListener implements j { // TODO: __INJECT_CLASS
        @Override
        public void a(tv.twitch.android.shared.ui.menus.k.b bVar) {}

        @Override
        public void a(b item, boolean isChecked) {
            SettingsController.OnToggleEvent(item, isChecked);
        }
    }


    @Override
    protected tv.twitch.android.settings.l.d U1() {
        return null;
    }

    @Override
    public String X1() {
        return null;
    }

    @Override
    protected j V1() { // TODO: __REPLACE_METHOD
        return new ToggleMenuChangeListener();
    }

    @Override
    public void c2() { // TODO: __REPLACE_METHOD
        SettingsController.initialize(R1(), W1());
    }
}
