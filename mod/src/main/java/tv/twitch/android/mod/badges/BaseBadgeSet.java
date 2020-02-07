package tv.twitch.android.mod.badges;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import tv.twitch.android.mod.bridges.ApiCallback;
import tv.twitch.android.mod.models.Badge;
import tv.twitch.android.mod.models.BadgeSet;
import tv.twitch.android.mod.utils.Logger;


public abstract class BaseBadgeSet<T> extends ApiCallback<T> implements BadgeSet {
    private final Map<String, List<Badge>> mBadges = Collections.synchronizedMap(new LinkedHashMap<String, List<Badge>>());

    public BaseBadgeSet() {
    }

    @Override
    public void addBadge(String userName, Badge badge) {
        if (badge == null) {
            Logger.error("badge is null");
            return;
        }
        if (TextUtils.isEmpty(userName)) {
            Logger.error("Empty userName");
            return;
        }

        synchronized (BaseBadgeSet.class) {
            if (mBadges.containsKey(userName)) {
                List<Badge> badges = mBadges.get(userName);
                if (badges == null) {
                    badges = new ArrayList<>();
                    badges.add(badge);
                    mBadges.put(userName, badges);
                } else {
                    badges.add(badge);
                }
            } else {
                List<Badge> badges = new ArrayList<>();
                badges.add(badge);
                mBadges.put(userName, badges);
            }
        }
    }

    @Override
    public List<Badge> getBadges(String userName) {
        return mBadges.get(userName);
    }

    @Override
    public boolean isEmpty() {
        return mBadges.isEmpty();
    }
}
