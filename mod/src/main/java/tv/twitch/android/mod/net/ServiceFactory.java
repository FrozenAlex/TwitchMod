package tv.twitch.android.mod.net;


import static tv.twitch.android.mod.bridges.RetrofitUtils.getRetrofitClient;


public class ServiceFactory {
    private static final String BTTV_API = "https://api.betterttv.net/";
    private static final String FFZ_API = "https://api.frankerfacez.com/";

    private static volatile BttvApi mBttvApi;
    private static volatile FfzApi mFfzApi;

    public static BttvApi getBttvApi() {
        if (mBttvApi == null) {
            synchronized (ServiceFactory.class) {
                if (mBttvApi == null)
                    mBttvApi = getRetrofitClient(BTTV_API).create(BttvApi.class);
            }
        }

        return mBttvApi;
    }

    public static FfzApi getFfzApi() {
        if (mFfzApi == null) {
            synchronized (ServiceFactory.class) {
                if (mFfzApi == null)
                    mFfzApi = getRetrofitClient(FFZ_API).create(FfzApi.class);
            }
        }

        return mFfzApi;
    }
}
