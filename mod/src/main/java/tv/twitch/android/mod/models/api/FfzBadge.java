package tv.twitch.android.mod.models.api;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class FfzBadge {
    @SerializedName("color")
    private String color;
    @SerializedName("css")
    private String css;
    @SerializedName("id")
    private long id;
    @SerializedName("image")
    private String image;
    @SerializedName("name")
    private String name;
    @SerializedName("replaces")
    private String replaces;
    @SerializedName("slot")
    private long slot;
    @SerializedName("title")
    private String title;
    @SerializedName("urls")
    private HashMap<Integer, String> urls;

    public HashMap<Integer, String> getUrls() {
        return urls;
    }

    public void setUrls(HashMap<Integer, String> urls) {
        this.urls = urls;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCss() {
        return css;
    }

    public void setCss(String css) {
        this.css = css;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getSlot() {
        return slot;
    }

    public void setSlot(long slot) {
        this.slot = slot;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getReplaces() {
        return replaces;
    }

    public void setReplaces(String replaces) {
        this.replaces = replaces;
    }
}
