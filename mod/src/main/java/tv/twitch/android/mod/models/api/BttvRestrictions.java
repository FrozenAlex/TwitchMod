package tv.twitch.android.mod.models.api;

import java.util.List;

public class BttvRestrictions {
    private List<String> channels;

    private List<String> games;

    public List<String> getChannels() {
        return channels;
    }

    public void setChannels(List<String> channels) {
        this.channels = channels;
    }

    public List<String> getGames() {
        return games;
    }

    public void setGames(List<String> games) {
        this.games = games;
    }
}
