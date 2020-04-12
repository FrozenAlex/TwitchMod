package tv.twitch.android.mod.models.api;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class FfzEmoticon {
    @SerializedName("id")
    private long id;
    @SerializedName("name")
    private String name;
    @SerializedName("urls")
    private HashMap<Integer, String> urls;

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

    public HashMap<Integer, String> getUrls() {
        return urls;
    }

    public void setUrls(HashMap<Integer, String> urls) {
        this.urls = urls;
    }
}
