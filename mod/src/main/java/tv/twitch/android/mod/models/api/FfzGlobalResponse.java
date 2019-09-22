package tv.twitch.android.mod.models.api;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

import tv.twitch.android.mod.models.Emote;

public class FfzGlobalResponse {
    @SerializedName("default_sets")
    private List<Integer> defaultSets;
    @SerializedName("sets")
    private HashMap<Integer, FfzSet> sets;
    @SerializedName("users")
    private HashMap<Integer, List<String>> users;

    public List<Integer> getDefaultSets() {
        return defaultSets;
    }

    public void setDefaultSets(List<Integer> defaultSets) {
        this.defaultSets = defaultSets;
    }

    public HashMap<Integer, List<String>> getUsers() {
        return users;
    }

    public void setUsers(HashMap<Integer, List<String>> users) {
        this.users = users;
    }

    public HashMap<Integer, FfzSet> getSets() {
        return sets;
    }

    public void setSets(HashMap<Integer, FfzSet> sets) {
        this.sets = sets;
    }
}
