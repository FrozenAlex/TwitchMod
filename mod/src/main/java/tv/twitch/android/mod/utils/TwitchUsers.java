package tv.twitch.android.mod.utils;

import android.text.TextUtils;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import retrofit2.Call;
import tv.twitch.android.mod.models.UserInfoCallback;
import tv.twitch.android.mod.bridges.ApiCallback;
import tv.twitch.android.mod.net.ServiceFactory;
import tv.twitch.android.mod.models.api.TwitchResponse;
import tv.twitch.android.mod.models.api.TwitchUser;

public class TwitchUsers {
    private static final String LOG_TAG = TwitchUsers.class.getName();

    private final ConcurrentHashMap<Long, String> mIds = new ConcurrentHashMap<>();

    private TwitchUsers() {
    }

    private static class Holder {
        static final TwitchUsers instance = new TwitchUsers();
    }

    public static TwitchUsers getInstance() {
        return Holder.instance;
    }

    private final class IdCallback extends ApiCallback<TwitchResponse<TwitchUser>> {
        private final long mChannelId;
        private WeakReference<UserInfoCallback> mCallback = null;

        public IdCallback(long channelId, UserInfoCallback callback) {
            this.mChannelId = channelId;
            if (callback != null)
                this.mCallback = new WeakReference<>(callback);
        }

        private void proc(TwitchUser twitchUser) {
            if (TextUtils.isEmpty(twitchUser.getLogin())) {
                Log.e(LOG_TAG, "Empty login");
                return;
            }
            mIds.put(twitchUser.getId(), twitchUser.getLogin());
            callback(twitchUser.getLogin(), twitchUser.getId());
        }

        private void callback(String userName, long userId) {
            if (this.mCallback != null) {
                UserInfoCallback callback = this.mCallback.get();
                if (callback != null) {
                    callback.userInfo(userName, userId);
                }
            }
        }

        @Override
        public void onRequestSuccess(TwitchResponse<TwitchUser> twitchResponse) {
            try {
                if (twitchResponse == null)
                    throw new Exception("Body is null");
                List<TwitchUser> list = twitchResponse.getData();
                if (list == null || list.isEmpty())
                    throw new Exception("Null userInfo");
                TwitchUser twitchUser = list.get(0);
                if (twitchUser == null)
                    throw new Exception("Null user");

                this.proc(twitchUser);
            } catch (Exception e) {
                e.printStackTrace();
                callback(null, 0);
            }
        }

        @Override
        public void onRequestFail(FailReason reason) {
        }

        public final void fetch() {
            Call<TwitchResponse<TwitchUser>> call = ServiceFactory.getTwitchApi().getUsersInfo(mChannelId);
            call.enqueue(this);
        }

    }

    public void request(long channelId) {
        request(channelId, null);
    }

    private void request(long channelId, UserInfoCallback callback) {
        Log.i(LOG_TAG, "New API request for id: " + channelId);
        IdCallback idCallback = new IdCallback(channelId, callback);
        idCallback.fetch();
    }

    public void requestUserName(long id, UserInfoCallback callback) {
        if (!mIds.containsKey(id)) {
            request(id, callback);
            return;
        }

        callback.userInfo(mIds.get(id), id);
    }
}
