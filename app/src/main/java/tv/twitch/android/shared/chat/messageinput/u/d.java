package tv.twitch.android.shared.chat.messageinput.u;

import tv.twitch.android.mod.bridges.Hooks;

/**
 * Source: EmoteAdapterSection
 */
public class d {
    private String e = "";


    private final String w() {
        return null;
    }

    public void e(Object b0Var) {
        //   invoke-direct {p0}, Ltv/twitch/android/shared/chat/messageinput/u/d;->w()Ljava/lang/String;
        //   move-result-object v0
        //   invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;
        //   const-string v1, ""
        //   invoke-static {v0, v1}, Ltv/twitch/android/mod/bridges/Hooks;->hookSetName(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
        //   move-result-object v0

        String str = Hooks.hookSetName(w(), this.e); // TODO: __HOOK
    }
}