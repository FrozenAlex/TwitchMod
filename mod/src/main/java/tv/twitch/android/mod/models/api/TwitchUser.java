package tv.twitch.android.mod.models.api;

import com.google.gson.annotations.SerializedName;

public class TwitchUser {
    @SerializedName("id")
    private long id;
    @SerializedName("login")
    private String login;
    @SerializedName("display_name")
    private String displayName;
    @SerializedName("type")
    private Object type;
    @SerializedName("broadcaster_type")
    private String broadcasterType;
    @SerializedName("description")
    private String description;
    @SerializedName("profile_image_url")
    private String profileImageUrl;
    @SerializedName("offline_image_url")
    private String offlineImageUrl;
    @SerializedName("view_count")
    private long viewCount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getViewCount() {
        return viewCount;
    }

    public void setViewCount(long viewCount) {
        this.viewCount = viewCount;
    }

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }

    public String getBroadcasterType() {
        return broadcasterType;
    }

    public void setBroadcasterType(String broadcasterType) {
        this.broadcasterType = broadcasterType;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getOfflineImageUrl() {
        return offlineImageUrl;
    }

    public void setOfflineImageUrl(String offlineImageUrl) {
        this.offlineImageUrl = offlineImageUrl;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
