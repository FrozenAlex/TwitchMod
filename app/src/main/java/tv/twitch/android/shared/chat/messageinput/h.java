package tv.twitch.android.shared.chat.messageinput;

import tv.twitch.android.mod.bridges.LoaderLS;

// Source: ChatMessageInputViewPresenter
public class h {
    static final class c<T> {
        public final void a(tv.twitch.a.k.e.z0.b bVar) {
            LoaderLS.getInstance().getHelper().setCurrentChannel(bVar.a().getId()); // TODO: __ADD
        }
    }
}
