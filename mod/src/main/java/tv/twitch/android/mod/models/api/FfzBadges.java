package tv.twitch.android.mod.models.api;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

public class FfzBadges {
    @SerializedName("badges")
    private List<FfzBadge> badges;
    @SerializedName("users")
    private HashMap<Integer, List<String>> users;

    public HashMap<Integer, List<String>> getUsers() {
        return users;
    }

    public List<FfzBadge> getBadges() {
        return badges;
    }

    public void setBadges(List<FfzBadge> badges) {
        this.badges = badges;
    }

    public void setUsers(HashMap<Integer, List<String>> users) {
        this.users = users;
    }
}
