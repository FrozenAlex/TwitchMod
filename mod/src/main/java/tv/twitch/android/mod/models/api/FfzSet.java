package tv.twitch.android.mod.models.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FfzSet {
    @SerializedName("_type")
    private int type;
    @SerializedName("css")
    private String css;
    @SerializedName("description")
    private String description;
    @SerializedName("emoticons")
    private List<FfzEmoticon> emoticons;
    @SerializedName("icon")
    private String icon;
    @SerializedName("id")
    private long id;
    @SerializedName("title")
    private String title;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCss() {
        return css;
    }

    public void setCss(String css) {
        this.css = css;
    }

    public List<FfzEmoticon> getEmoticons() {
        return emoticons;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setEmoticons(List<FfzEmoticon> emoticons) {
        this.emoticons = emoticons;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
