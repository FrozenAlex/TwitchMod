package tv.twitch.android.mod.models.api;

import com.google.gson.annotations.SerializedName;

public class FfzOwner {
    @SerializedName("_id")
    private long id;
    @SerializedName("display_name")
    private String displayName;
    @SerializedName("name")
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
