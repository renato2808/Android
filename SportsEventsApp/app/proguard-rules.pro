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

# For Hilt
-keep class * extends dagger.hilt.android.lifecycle.HiltViewModel

# For Hilt generated classes
-keep @dagger.hilt.android.lifecycle.HiltViewModel class *

# For Hilt modules
-keepclassmembers class ** {
    @dagger.hilt.InstallIn <methods>;
}

-keep @androidx.compose.runtime.Composable public interface * { public *; }

-keep public class androidx.compose.runtime.* { public *; }
-keep public class androidx.compose.ui.* { public *; }
-keep public class androidx.compose.material.* { public *; }
-keep public class androidx.compose.foundation.layout.* { public *; }

-keep public class * extends androidx.compose.ui.platform.AbstractComposeView {
    public *;
}

# For Room Database
-keep class androidx.room.** { *; }
-dontwarn androidx.room.**

-keep public class com.example.sportseventsapp.viewmodel.SportsEventsViewModel { public *; }

-keepclassmembers class * {
    @androidx.room.Dao <methods>;
}

-keepclassmembers class * {
    @androidx.room.Entity <fields>;
    @androidx.room.PrimaryKey <fields>;
}

-keepclassmembers class * extends androidx.room.RoomDatabase {
    public static ** databaseBuilder(android.content.Context, java.lang.Class, java.lang.String)
}