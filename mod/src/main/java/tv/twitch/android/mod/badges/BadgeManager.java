package tv.twitch.android.mod.badges;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import tv.twitch.android.mod.models.Badge;
import tv.twitch.android.mod.utils.Logger;

public class BadgeManager {
    private static FfzBadgeSet mBadgeSet = new FfzBadgeSet();

    private static volatile BadgeManager sInstance;

    private BadgeManager() {
        fetchBadges();
    }

    public static BadgeManager getInstance() {
        if (sInstance == null) {
            synchronized(BadgeManager.class) {
                if (sInstance == null)
                    sInstance = new BadgeManager();
            }
        }
        return sInstance;
    }

    private void fetchBadges() {
        Logger.info("Fetching badges...");
        mBadgeSet.fetch();
    }

    public List<Badge> getBadges(String userName) {
        if (TextUtils.isEmpty(userName)) {
            Logger.warning("Empty userName");
            return new ArrayList<>();
        }

        if (mBadgeSet.isEmpty()) {
            Logger.warning("Empty badgeSet");
            fetchBadges();
            return new ArrayList<>();
        }

        return mBadgeSet.getBadges(userName);
    }
}
