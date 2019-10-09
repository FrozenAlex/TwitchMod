package tv.twitch.android.mod.models.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FfzUser {
    @SerializedName("avatar")
    private String avatar;
    @SerializedName("badges")
    private List<Integer> badges;
    @SerializedName("display_name")
    private String displayName;
    @SerializedName("emote_sets")
    private List<Integer> emoteSets;
    @SerializedName("id")
    private long id;
    @SerializedName("is_donor")
    private boolean isDonor;
    @SerializedName("name")
    private String name;
    @SerializedName("twitch_id")
    private int twitchId;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<Integer> getBadges() {
        return badges;
    }

    public void setBadges(List<Integer> badges) {
        this.badges = badges;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<Integer> getEmoteSets() {
        return emoteSets;
    }

    public void setEmoteSets(List<Integer> emoteSets) {
        this.emoteSets = emoteSets;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTwitchId() {
        return twitchId;
    }

    public void setTwitchId(int twitchId) {
        this.twitchId = twitchId;
    }

    public boolean isDonor() {
        return isDonor;
    }

    public void setDonor(boolean donor) {
        isDonor = donor;
    }
}
