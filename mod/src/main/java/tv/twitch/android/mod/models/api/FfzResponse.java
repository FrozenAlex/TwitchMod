package tv.twitch.android.mod.models.api;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;


public class FfzResponse {
    @SerializedName("room")
    private FfzRoom room;
    @SerializedName("sets")
    private HashMap<Integer, FfzSet> sets;

    public HashMap<Integer, FfzSet> getSets() {
        return sets;
    }

    public void setSets(HashMap<Integer, FfzSet> sets) {
        this.sets = sets;
    }

    public FfzRoom getRoom() {
        return room;
    }

    public void setRoom(FfzRoom room) {
        this.room = room;
    }
}
