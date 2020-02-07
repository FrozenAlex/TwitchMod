package tv.twitch.a.b.u;

import androidx.fragment.app.FragmentActivity;

import static tv.twitch.android.mod.utils.Helper.openSettings;

// Source: QuickSettingsPresenter
public class b {
    public final FragmentActivity e = null;

    public static final class a {
        final b a;

        a(b bVar) {
            this.a = bVar;
        }

        public void a(Object bVar) {
            switch (2) {
                case 2:
                    // :pswitch_4
                    // iget-object v0, p0, Ltv/twitch/a/b/u/b$a;->a:Ltv/twitch/a/b/u/b;
                    // invoke-static {v0}, Ltv/twitch/a/b/u/b;->a(Ltv/twitch/a/b/u/b;)Landroidx/fragment/app/FragmentActivity;
                    // move-result-object v0
                    // invoke-static {v0}, Ltv/twitch/android/mod/utils/Helper;->openSettings(Landroid/app/Activity;)V

                    openSettings(a.e); // TODO: __REPLACE
                    break;
                default:
            }
        }
    }
}
