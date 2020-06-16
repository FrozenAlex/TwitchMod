package tv.twitch.android.shared.chat.communitypoints;

import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Random;

import tv.twitch.android.mod.bridges.Hooks;
import tv.twitch.android.mod.utils.Clicker;
import tv.twitch.android.mod.utils.Helper;
import tv.twitch.android.shared.chat.communitypoints.models.CommunityPointsModel;


/**
 * Source: CommunityPointsButtonViewDelegate
 */
public class t {
    private final ViewGroup b = null;


    private final void E(CommunityPointsModel communityPointsModel) { // TODO: __REPLACE_METHOD
        Hooks.setClicker(new f(this, communityPointsModel), communityPointsModel);
    }

    static final class f implements View.OnClickListener {
        f(t tVar, CommunityPointsModel communityPointsModel) {}

        public final void onClick(View view) {}
    }

    private void z() {
        // TODO: __REMOVE_TOAST
    }
}