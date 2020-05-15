package tv.twitch.android.shared.chat.messageinput.u;

import tv.twitch.android.mod.bridges.Hooks;

/**
 * Source: EmoteAdapterSection
 */
public class d {
    private final String e = "";


    private final String k() {
        return null;
    };

    public void a(Object b0Var) {
//        invoke-direct {p0}, Ltv/twitch/android/shared/chat/messageinput/u/d;->k()Ljava/lang/String;
//        move-result-object v0
//        iget-object v4, p0, Ltv/twitch/android/shared/chat/messageinput/u/d;->e:Ljava/lang/String;
//        invoke-static {v0, v4}, Ltv/twitch/android/mod/bridges/Hooks;->hookSetName(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
//        move-result-object v0

        String str = Hooks.hookSetName(k(), this.e); // TODO: __HOOK
    }
}