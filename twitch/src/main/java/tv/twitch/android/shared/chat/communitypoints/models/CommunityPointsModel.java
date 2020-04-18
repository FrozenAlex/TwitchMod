package tv.twitch.android.shared.chat.communitypoints.models;

import tv.twitch.android.models.communitypoints.ActiveClaimModel;

public class CommunityPointsModel {
    private ActiveClaimModel claim;

    public final ActiveClaimModel getClaim() {
        return this.claim;
    }
}
