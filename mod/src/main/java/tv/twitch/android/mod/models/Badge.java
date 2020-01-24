package tv.twitch.android.mod.models;

public class Badge {
    private final String url;
    private final String name;
    private final int id;

    public Badge(String url, String name, int id) {
        this.url = url;
        this.name = name;
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
