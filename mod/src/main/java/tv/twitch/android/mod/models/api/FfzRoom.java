package tv.twitch.android.mod.models.api;


import com.google.gson.annotations.SerializedName;


public class FfzRoom {
    @SerializedName("set")
    private int set;

    public int getSet() {
        return set;
    }

    public void setSet(int set) {
        this.set = set;
    }
}
