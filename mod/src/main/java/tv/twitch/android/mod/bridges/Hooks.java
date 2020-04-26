package tv.twitch.android.mod.bridges;


import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;

import com.google.android.exoplayer2.PlaybackParameters;

import java.util.Date;

import tv.twitch.a.k.c0.b.s.h;
import tv.twitch.android.mod.settings.PrefManager;
import tv.twitch.android.mod.utils.ChatUtils;
import tv.twitch.android.mod.utils.Helper;
import tv.twitch.android.shared.chat.communitypoints.models.CommunityPointsModel;
import tv.twitch.chat.ChatEmoticonSet;


// TODO: __HOOK
public class Hooks {
    /**
     * signature: Ltv/twitch/a/k/c0/b/s/h;-><init>
     */
    public static h hookUrlDrawableConstructor(h instance) {
        if (instance == null)
            return null;

        PrefManager prefManager = LoaderLS.getInstance().getPrefManager();
        if (prefManager.isDisableGifs()) {
            return new StaticUrlDrawable(instance);
        }

        return instance;
    }


    /**
     * Class: ChatMessageFactory
     * signature: private final CharSequence a(tv.twitch.a.k.g.g gVar, int i2, tv.twitch.a.k.g.t0.a aVar, boolean z, String str, String str2)
     */
    public static int hookUsernameSpanColor(int color) {
        Helper helper = LoaderLS.getInstance().getHelper();
        return helper.getFineColor(color);
    }


    /**
     * Class: EmoteAdapterSection
     * signature: public void a(RecyclerView.b0 b0Var)
     */
    public static String hookSetName(String org, String setId) {
        if (!TextUtils.isEmpty(setId)) {
            if (setId.equals(ChatUtils.EmoteSet.GLOBAL.getId()))
                return "BetterTTV Global Emotes";
            else if (setId.equals(ChatUtils.EmoteSet.FFZ.getId()))
                return "FFZ Emotes";
            else if (setId.equals(ChatUtils.EmoteSet.BTTV.getId()))
                return "BetterTTV Emotes";
        }

        return org;
    }

    /**
     * Class: MessageRecyclerItem
     * signature:  public void g()
     */
    public static boolean hookMsgRemover(boolean org) {
        PrefManager manager = LoaderLS.getInstance().getPrefManager();
        return manager.isPreventMsg() || org;
    }

    /**
     * Class: CommunityPointsButtonViewDelegate
     * signature: private final void e(CommunityPointsModel communityPointsModel)
     */
    public static void setClicker(final View pointButtonView, CommunityPointsModel pointsModel) {
        Helper helper = LoaderLS.getInstance().getHelper();
        helper.setClicker(pointButtonView, pointsModel);
    }

    /**
     * Class: StandaloneMediaClock
     * signatute: private PlaybackParameters f = PlaybackParameters.e;
     */
    public static PlaybackParameters hookVodPlayerStandaloneMediaClockInit(PlaybackParameters org) {
        PrefManager manager = LoaderLS.getInstance().getPrefManager();
        float speed = manager.getExoplayerSpeed();
        if (speed == org.a)
            return org;

        for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
            if (element == null)
                continue;

            String clazz = element.getClassName();
            if (TextUtils.isEmpty(clazz))
                continue;

            if (!clazz.equals("tv.twitch.a.k.v.j0.w"))
                continue;

            return new PlaybackParameters(speed);
        }

        return PlaybackParameters.e;
    }

    /**
     * Class: MessageRecyclerItem
     * signature:
     */
    public static Spanned addTimestampToMessage(Spanned spanned) {
        PrefManager manager = LoaderLS.getInstance().getPrefManager();
        if (!manager.isTimestampsOn()) {
            return spanned;
        }

        if (TextUtils.isEmpty(spanned)) {
            return spanned;
        }

        return ChatUtils.addTimestamp(spanned, new Date());
    }

    /**
     * Class: ChatController
     * signature: ChatEmoticonSet[] b()
     */
    public static ChatEmoticonSet[] hookChatEmoticonSet(ChatEmoticonSet[] orgSet) {
        Helper helper = LoaderLS.getInstance().getHelper();
        return helper.hookChatEmoticonSet(orgSet);
    }

    /**
     * Class: *.*
     */
    public static boolean hookAd(boolean org) {
        PrefManager manager = LoaderLS.getInstance().getPrefManager();
        if (manager.isAdblockOn())
            return false;

        return org;
    }

    public static void helper() {
        if (!LoaderLS.getInstance().getPrefManager().isAdblockOn()) { } // TODO: __HOOK
        boolean ad = hookAd(true); // TODO: __HOOK
        Object o = hookUrlDrawableConstructor(new h(null, null)); // TODO: __HOOK
        o = hookVodPlayerStandaloneMediaClockInit(new PlaybackParameters(0.0f)); // TODO: __HOOK
    }
}
