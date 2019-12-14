package tv.twitch.a.m.p.i0;

import java.util.List;

import tv.twitch.android.mod.settings.PrefManager;

public class c {
    public String b(String str) { // TODO: __REPLACE
        if (PrefManager.isForcedQuality()) {
            if (str != null)
                if (str.contains("audio") || str.toLowerCase().equals("auto")) {
                    return org(str);
            }
            if (this.t() != null) {
                for (j sm : this.t()) {
                    if (sm != null) {
                        String quality = sm.b();
                        if (quality != null && quality.contains("source"))
                            return sm.d();
                    }
                }
            }
        }

        return org(str);
    }


    public String org(String str) { // TODO: __RENAME
        return "";
    }

    private List<j> t() {
        return null;
    }
}
