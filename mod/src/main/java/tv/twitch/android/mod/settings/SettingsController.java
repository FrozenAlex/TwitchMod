package tv.twitch.android.mod.settings;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tv.twitch.android.mod.bridges.LoaderLS;
import tv.twitch.android.mod.models.settings.EmoteSize;
import tv.twitch.android.mod.models.settings.ExoPlayerSpeed;
import tv.twitch.android.mod.models.settings.Gifs;
import tv.twitch.android.mod.models.settings.MiniPlayerSize;
import tv.twitch.android.mod.models.settings.PlayerImpl;
import tv.twitch.android.mod.models.PreferenceItem;
import tv.twitch.android.mod.utils.Helper;
import tv.twitch.android.mod.utils.Logger;
import tv.twitch.android.shared.ui.menus.k;
import tv.twitch.android.shared.ui.menus.p.b;
import tv.twitch.android.shared.ui.menus.q.a;


public class SettingsController {
    private static final String TELEGRAM_URL = "https://t.me/pubTw";
    private static final String GITHUB_URL = "https://github.com/nopbreak/TwitchMod";


    public static void onToggleEvent(tv.twitch.android.shared.ui.menus.w.b item, boolean isChecked) {
        if (item == null) {
            Logger.error("item is null");
            return;
        }

        k.a var = item.n();
        if (var == null) {
            Logger.error("var is null");
            return;
        }

        String prefKey = var.getPreferenceKey();
        if (TextUtils.isEmpty(prefKey)) {
            Logger.warning("prefKey is null");
            return;
        }

        PrefManager prefManager = LoaderLS.getInstance().getPrefManager();
        prefManager.updateBoolean(prefKey, isChecked);
    }

