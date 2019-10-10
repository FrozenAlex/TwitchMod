package tv.twitch.android.mod.models;

public interface UserInfoCallback {
    void userInfo(String userName, int userId);

    void fail(int userId);
}
