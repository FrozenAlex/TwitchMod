package tv.twitch.android.app.core.j2;

import java.util.ArrayList;
import java.util.List;

import tv.twitch.android.mod.bridges.Hooks;
import tv.twitch.android.mod.utils.Logger;


/**
 * Source: BottomNavigationPresenter
 */
public class b {
    private final List<a> b;


    private enum a {
        e,  // ic_navigation_following_selector
        f,  // ic_navigation_discover_selector
        g,  // ic_navigation_browse_selector
        h,  // ic_navigation_esports_selector
        i   // ic_navigation_broadcast
    }

    public b() {
        this.b = filterList(null);
    }

    private static List<a> filterList(ArrayList list) { // TODO: __INJECT_METHOD
        if (list == null) {
            Logger.debug("list is null");
            return null;
        }

        if (Hooks.isHideDiscoverTab()) {
            list.remove(a.f);
        }
        if (Hooks.isHideEsportsTab()) {
            list.remove(a.h);
        }

        return list;
    }
}
