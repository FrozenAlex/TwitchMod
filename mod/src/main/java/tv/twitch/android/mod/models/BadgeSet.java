package tv.twitch.android.mod.models;

import java.util.List;

public interface BadgeSet {
    void addBadge(String userName, Badge badge);

    List<Badge> getBadges(String userName);

    boolean isEmpty();
}
