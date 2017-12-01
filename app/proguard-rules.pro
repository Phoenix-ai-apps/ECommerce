# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /home/angel/Documents/AJAY/SDK_Updated/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

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

-ignorewarnings

-keep class * {
    public private *;
}

-keepclassmembers class * {
        private *;
}


#Gson
-keepattributes Signature
-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }

# Models
-keep class com.demo.ecommerce.models.** { *; }


#butterknife
-dontwarn butterknife.internal.**
-keep class **$$ViewInjector { *; }
-keepnames class * { @butterknife.InjectView *;}

#All Activity
-keep public class * extends android.app.Activity
-keep public class * extends android.support.v7.app.AppCompatActivity


# For All extenal Libraries
-keep class org.** { *; }
-keep class android.** { *; }
-keep class java.** { *; }
-keep class butterknief.** { *; }
-keep class javax.** { *; }
-keep class io.** { *; }
-keep class io.protostuff.** { *; }
-keep class net.** { *; }
-keep class com.google.** { *; }
-keep class com.github.** { *; }
-keep class com.android.** { *; }
