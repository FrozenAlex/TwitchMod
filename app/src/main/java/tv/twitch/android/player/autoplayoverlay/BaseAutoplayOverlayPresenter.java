package tv.twitch.android.player.autoplayoverlay;

import tv.twitch.android.models.Playable;

import static tv.twitch.android.mod.utils.Helper.isDisableAutoplay;

public class BaseAutoplayOverlayPresenter<T extends Playable> {
    public final void presentAutoplayRecommendation(AutoplayOverlayActionListener<T> autoplayOverlayActionListener) {
        if (!isDisableAutoplay()) { // TODO: __ADD

        }
        showFrameAndMaybeOverlays();
    }

    public final void showFrameAndMaybeOverlays() {
    }
}
