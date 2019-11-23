package tv.twitch.android.mod.net;

import static tv.twitch.android.mod.bridges.RetrofitUtils.getRetrofitClient;

public class ServiceFactory {
    private static final String BTTV_API = "https://api.betterttv.net/";
    private static final String FFZ_API = "https://api.frankerfacez.com/";
    private static final String TWITCH_API = "https://api.twitch.tv/";

    private static BttvApi mBttvApi;
    private static FfzApi mFfzApi;
    private static TwitchApi mTwitchApi;


    public static BttvApi getBttvApi() {
        if (mBttvApi == null) {
            mBttvApi = getRetrofitClient(BTTV_API).create(BttvApi.class);
        }

        return mBttvApi;
    }

    public static FfzApi getFfzApi() {
        if (mFfzApi == null) {
            mFfzApi = getRetrofitClient(FFZ_API).create(FfzApi.class);
        }

        return mFfzApi;
    }

    public static TwitchApi getTwitchApi() {
        if (mTwitchApi == null) {
            mTwitchApi = getRetrofitClient(TWITCH_API).create(TwitchApi.class);
        }

        return mTwitchApi;
    }
}
