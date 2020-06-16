package tv.twitch.android.mod.bridges;


import android.content.Context;
import android.content.res.Resources;
import android.util.LruCache;


public class IDPub {
    public static final int PLAYER_OVERLAY_ID = 0x7f0b0626;
    public static final int DEBUG_PANEL_CONTAINER_ID = 0x7f0b02be;
    public static final int FLOATING_CHAT_CONTAINER_ID = 0x7f0b03b2;
    public static final int VIDEO_DEBUG_LIST_ID = 0x7f0b08d7;
    public static final int MESSAGES_CONTAINER_ID = 0x7f0b04e3;

    public final PubCache mStringIdsCache = new PubCache(200, "string");


    private static class PubCache extends LruCache<String, Integer> {
        private final String mDefType;

        public PubCache(int maxSize, String defType) {
            super(maxSize);
            mDefType = defType;
        }

        @Override
        protected Integer create(String key) {
            Context context = LoaderLS.getInstance();
            Resources resources = context.getResources();
            return resources.getIdentifier(key, mDefType, context.getPackageName());
        }
    }

    public String getString(String name) {
        int resId = mStringIdsCache.get(name);
        if (resId == 0) {
            return "RESOURCE ID NOT FOUND: '" + name + "'";
        } else {
            return LoaderLS.getInstance().getResources().getString(resId);
        }
    }
}
