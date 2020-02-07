package tv.twitch.android.mod.bridges;


import android.content.Context;

import tv.twitch.android.app.consumer.TwitchApplication;
import tv.twitch.android.mod.badges.BadgeManager;
import tv.twitch.android.mod.emotes.EmoteManager;
import tv.twitch.android.mod.settings.PrefManager;
import tv.twitch.android.mod.utils.Helper;
import tv.twitch.android.mod.utils.Logger;
import tv.twitch.android.mod.utils.TwitchUsers;


public class LoaderLS extends TwitchApplication {
    private TwitchUsers sTwitchUser;
    private EmoteManager sEmoteManager;
    private PrefManager sPrefManager;
    private BadgeManager sBadgeManager;
    private Helper sHelper;

    private static LoaderLS sInstance = null;

    public static LoaderLS getInstance() {
        return sInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        init(); // init before dagger
        super.onCreate();
    }

    private void init() {
        Logger.debug("Init LoaderLS");
        sInstance = this;
        sTwitchUser = TwitchUsers.getInstance();
        sEmoteManager = EmoteManager.getInstance();
        sBadgeManager = BadgeManager.getInstance();
        sHelper = Helper.getInstance();
        sPrefManager = new PrefManager(getApplicationContext());
    }

    public BadgeManager getBadgeManager() {
        if (sBadgeManager == null) {
            synchronized (LoaderLS.class) {
                if (sBadgeManager == null) {
                    Logger.warning("getting new instance");
                    sBadgeManager = BadgeManager.getInstance();
                }
            }
        }
        return sBadgeManager;
    }

    public EmoteManager getEmoteManager() {
        if (sEmoteManager == null) {
            synchronized (LoaderLS.class) {
                if (sEmoteManager == null) {
                    Logger.warning("getting new instance");
                    sEmoteManager = EmoteManager.getInstance();
                }
            }
        }
        return sEmoteManager;
    }

    public PrefManager getPrefManager() {
        if (sPrefManager == null) {
            synchronized (LoaderLS.class) {
                if (sPrefManager == null) {
                    Logger.warning("creating new instance");
                    sPrefManager = new PrefManager(getApplicationContext());
                }
            }
        }
        return sPrefManager;
    }

    public TwitchUsers getTwitchUser() {
        if (sTwitchUser == null) {
            synchronized (LoaderLS.class) {
                if (sTwitchUser == null) {
                    Logger.warning("getting new instance");
                    sTwitchUser = TwitchUsers.getInstance();
                }
            }
        }
        return sTwitchUser;
    }

    public Helper getHelper() {
        if (sHelper == null) {
            synchronized (LoaderLS.class) {
                if (sHelper == null) {
                    Logger.warning("getting new instance");
                    sHelper = Helper.getInstance();
                }
            }
        }
        return sHelper;
    }
}
