package tv.twitch.android.shared.chat.communitypoints.models;

import tv.twitch.android.models.communitypoints.ActiveClaimModel;

public final class CommunityPointsModel {
    private ActiveClaimModel claim;

    public final ActiveClaimModel getClaim() {
        return this.claim;
    }
}
