package tv.twitch.a.m.p.i0;

import android.text.TextUtils;

import java.util.List;

import tv.twitch.android.mod.settings.PrefManager;
import tv.twitch.android.mod.utils.Logger;

public class c {
    public String b(String str) { // TODO: __REPLACE
        if (PrefManager.isForcedQuality()) {
            if (!TextUtils.isEmpty(str) && str.toLowerCase().equals("auto")) {
                if (this.t() != null) {
                    for (j sm : this.t()) {
                        if (sm != null) {
                            String quality = sm.b();
                            if (!TextUtils.isEmpty(quality) && quality.toLowerCase().contains("source")) {
                                String url = sm.d();
                                Logger.debug("url: " + url);
                                return url;
                            }
                        }
                    }
                }
            }

        }

        return org(str);
    }


    // b(String str)
    public String org(String str) { // TODO: __RENAME
        return "";
    }

    private List<j> t() {
        return null;
    }
}
