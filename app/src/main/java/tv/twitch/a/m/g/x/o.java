package tv.twitch.a.m.g.x;

import h.v.d.j;

public enum o {
    c("playercore", "c"),
    d("exoplayer_2", "e2");

    public final String a;
    private final String b;

    public static final class a {
        public final o a(String str) {
            j.b(str, "name");
            if (j.a((Object) str, (Object) o.c.a)) {
                return o.d; // TODO: __REPLACE
            }
            if (j.a((Object) str, (Object) o.d.a)) {
                return o.d;
            }
            return null;
        }
    }

    private o(String str, String str2) {
        this.a = str;
        this.b = str2;
    }
}