    private static class OnTelegramClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Helper.openUrl(TELEGRAM_URL);
        }
    }

    private static class OnGithubClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Helper.openUrl(GITHUB_URL);
        }
    }

    public static final class OnDropEvent implements a.a1<PreferenceItem> {
        final ArrayAdapter adapter;

        OnDropEvent(ArrayAdapter arrayAdapter) {
            this.adapter = arrayAdapter;
        }

        @Override
        public void a(a<PreferenceItem> menuModel, int itemPos, boolean z) {
            PreferenceItem preference = (PreferenceItem) this.adapter.getItem(itemPos);
            if (preference == null) {
                Logger.error("preference is null");
                return;
            }

            PrefManager prefManager = LoaderLS.getInstance().getPrefManager();
            Logger.debug("Preference key=" + preference.getPreferenceKey() + ", val=" + preference.getPreferenceValue());
            prefManager.updateString(preference.getPreferenceKey(), preference.getPreferenceValue());
        }
    }

    public static class MenuFactory {
        private static tv.twitch.android.shared.ui.menus.p.b getMenuInfo(String title, String desc, View.OnClickListener listener) {
            return new tv.twitch.android.shared.ui.menus.s.a(title, desc, null, null, null, null, listener, 0, null);
        }

        private static tv.twitch.android.shared.ui.menus.p.b getToggleMenu(String title, String desc, boolean state, k.a controller) {
            return new tv.twitch.android.shared.ui.menus.w.b(title, desc, null, state, false, null, false, null, false, null, null, null, controller, null, 12276, null);
        }

        private static tv.twitch.android.shared.ui.menus.p.b getDropDownMenu(Context context, String title, String desc, ArrayList<PreferenceItem> list, PreferenceItem state) {
            ArrayAdapter<PreferenceItem> adapter = new ArrayAdapter<>(context, tv.twitch.android.settings.d.twitch_spinner_dropdown_item, list);
            return new tv.twitch.android.shared.ui.menus.q.a<>(adapter, list.indexOf(state), title, desc, null, null, new OnDropEvent(adapter));
        }
    }

    public static void initialize(Context context, List<b> items) {
        items.clear();

        PrefManager prefManager = LoaderLS.getInstance().getPrefManager();

        items.add(MenuFactory.getMenuInfo("BetterTTV/FFZ", null, null));
        items.add(MenuFactory.getToggleMenu("Enable", "Show BetterTTV/FFZ emoticons in chat", prefManager.isEmotesOn(), k.a.BttvEmotes));
        items.add(MenuFactory.getToggleMenu("Emote menu", "Adds extra BetterTTV/FFZ emoticons to emote picker. Not working on some devices", prefManager.isHookEmoticonSetOn(), k.a.BttvEmotesPicker));
        items.add(MenuFactory.getDropDownMenu(context, "GIFS", "Change the mode of how GIF emoticons are showing up", new ArrayList<PreferenceItem>(Arrays.asList(Gifs.values())), prefManager.getGifsStrategy()));
        items.add(MenuFactory.getDropDownMenu(context, "Source emote size", null, new ArrayList<PreferenceItem>(Arrays.asList(EmoteSize.values())), prefManager.getEmoteSize()));

        items.add(MenuFactory.getMenuInfo("Chat", null, null));
        items.add(MenuFactory.getToggleMenu("Floating chat", "Enable floating chat over video in landscape mode", prefManager.isFloatingChatEnabled(), k.a.FloatingChat));
        items.add(MenuFactory.getToggleMenu("Tap copy", "Tap and hold on the message to copy", prefManager.isCopyMsgOn(), k.a.CopyMsg));
        items.add(MenuFactory.getToggleMenu("Timestamps", "Display timestamps alongside chat messages", prefManager.isTimestampsOn(), k.a.Timestamps));
        items.add(MenuFactory.getToggleMenu("Auto-redeem channel points", "Auto click the bonus channel points button when it appears", prefManager.isClickerOn(), k.a.Points));

        items.add(MenuFactory.getMenuInfo("Player", null, null));
        items.add(MenuFactory.getToggleMenu("Turn off autoplay", "Disable autoplay of the next video", prefManager.isDisableAutoplay(), k.a.AutoPlay));
        items.add(MenuFactory.getToggleMenu("Video stats", "Enable video debug information", prefManager.isShowVideoDebugPanel(), k.a.VideoDebugPanel));
        items.add(MenuFactory.getDropDownMenu(context, "Player implementation", null, new ArrayList<PreferenceItem>(Arrays.asList(PlayerImpl.values())), prefManager.getPlayer()));
        items.add(MenuFactory.getDropDownMenu(context, "MiniPlayer size", null, new ArrayList<PreferenceItem>(Arrays.asList(MiniPlayerSize.values())), prefManager.getMiniPlayerSize()));
        items.add(MenuFactory.getDropDownMenu(context, "ExoPlayer VOD speed", null, new ArrayList<PreferenceItem>(Arrays.asList(ExoPlayerSpeed.values())), prefManager.getExoplayerSpeed()));

        items.add(MenuFactory.getMenuInfo("Swipe controls", null, null));
        items.add(MenuFactory.getToggleMenu("Volume swipe", "Control volume by swiping", prefManager.isVolumeSwipeEnabled(), k.a.VolumeSwipe));
        items.add(MenuFactory.getToggleMenu("Brightness swipe", "Control brightness by swiping", prefManager.isBrightnessSwipeEnabled(), k.a.BrightnessSwipe));

        items.add(MenuFactory.getMenuInfo("Patches", null, null));
        items.add(MenuFactory.getToggleMenu("Hide «Recommended Streams»", null, prefManager.isDisableRecommendations(), k.a.HideRecommendedStreams));
        items.add(MenuFactory.getToggleMenu("Hide «Recent Watching»", null, prefManager.isDisableRecentWatching(), k.a.HideResumeWatchingStreams));
        items.add(MenuFactory.getToggleMenu("Hide «Followed Games»", null, prefManager.isDisableFollowedGames(), k.a.HideFollowedGames));
        items.add(MenuFactory.getToggleMenu("Hide «Discover» tab", "You must restart the app to apply changes", prefManager.isHideDiscoverTab(), k.a.HideDiscoverTab));
        items.add(MenuFactory.getToggleMenu("Hide «ESports» tab", "You must restart the app to apply changes", prefManager.isHideEsportsTab(), k.a.HideEsportsTab));
        items.add(MenuFactory.getToggleMenu("Disable recent searches", "Hide all your recent searches", prefManager.isDisableRecentSearch(), k.a.RecentSearch));
        items.add(MenuFactory.getToggleMenu("Developer mode", "This option should only be enable if you think you need it", prefManager.isDevModeOn(), k.a.DevMode));

        items.add(MenuFactory.getMenuInfo("Info", null, null));

        items.add(MenuFactory.getMenuInfo("TwitchMod r11 (9.1.1)", "TEST BUILD", null));
        items.add(MenuFactory.getMenuInfo("Open Telegram channel", null, new OnTelegramClick()));
        items.add(MenuFactory.getMenuInfo("Open GitHub page", null, new OnGithubClick()));
    }
}
