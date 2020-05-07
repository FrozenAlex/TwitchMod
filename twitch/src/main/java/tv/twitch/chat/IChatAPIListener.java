package tv.twitch.chat;

public interface IChatAPIListener {
    void chatUserEmoticonSetsChanged(int i2, ChatEmoticonSet[] chatEmoticonSetArr);
}
