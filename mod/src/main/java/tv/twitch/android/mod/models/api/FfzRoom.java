package tv.twitch.android.mod.models.api;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class FfzRoom {
    @SerializedName("_id")
    private long roomId;
    @SerializedName("css")
    private String css;
    @SerializedName("display_name")
    private String displayName;
    @SerializedName("id")
    private String id;
    @SerializedName("is_group")
    private boolean isGroup;
    @SerializedName("mod_urls")
    private HashMap<Integer, String> modUrls;
    @SerializedName("moderator_badge")
    private String moderatorBadge;
    @SerializedName("set")
    private int set;
    @SerializedName("twitch_id")
    private long twitchId;
    @SerializedName("user_badges")
    private Object userBadges;

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long id) {
        this.roomId = id;
    }

    public String getId() {
        return id;
    }

    public void setd(String id) {
        this.id = id;
    }

    public String getCss() {
        return css;
    }

    public void setCss(String css) {
        this.css = css;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public long getTwitchId() {
        return twitchId;
    }

    public void setTwitchId(long twitchId) {
        this.twitchId = twitchId;
    }

    public int getSet() {
        return set;
    }

    public void setSet(int set) {
        this.set = set;
    }

    public Object getUserBadges() {
        return userBadges;
    }

    public void setUserBadges(Object userBadges) {
        this.userBadges = userBadges;
    }

    public String getModeratorBadge() {
        return moderatorBadge;
    }

    public void setModeratorBadge(String moderatorBadge) {
        this.moderatorBadge = moderatorBadge;
    }

    public HashMap<Integer, String> getModUrls() {
        return modUrls;
    }

    public void setModUrls(HashMap<Integer, String> modUrls) {
        this.modUrls = modUrls;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean group) {
        isGroup = group;
    }
}
