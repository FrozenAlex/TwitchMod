package tv.twitch.android.settings.l;


import androidx.fragment.app.FragmentActivity;

import java.util.List;

import tv.twitch.android.shared.ui.menus.j;

/**
 * Source: BaseSettingsPresenter
 */
public abstract class b {


    /**
     * Destinations
     */
    protected abstract d U1();

    /**
     * Title
     */
    public abstract String X1();


    /**
     * Controller
     */
    protected abstract j V1();

    public final FragmentActivity R1() {
        return null;
    }

    public abstract void c2();

    /**
     * Menu items
     */
    public final List<tv.twitch.android.shared.ui.menus.l.b> W1() {
        return null;
    }
}
