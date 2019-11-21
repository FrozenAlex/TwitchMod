package tv.twitch.android.models.webview;


public enum WebViewSource {
    Chat("chat"),
    Whisper("whisper"),
    ChannelFeed("channel_feed"),
    NewsFeed("news_feed"),
    Profile("profile"),
    Other("other");

    private String mTrackingString;

    private WebViewSource(String str) {
        this.mTrackingString = str;
    }
}