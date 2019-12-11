package tv.twitch.android.app.consumer;

import android.annotation.SuppressLint;
import android.app.Application;

import tv.twitch.android.mod.utils.Loader;

@SuppressLint("Registered")
public class TwitchApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Loader.init(this); // TODO: __ADD_END
    }
}
