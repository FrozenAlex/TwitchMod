package tv.twitch.android.player.autoplayoverlay;

import android.os.Bundle;

public interface AutoplayOverlayActionListener<T> {
    void onAutoplayRecommendationCancelled();

    void onAutoplayRecommendationTriggered(T t, Bundle bundle);

    void onReplayClicked();
}