package tv.twitch.android.mod.utils;

import android.text.TextUtils;
import android.util.Log;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import tv.twitch.android.mod.models.UserInfoCallback;
import tv.twitch.android.mod.bridges.ApiCallback;
import tv.twitch.android.mod.net.ServiceFactory;
import tv.twitch.android.mod.models.api.TwitchResponse;
import tv.twitch.android.mod.models.api.TwitchUser;

public class TwitchUsers {
    private static final String LOG_TAG = TwitchUsers.class.getName();

    private final ConcurrentHashMap<Integer, String> cache = new ConcurrentHashMap<>();

    private TwitchUsers() {
    }

    private static class Holder {
        static final TwitchUsers mInstance = new TwitchUsers();
    }

    public static TwitchUsers getInstance() {
        return Holder.mInstance;
    }

    private final class UserInfo extends ApiCallback<TwitchResponse<TwitchUser>> {
        private final int mChannelId;
        private final UserInfoCallback mCallback;

        public UserInfo(int channelId, UserInfoCallback callback) {
            this.mChannelId = channelId;
            this.mCallback = callback;
        }

        @Override
        public void onRequestSuccess(TwitchResponse<TwitchUser> twitchResponse) {
            List<TwitchUser> list = twitchResponse.getData();
            if (list == null || list.isEmpty()) {
                this.mCallback.fail(mChannelId);
                return;
            }
            TwitchUser twitchUser = list.get(0);
            if (twitchUser == null) {
                this.mCallback.fail(mChannelId);
                return;
            }
            if (TextUtils.isEmpty(twitchUser.getLogin())) {
                this.mCallback.fail(mChannelId);
                return;
            }
            cache.put(twitchUser.getId(), twitchUser.getLogin());
            this.mCallback.userInfo(twitchUser.getLogin(), twitchUser.getId());
        }

        @Override
        public void onRequestFail(FailReason reason) {
            Log.d(LOG_TAG, String.format("Request for %d fail: %s", mChannelId, reason.name()));
        }

        public void fetch() {
            ServiceFactory.getTwitchApi().getUsersInfo(mChannelId).enqueue(this);
        }

    }

    private void request(int channelId, UserInfoCallback callback) {
        Log.i(LOG_TAG, "New API request for id: " + channelId);
        UserInfo userInfo = new UserInfo(channelId, callback);
        userInfo.fetch();
    }

    public void getUserName(int id, UserInfoCallback callback) {
        if (!cache.containsKey(id)) {
            request(id, callback);
            return;
        }

        callback.userInfo(cache.get(id), id);
    }
}
