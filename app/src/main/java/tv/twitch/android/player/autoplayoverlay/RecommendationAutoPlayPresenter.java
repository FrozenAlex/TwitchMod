package tv.twitch.android.player.autoplayoverlay;

import tv.twitch.android.mod.bridges.LoaderLS;
import tv.twitch.android.models.Playable;

public class RecommendationAutoPlayPresenter<T extends Playable> {
    public final void prepareRecommendationForCurrentModel(T t) {
        if (!LoaderLS.getInstance().getPrefManager().isDisableAutoplay()) // TODO: __ADD
            return;
    }
}
