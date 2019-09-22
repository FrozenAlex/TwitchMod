package tv.twitch.android.mod.models.api;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class FfzEmoticon {
    @SerializedName("css")
    private String css;
    @SerializedName("height")
    private String height;
    @SerializedName("hidden")
    private boolean hidden;
    @SerializedName("id")
    private long id;
    @SerializedName("margins")
    private String margins;
    @SerializedName("modifier")
    private boolean modifier;
    @SerializedName("name")
    private String name;
    @SerializedName("offset")
    private String offset;
    @SerializedName("owner")
    private FfzOwner owner;
    @SerializedName("public")
    private boolean publicEmote;
    @SerializedName("urls")
    private HashMap<Integer, String> urls;
    @SerializedName("usage_count")
    private long usageCount;
    @SerializedName("width")
    private long width;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FfzOwner getOwner() {
        return owner;
    }

    public void setOwner(FfzOwner owner) {
        this.owner = owner;
    }

    public HashMap<Integer, String> getUrls() {
        return urls;
    }

    public void setUrls(HashMap<Integer, String> urls) {
        this.urls = urls;
    }

    public long getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(long usageCount) {
        this.usageCount = usageCount;
    }

    public long getWidth() {
        return width;
    }

    public void setWidth(long width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getMargins() {
        return margins;
    }

    public void setMargins(String margins) {
        this.margins = margins;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isModifier() {
        return modifier;
    }

    public void setModifier(boolean modifier) {
        this.modifier = modifier;
    }

    public boolean isPublicEmote() {
        return publicEmote;
    }

    public void setPublicEmote(boolean publicEmote) {
        this.publicEmote = publicEmote;
    }
}
