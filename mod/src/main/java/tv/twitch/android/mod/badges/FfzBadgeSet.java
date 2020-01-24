package tv.twitch.android.mod.badges;

import android.text.TextUtils;
import android.util.SparseArray;


import java.util.HashMap;
import java.util.List;

import tv.twitch.android.mod.bridges.ApiCallback;
import tv.twitch.android.mod.models.Badge;
import tv.twitch.android.mod.models.api.FfzBadge;
import tv.twitch.android.mod.models.api.FfzBadges;
import tv.twitch.android.mod.utils.Logger;

import static tv.twitch.android.mod.net.ServiceFactory.getFfzApi;

public class FfzBadgeSet extends BaseBadgeSet<FfzBadges> {
    @Override
    public void onRequestSuccess(FfzBadges response) {
        if (response == null)  {
            Logger.error("response is null");
            return;
        }

        List<FfzBadge> responseBadges = response.getBadges();
        if (responseBadges == null)  {
            Logger.error("responseBadges is null");
            return;
        }

        SparseArray<Badge> badges = new SparseArray<>();
        for (FfzBadge ffzBadge : responseBadges) {
            HashMap<Integer, String> urls = ffzBadge.getUrls();
            if (urls == null)
                continue;

            String url;
            if (urls.containsKey(4))
                url = urls.get(4);
            else if (urls.containsKey(2))
                url = urls.get(2);
            else if (urls.containsKey(1))
                url = urls.get(1);
            else
                continue;

            if (url == null)
                continue;

            if (url.startsWith("//"))
                url = "https:" + url;
            String badgeName = ffzBadge.getName();
            int id = ffzBadge.getId();

            Badge badge = new Badge(url, badgeName, id);
            badges.put(id, badge);
        }

        HashMap<Integer, List<String>> users = response.getUsers();

        if (users == null) {
            Logger.error("users is null");
            return;
        }

        for (Integer badgeId : users.keySet()) {
            Badge badge = badges.get(badgeId);
            if (badge == null)
                continue;

            List<String> usersList = users.get(badgeId);
            if (usersList == null)
                continue;

            for (String userName : usersList) {
                if (TextUtils.isEmpty(userName))
                    continue;

                addBadge(userName, badge);
            }
        }

        Logger.debug("done!");
    }

    @Override
    public void fetch() {
        getFfzApi().getBadges().enqueue(this);
    }

    @Override
    public void onRequestFail(ApiCallback.FailReason reason) {
        Logger.error(reason.name());
    }
}
