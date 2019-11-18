package tv.twitch.android.models.communitypoints;

public final class ActiveClaimModel {
    private int basePoints;
    private int pointsEarned;
    private String id;


    public final int getBasePoints() {
        return this.basePoints;
    }

    public final int getPointsEarned() {
        return this.pointsEarned;
    }

    public final String getId() {
        return this.id;
    }
}
