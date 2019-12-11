package tv.twitch.android.util;

import android.util.Log;

import java.util.Arrays;

// Source: Logger
public class l0 { // TODO: __DEBUG_REPLACE
    public static void a(String str, Throwable th) {
        b(k0.c, str, th);
        (new Exception()).printStackTrace();
    }

    public static void a(k0 k0Var, String str) {
        Log.d(k0Var.b, str);
        (new Exception()).printStackTrace();
    }

    public static void a(k0 k0Var, String str, Throwable th) {
        Log.d(k0Var.b, str);
        th.printStackTrace();
    }

    public static void a(k0 k0Var, Object... objArr) {
        Log.d(k0Var.b, Arrays.toString(objArr));
        (new Exception()).printStackTrace();
    }

    public static void b(String str) {
        b(k0.c, str);
    }

    public static void b(k0 k0Var, String str) {
        a(k0Var, str);
    }

    public static void b(k0 k0Var, String str, Throwable th) {
        a(k0Var, str, th);
    }

    public static void c(String str) {
        c(k0.c, str);
    }

    public static void c(k0 k0Var, String str) {
        a(k0Var, str);
    }

    public static void d(String str) {
        d(k0.c, str);
    }

    public static void d(k0 k0Var, String str) {
        a(k0Var, str);
    }

    public static void e(k0 k0Var, String str) {
        a(k0Var, str);
    }

    public static void f(String str) {
        f(k0.c, str);
    }

    public static void f(k0 k0Var, String str) {
        a(k0Var, str);
    }

    public static void a(String str) {
        a(k0.c, str);
    }

    public static void e(String str) {
        e(k0.c, str);
    }

    public static void a(Throwable th, String str) {
        a(str, th);
    }
}
