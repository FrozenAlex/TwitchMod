package tv.twitch.android.settings.z;


import tv.twitch.android.mod.settings.SettingsController;
import tv.twitch.android.mod.utils.Logger;
import tv.twitch.android.shared.ui.menus.k;
import tv.twitch.android.shared.ui.menus.o.b;


/**
 * Source: SystemSettingsPresenter
 */
public class d extends tv.twitch.android.settings.l.b {
    public static final class ToggleMenuChangeListener implements k { // TODO: __INJECT_CLASS
        final d presenter;

        public ToggleMenuChangeListener(d systemSettingsPresenter) {
            presenter = systemSettingsPresenter;
        }

        @Override
        public void a(b bVar) {}

        @Override
        public void a(tv.twitch.android.shared.ui.menus.w.b item, boolean isChecked) {
            Logger.debug("clicked");
            SettingsController.onToggleEvent(item, isChecked);
        }
    }


    @Override
    protected tv.twitch.android.settings.l.d o0() {
        return null;
    }

    @Override
    public String r0() {
        return null;
    }

    @Override
    protected k p0() { // TODO: __REPLACE_METHOD
        return new ToggleMenuChangeListener(this);
    }

    @Override
    public void w0() { // TODO: __REPLACE_METHOD
        SettingsController.initialize(l0(), q0());
    }
}
