package tv.twitch.android.player.autoplayoverlay;

import tv.twitch.android.mod.settings.PrefManager;
import tv.twitch.android.models.Playable;

public class BaseAutoplayOverlayPresenter<T extends Playable> {
    public final void presentAutoplayRecommendation(AutoplayOverlayActionListener<T> autoplayOverlayActionListener) {
        if (!PrefManager.isDisableAutoplay()) { // TODO: __ADD

        }
        showFrameAndMaybeOverlays();
    }

    public final void showFrameAndMaybeOverlays() {
    }
}
