package h.v.d;

// Source: Intrinsics
public class j {
    public static void b(Object obj, String str) {
        if (obj == null) {
            // a(str);
            throw null;
        }
    }

    public static boolean a(Object obj, Object obj2) {
        if (obj == null) {
            return obj2 == null;
        }
        return obj.equals(obj2);
    }


}
