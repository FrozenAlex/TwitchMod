package tv.twitch.android.mod.badges;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;


import tv.twitch.android.mod.models.Badge;
import tv.twitch.android.mod.utils.Logger;

public class BadgeManager {
    private FfzBadgeSet mBadgeSet;

    private BadgeManager() {
        fetchBadges();
    }

    private static class Holder {
        static final BadgeManager mInstance = new BadgeManager();
    }

    public static BadgeManager getInstance() {
        return BadgeManager.Holder.mInstance;
    }

    private void fetchBadges() {
        Logger.info("Fetching badges...");
        if (mBadgeSet == null) {
            mBadgeSet = new FfzBadgeSet();
            mBadgeSet.fetch();
        }
    }

    public List<Badge> getBadges(String userName) {
        if (TextUtils.isEmpty(userName)) {
            Logger.error("Empty userName");
            return new ArrayList<>();
        }

        if (mBadgeSet != null)
            return mBadgeSet.getBadges(userName);

        return new ArrayList<>();
    }
}
