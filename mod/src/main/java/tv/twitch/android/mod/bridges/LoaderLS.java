package tv.twitch.android.mod.bridges;


import android.content.Context;

import java.util.Map;

import tv.twitch.android.app.consumer.TwitchApplication;
import tv.twitch.android.mod.emotes.EmoteManager;
import tv.twitch.android.mod.settings.PrefManager;
import tv.twitch.android.mod.utils.Helper;
import tv.twitch.android.mod.utils.Logger;


public class LoaderLS extends TwitchApplication {
    public static int PREFERENCES_ID = 0x7f160006;
    public static int PLAYER_OVERLAY_ID = 0x7f0b05ec;

    private EmoteManager sEmoteManager;
    private PrefManager sPrefManager;
    private Helper sHelper;

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

    private void post() {
        // debug glide executor
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Map<Thread, StackTraceElement[]> map = Thread.getAllStackTraces();
                    for (Thread thread : map.keySet()) {
                        if (!thread.isAlive())
                            continue;

                        if (thread.getName().startsWith("glide-animation")) {
                            Logger.debug(thread.getName() + " alive!");
                        }
                    }
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        th.setDaemon(true);
        th.start();
    }

    private void init() {
        Logger.debug("Init LoaderLS");
        sInstance = this;
        sEmoteManager = new EmoteManager();
        sPrefManager = new PrefManager(getApplicationContext());
        sHelper = new Helper();
    }

    public EmoteManager getEmoteManager() {
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

    public Helper getHelper() {
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
}
