package tv.twitch.android.mod.settings;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tv.twitch.android.mod.bridges.IDPub;
import tv.twitch.android.mod.bridges.LoaderLS;
import tv.twitch.android.mod.models.settings.EmotePickerView;
import tv.twitch.android.mod.models.settings.FollowView;
import tv.twitch.android.mod.models.settings.UserMessagesFiltering;
import tv.twitch.android.mod.models.settings.EmoteSize;
import tv.twitch.android.mod.models.settings.ExoPlayerSpeed;
import tv.twitch.android.mod.models.settings.Gifs;
import tv.twitch.android.mod.models.settings.MiniPlayerSize;
import tv.twitch.android.mod.models.settings.PlayerImpl;
import tv.twitch.android.mod.models.PreferenceItem;
import tv.twitch.android.mod.utils.Helper;
import tv.twitch.android.mod.utils.Logger;
import tv.twitch.android.shared.ui.menus.j;
import tv.twitch.android.shared.ui.menus.l.b;
import tv.twitch.android.shared.ui.menus.m.a;


public class SettingsController {
    private static final String TELEGRAM_URL = "https://t.me/pubTw";
    private static final String GITHUB_URL = "https://github.com/nopbreak/TwitchMod";


    public static void OnToggleEvent(tv.twitch.android.shared.ui.menus.s.b menuItem, boolean isChecked) {
        if (menuItem == null) {
            Logger.error("menuItem is null");
            return;
        }

        j.a prefType = menuItem.t();
        if (prefType == null) {
            Logger.error("prefType is null");
            return;
        }

        String preferenceKey = prefType.getPreferenceKey();
        if (TextUtils.isEmpty(preferenceKey)) {
            Logger.warning("preferenceKey is null. Object=" + prefType.toString());
            return;
        }

        LoaderLS.getPrefManagerInstance().updateBoolean(preferenceKey, isChecked);
    }

    public static final class OnDropEvent implements a.a1<PreferenceItem> {
        final ArrayAdapter<PreferenceItem> adapter;

        OnDropEvent(ArrayAdapter<PreferenceItem> arrayAdapter) {
            this.adapter = arrayAdapter;
        }

        @Override
        public void a(a<PreferenceItem> menuModel, int itemPos, boolean z) {
            PreferenceItem preference = this.adapter.getItem(itemPos);
            if (preference == null) {
                Logger.error("preference is null");
                return;
            }

            Logger.debug("Preference key=" + preference.getPreferenceKey() + ", val=" + preference.getPreferenceValue());
            LoaderLS.getPrefManagerInstance().updateString(preference.getPreferenceKey(), preference.getPreferenceValue());
        }
    }

    public static class MenuFactory {
        private static tv.twitch.android.shared.ui.menus.l.b getMenuInfo(String title, String desc, View.OnClickListener listener) {
            return new tv.twitch.android.shared.ui.menus.o.a(title, desc, null, null, null, null, listener, 0, null);
        }

        private static tv.twitch.android.shared.ui.menus.l.b getToggleMenu(String title, String desc, boolean state, j.a controller) {
            return new tv.twitch.android.shared.ui.menus.s.b(title, desc, null, state, false, null, false, null, false, null, null, null, controller, null, 12276, null);
        }

        private static tv.twitch.android.shared.ui.menus.l.b getDropDownMenu(Context context, String title, String desc, ArrayList<PreferenceItem> list, PreferenceItem state) {
            ArrayAdapter<PreferenceItem> adapter = new ArrayAdapter<>(context, tv.twitch.android.settings.d.twitch_spinner_dropdown_item, list);
            return new tv.twitch.android.shared.ui.menus.m.a<>(adapter, list.indexOf(state), title, desc, null, null, new OnDropEvent(adapter));
        }
    }

