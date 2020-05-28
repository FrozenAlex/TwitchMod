package tv.twitch.android.mod.net;


import static tv.twitch.android.mod.bridges.RetrofitUtils.getRetrofitClient;


public class ServiceFactory {
    private static final String BTTV_API = "https://api.betterttv.net/";

    private static volatile BttvApi mBttvApi;

    public static BttvApi getBttvApi() {
        if (mBttvApi == null) {
            synchronized (ServiceFactory.class) {
                if (mBttvApi == null)
                    mBttvApi = getRetrofitClient(BTTV_API).create(BttvApi.class);
            }
        }

        return mBttvApi;
    }
}
