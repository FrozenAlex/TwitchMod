package tv.twitch.chat;

public class ChatEmoticonUrl extends ChatEmoticon { //TODO: __ADD
    private String url;

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
