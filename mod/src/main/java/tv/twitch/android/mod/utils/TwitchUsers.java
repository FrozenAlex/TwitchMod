package tv.twitch.android.mod.utils;

import android.text.TextUtils;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import tv.twitch.android.mod.models.UserInfoCallback;
import tv.twitch.android.mod.bridges.ApiCallback;
import tv.twitch.android.mod.net.ServiceFactory;
import tv.twitch.android.mod.models.api.TwitchResponse;
import tv.twitch.android.mod.models.api.TwitchUser;

public class TwitchUsers {
    private static final ConcurrentHashMap<Integer, String> mCache = new ConcurrentHashMap<>();

    private final Set<Integer> mRequests = Collections.newSetFromMap(new ConcurrentHashMap<Integer, Boolean>());

    private static volatile TwitchUsers sInstance;

    private TwitchUsers() {
    }

    public static TwitchUsers getInstance() {
        if (sInstance == null) {
            synchronized(TwitchUsers.class) {
                if (sInstance == null)
                    sInstance = new TwitchUsers();
            }
        }
        return sInstance;
    }

    public synchronized void putData(int channelID, String channelName) {
        if (channelID <= 0) {
            Logger.error("Bad channelID");
            return;
        }
        if (TextUtils.isEmpty(channelName)) {
            Logger.error("Empty channelName");
            return;
        }

        mCache.put(channelID, channelName);
    }

    private final class UserInfo extends ApiCallback<TwitchResponse<TwitchUser>> {
        private final int mChannelId;
        private final UserInfoCallback mCallback;

        UserInfo(int channelId, UserInfoCallback callback) {
            this.mChannelId = channelId;
            this.mCallback = callback;
        }

        @Override
        public void onRequestSuccess(TwitchResponse<TwitchUser> twitchResponse) {
            List<TwitchUser> list = twitchResponse.getData();
            if (list == null || list.isEmpty()) {
                mCallback.fail(mChannelId);
                return;
            }
            TwitchUser twitchUser = list.get(0);
            if (twitchUser == null) {
                mCallback.fail(mChannelId);
                return;
            }
            if (TextUtils.isEmpty(twitchUser.getLogin())) {
                mCallback.fail(mChannelId);
                return;
            }
            if (twitchUser.getId() == 0) {
                mCallback.fail(mChannelId);
                return;
            }
            putData(mChannelId, twitchUser.getLogin());
            mRequests.remove(mChannelId);
            mCallback.userInfo(twitchUser.getLogin(), twitchUser.getId());
        }

        @Override
        public void onRequestFail(FailReason reason) {
            Logger.debug(String.format(Locale.ENGLISH, "Request for %d fail: %s", mChannelId, reason.name()));
        }

        public void fetch() {
            ServiceFactory.getTwitchApi().getUsersInfo(mChannelId).enqueue(this);
        }
    }

    private void request(int channelId, UserInfoCallback callback) {
        UserInfo userInfo = new UserInfo(channelId, callback);
        userInfo.fetch();
    }

    public void getUserName(int id, UserInfoCallback callback) {
        if (mCache.containsKey(id)) {
            callback.userInfo(mCache.get(id), id);
            return;
        }

        if (!mRequests.contains(id)) {
            synchronized (mRequests) {
                if (!mRequests.contains(id)) {
                    mRequests.add(id);
                    request(id, callback);
                }
            }
        }
    }
}
