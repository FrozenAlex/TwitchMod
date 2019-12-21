package tv.twitch.chat;

public class ChatEmoticonUrl extends ChatEmoticon {
    private String url;

    public ChatEmoticonUrl(String emoteId, String match, boolean isRegex, String url) {
        this.emoticonId = emoteId;
        this.match = match;
        this.isRegex = isRegex;
        this.url = url;
    }

    public ChatEmoticonUrl(String match, String url) {
        this.emoticonId = "-1";
        this.isRegex = false;
        this.match = match;
        this.url = url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
