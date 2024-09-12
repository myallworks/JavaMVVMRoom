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
# General rules to optimize code
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-dontpreverify
-dontobfuscate
-dontnote **

# Keep exception names
-keepattributes SourceFile,LineNumberTable


# Keep Parcelable
-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# Keep Room entities and DAOs
-keep class androidx.room.** { *; }
-keep interface androidx.room.* { *; }
-keep class your.package.name.db.** { *; }

# Keep schema and migration classes
-keep class androidx.sqlite.db.** { *; }

# Keep AndroidX libraries
-keep class androidx.** { *; }
-dontwarn androidx.**

# Keep Kotlin metadata
-keepclassmembers class kotlin.Metadata { *; }

# If you're using coroutines, you might want to keep the suspend functions
-keep class kotlinx.coroutines.** { *; }


# Keep log methods
-keep class android.util.Log { *; }

-assumenosideeffects class android.util.Log {
    public static int v(...);
    public static int d(...);
    public static int i(...);
    public static int w(...);
    public static int e(...);
}

# Glide rules
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public class * extends com.bumptech.glide.annotation.GlideExtension { *; }

# Gson rules
-keep class com.google.gson.** { *; }
-keep class your.package.name.models.** { *; }

# Keep fields in classes serialized/deserialized by Gson
-keepclassmembers class ** {
    @com.google.gson.annotations.SerializedName <fields>;
}


# Retrofit and OkHttp keep annotations
-keepattributes Signature
-keepattributes *Annotation*

# Retrofit models
-keep class your.package.name.models.** { *; }
-keep class retrofit2.Retrofit { *; }
-keep class retrofit2.converter.gson.GsonConverterFactory { *; }

# Keep native methods
-keepclassmembers class * { 
    native <methods>; 
}


# Keep application classes
-keep class your.package.name.** { *; }

# Keep specific classes like Activities, Services, etc.
-keep class * extends android.app.Activity
-keep class * extends android.app.Service
-keep class * extends android.content.BroadcastReceiver
-keep class * extends android.content.ContentProvider

# Keep entry points
-keep public class * extends android.app.Application
-keep public class * extends android.app.Instrumentation



