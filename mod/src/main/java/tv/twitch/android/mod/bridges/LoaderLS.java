package tv.twitch.android.mod.bridges;


import android.content.Context;

import tv.twitch.android.app.consumer.TwitchApplication;
import tv.twitch.android.mod.emotes.EmoteManager;
import tv.twitch.android.mod.settings.PrefManager;
import tv.twitch.android.mod.utils.Helper;
import tv.twitch.android.mod.utils.Logger;


public class LoaderLS extends TwitchApplication {
    public static final String VERSION = "TwitchMod v2.2";
    public static final String BUILD = "TEST BUILD r9";

    private EmoteManager sEmoteManager;
    private PrefManager sPrefManager;
    private Helper sHelper;
    private IDPub sIDPub;

    private static volatile LoaderLS sInstance = null;

    public static LoaderLS getInstance() {
        return sInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        init();
        super.onCreate();
        post();
    }

    private void post() { }

    private void init() {
        Logger.debug("Init LoaderLS. " + VERSION);
        sInstance = this;
        sEmoteManager = new EmoteManager();
        sPrefManager = new PrefManager(getApplicationContext());
        sHelper = new Helper();
        sIDPub = new IDPub();
    }

    public static EmoteManager getEmoteMangerInstance() {
        return getInstance().getEmoteManager();
    }

    public static PrefManager getPrefManagerInstance() {
        return getInstance().getPrefManager();
    }

    public static Helper getHelperInstance() {
        return getInstance().getHelper();
    }

    public static IDPub getIDPubInstance() {
        return getInstance().getIDPub();
    }

    private EmoteManager getEmoteManager() {
        if (sEmoteManager == null) {
            synchronized (LoaderLS.class) {
                if (sEmoteManager == null) {
                    Logger.warning("creating new instance");
                    sEmoteManager = new EmoteManager();
                }
            }
        }
        return sEmoteManager;
    }

    private PrefManager getPrefManager() {
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

    private Helper getHelper() {
        if (sHelper == null) {
            synchronized (LoaderLS.class) {
                if (sHelper == null) {
                    Logger.warning("creating new instance");
                    sHelper = new Helper();
                }
            }
        }
        return sHelper;
    }

    private IDPub getIDPub() {
        if (sIDPub == null) {
            synchronized (LoaderLS.class) {
                if (sIDPub == null) {
                    Logger.warning("creating new instance");
                    sIDPub = new IDPub();
                }
            }
        }
        return sIDPub;
    }
}
