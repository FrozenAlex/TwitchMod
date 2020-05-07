package tv.twitch.android.mod.models.api;


import com.google.gson.annotations.SerializedName;

import java.util.List;


public class FfzSet {
    @SerializedName("emoticons")
    private List<FfzEmoticon> emoticons;

    public List<FfzEmoticon> getEmoticons() {
        return emoticons;
    }

    public void setEmoticons(List<FfzEmoticon> emoticons) {
        this.emoticons = emoticons;
    }
}
