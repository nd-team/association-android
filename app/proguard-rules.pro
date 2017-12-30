# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\huanghuanlai\java\android-sdk/tools/proguard/proguard-android.txt
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
-dontwarn
-optimizationpasses 5          # 指定代码的压缩级别
-dontusemixedcaseclassnames   # 是否使用大小写混合
-dontpreverify           # 混淆时是否做预校验
-verbose                # 混淆时是否记录日志
-ignorewarnings     # 抑制警告
-dontskipnonpubliclibraryclasses

#-applymapping mapping.txt
-keepattributes SourceFile,LineNumberTable,EnclosingMethod
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*  # 混淆时所采用的算法


-keep public class * extends android.app.ActivityManagerNative
-keep public class * extends android.app.ActivityManager
-keep public class * extends android.app.View      # 保持哪些类不被混淆
-keep public class * extends android.app.Activity      # 保持哪些类不被混淆
-keep public class * extends android.app.Application   # 保持哪些类不被混淆
-keep public class * extends android.app.Service       # 保持哪些类不被混淆
-keep public class * extends android.content.BroadcastReceiver  # 保持哪些类不被混淆
-keep public class * extends android.content.ContentProvider    # 保持哪些类不被混淆
-keep public class * extends android.app.backup.BackupAgentHelper # 保持哪些类不被混淆
-keep public class * extends android.preference.Preference        # 保持哪些类不被混淆
-keep public class com.android.vending.licensing.ILicensingService    # 保持哪些类不被混淆


#保持包不被混淆
#-keep class com.ike.sq.aliance.bean.** {*;}
#  -keep class com.ike.sq.aliance.utils.** {*;}
#  -keep class com.ike.sq.aliance.ui..**{*;}
#  -keep class com.ike.sq.aliance.base.**{*;}
#  -keep class com.ike.sq.aliance.db.**{*;}
#  -keep class com.ike.sq.aliance.app{*;}

-keepclasseswithmembernames class * {  # 保持 native 方法不被混淆
    native <methods>;
}
-keepclasseswithmembers class * {   # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {# 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.app.Activity { # 保持自定义控件类不被混淆
    public void *(android.view.View);
}
-keepclassmembers enum * {     # 保持枚举 enum 类不被混淆
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
    -keepnames class * implements android.os.Parcelable
-keep class * implements android.os.Parcelable { # 保持 Parcelable 不被混淆
    public static final android.os.Parcelable$Creator *;
}
 #保持 Serializable 不被混淆
    -keepnames class * implements java.io.Serializable
-keep class * implements android.os.Serializable {
    public static final android.os.Serializable$Creator *;
}
#保持 Serializable 不被混淆并且enum 类也不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
#如果引用了v4或者v7包
-dontwarn android.support.**
-dontwarn android.support.v4.**
-keep class android.support.** { *;}
-keep class android.support.v4.** { *; }
-keep public class * extends android.support.v4.**
-keep public class * extends android.app.Fragment


-dontwarn junit.framework.**
-keep class junit.framework.**{*;}
-dontwarn junit.runner.**
-keep class junt.runner.**{*;}
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}

-dontwarn okio.**
-keep class okio**{*;}

-keep class or.codehaus.**{*;}
-dontwarn or.codehaus.**
-keep class or.bouncycastle.**{*;}
-dontwarn or.bouncycastle.**

-dontwarn org.junit.**
-keep class org.junit.**{*;}


-dontwarn org.xutils.**
-keep org.xutils.**{*;}