    public static void initialize(Context context, List<b> items) {
        items.clear();

        PrefManager prefManager = LoaderLS.getPrefManagerInstance();
        IDPub idPub = LoaderLS.getIDPubInstance();

        items.add(MenuFactory.getMenuInfo(idPub.getString("mod_category_settings_chat_bttv"), null, null));
        items.add(MenuFactory.getToggleMenu(idPub.getString("mod_bttv_settings_bttv_emotes"), idPub.getString("mod_bttv_settings_bttv_emotes_desc"), prefManager.isEmotesOn(), j.a.BttvEmotes));
        items.add(MenuFactory.getToggleMenu(idPub.getString("mod_bttv_settings_bttv_widget"), idPub.getString("mod_bttv_settings_bttv_widget_desc"), prefManager.isHookEmoticonSetOn(), j.a.BttvEmotesPicker));
        items.add(MenuFactory.getDropDownMenu(context, idPub.getString("mod_bttv_settings_bttv_gifs"), idPub.getString("mod_bttv_settings_bttv_gifs_desc"), new ArrayList<PreferenceItem>(Arrays.asList(Gifs.values())), prefManager.getGifsStrategy()));
        items.add(MenuFactory.getDropDownMenu(context, idPub.getString("mod_bttv_settings_emote_size"), idPub.getString("mod_bttv_settings_emote_size_desc"), new ArrayList<PreferenceItem>(Arrays.asList(EmoteSize.values())), prefManager.getEmoteSize()));

        items.add(MenuFactory.getMenuInfo(idPub.getString("mod_category_settings_chat_category"), null, null));
        items.add(MenuFactory.getDropDownMenu(context, idPub.getString("mod_chat_settings_filtering"), idPub.getString("mod_chat_settings_filtering_desc"), new ArrayList<PreferenceItem>(Arrays.asList(UserMessagesFiltering.values())), prefManager.getChatFiltering()));
        items.add(MenuFactory.getDropDownMenu(context, idPub.getString("mod_chat_settings_emote_picker"), null, new ArrayList<PreferenceItem>(Arrays.asList(EmotePickerView.values())), prefManager.getEmotePickerView()));
        items.add(MenuFactory.getToggleMenu(idPub.getString("mod_chat_settings_floating_chat"), idPub.getString("mod_chat_settings_floating_chat_desc"), prefManager.isFloatingChatEnabled(), j.a.FloatingChat));
        items.add(MenuFactory.getToggleMenu(idPub.getString("mod_chat_settings_tap_copy"), idPub.getString("mod_chat_settings_tap_copy_desc"), prefManager.isCopyMsgOn(), j.a.CopyMsg));
        items.add(MenuFactory.getToggleMenu(idPub.getString("mod_chat_settings_timestamps"), idPub.getString("mod_chat_settings_timestamps_desc"), prefManager.isTimestampsOn(), j.a.Timestamps));

        items.add(MenuFactory.getMenuInfo(idPub.getString("mod_category_settings_player_category"), null, null));
        items.add(MenuFactory.getToggleMenu(idPub.getString("mod_player_settings_disable_autoplay"), idPub.getString("mod_player_settings_disable_autoplay_desc"), prefManager.isDisableAutoplay(), j.a.AutoPlay));
        items.add(MenuFactory.getToggleMenu(idPub.getString("mod_player_settings_video_debug_panel"), idPub.getString("mod_player_settings_video_debug_panel_desc"), prefManager.isShowVideoDebugPanel(), j.a.VideoDebugPanel));
        items.add(MenuFactory.getDropDownMenu(context, idPub.getString("mod_player_settings_follow_view"), null, new ArrayList<PreferenceItem>(Arrays.asList(FollowView.values())), prefManager.getFollowView()));
        items.add(MenuFactory.getDropDownMenu(context, idPub.getString("mod_player_settings_player"), idPub.getString("mod_player_settings_player_desc"), new ArrayList<PreferenceItem>(Arrays.asList(PlayerImpl.values())), prefManager.getPlayer()));
        items.add(MenuFactory.getDropDownMenu(context, idPub.getString("mod_player_settings_miniplayer_size"), idPub.getString("mod_player_settings_miniplayer_size_desc"), new ArrayList<PreferenceItem>(Arrays.asList(MiniPlayerSize.values())), prefManager.getMiniPlayerSize()));
        items.add(MenuFactory.getDropDownMenu(context, idPub.getString("mod_player_settings_exoplayer_speed"), idPub.getString("mod_player_settings_exoplayer_speed_desc"), new ArrayList<PreferenceItem>(Arrays.asList(ExoPlayerSpeed.values())), prefManager.getExoplayerSpeed()));

        items.add(MenuFactory.getMenuInfo(idPub.getString("mod_category_settings_swipe"), null, null));
        items.add(MenuFactory.getToggleMenu(idPub.getString("mod_swipe_settings_enable_volume"), idPub.getString("mod_swipe_settings_enable_volume_desc"), prefManager.isVolumeSwipeEnabled(), j.a.VolumeSwipe));
        items.add(MenuFactory.getToggleMenu(idPub.getString("mod_swipe_settings_enable_brightness"), idPub.getString("mod_swipe_settings_enable_brightness_desc"), prefManager.isBrightnessSwipeEnabled(), j.a.BrightnessSwipe));

        items.add(MenuFactory.getMenuInfo(idPub.getString("mod_category_settings_patch"), null, null));
        items.add(MenuFactory.getToggleMenu(idPub.getString("mod_patches_settings_disable_recommendations"), idPub.getString("mod_patches_settings_disable_recommendations_desc"), prefManager.isDisableRecommendations(), j.a.HideRecommendedStreams));
        items.add(MenuFactory.getToggleMenu(idPub.getString("mod_patches_settings_disable_resume_watching"), idPub.getString("mod_patches_settings_disable_resume_watching_desc"), prefManager.isDisableRecentWatching(), j.a.HideResumeWatchingStreams));
        items.add(MenuFactory.getToggleMenu(idPub.getString("mod_patches_settings_disable_followed_games"), idPub.getString("mod_patches_settings_disable_followed_games_desc"), prefManager.isDisableFollowedGames(), j.a.HideFollowedGames));
        items.add(MenuFactory.getToggleMenu(idPub.getString("mod_patches_settings_hide_discover"), idPub.getString("mod_patches_settings_hide_discover_desc"), prefManager.isHideDiscoverTab(), j.a.HideDiscoverTab));
        items.add(MenuFactory.getToggleMenu(idPub.getString("mod_patches_settings_hide_esports"), idPub.getString("mod_patches_settings_hide_esports_desc"), prefManager.isHideEsportsTab(), j.a.HideEsportsTab));
        items.add(MenuFactory.getToggleMenu(idPub.getString("mod_patches_settings_disable_recent_search"), idPub.getString("mod_patches_settings_disable_recent_search_desc"), prefManager.isDisableRecentSearch(), j.a.RecentSearch));
        items.add(MenuFactory.getToggleMenu(idPub.getString("mod_patches_settings_dev_mode"), idPub.getString("mod_patches_settings_dev_mode_desc"), prefManager.isDevModeOn(), j.a.DevMode));
        if (prefManager.isDevModeOn()) {
            items.add(MenuFactory.getToggleMenu(idPub.getString("mod_patches_settings_interceptor"), null, prefManager.isInterceptorOn(), j.a.Interceptor));
        }

        items.add(MenuFactory.getMenuInfo(idPub.getString("mod_category_info"), null, null));

        items.add(MenuFactory.getMenuInfo(LoaderLS.VERSION, LoaderLS.BUILD, null));
        items.add(MenuFactory.getMenuInfo(idPub.getString("mod_info_open_telegram"), null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.openUrl(TELEGRAM_URL);
            }
        }));
        items.add(MenuFactory.getMenuInfo(idPub.getString("mod_info_open_github"), null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.openUrl(GITHUB_URL);
            }
        }));
    }
}
