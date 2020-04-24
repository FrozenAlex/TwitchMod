package tv.twitch.android.shared.chat.communitypoints;

import android.view.ViewGroup;

import tv.twitch.android.mod.bridges.LoaderLS;
import tv.twitch.android.shared.chat.communitypoints.models.CommunityPointsModel;


// Source: CommunityPointsButtonViewDelegate
public class t {
    private final ViewGroup b = null;

    private final void e(CommunityPointsModel communityPointsModel) {

        LoaderLS.getInstance().getHelper().clicker(b, communityPointsModel); // TODO: __ADD_END
    }
}