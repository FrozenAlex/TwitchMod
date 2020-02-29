# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-keep class okhttp3.** { *; }

# Twitch
-keep class tv.twitch.** { *; }
-keep class com.google.android.material.snackbar.** { *; }
-keep class com.google.android.exoplayer2.** { *; }

# FakeLibs
-keep class io.branch.** { *; }
-keep class com.amazon.** { *; }
-keep class com.google.android.gms.ads.** { *; }

-keep class androidx.appcompat.app.AppCompatActivity { *; }
