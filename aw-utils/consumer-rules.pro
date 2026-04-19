# aw-utils consumer ProGuard rules

-keepclassmembers class com.answufeng.utils.** {
    public *;
}

-keepclassmembers class * extends com.answufeng.utils.SpDelegate {
    <fields>;
}

-keep @interface com.answufeng.utils.AwExperimentalApi
-keep @com.answufeng.utils.AwExperimentalApi class * { *; }
-keepclassmembers class * {
    @com.answufeng.utils.AwExperimentalApi *;
}

-keep class com.answufeng.utils.NetworkType { *; }
-keep class com.answufeng.utils.PasswordStrength { *; }

-keepclassmembers class * implements kotlin.properties.ReadWriteProperty {
    *;
}

-keepattributes Signature, *Annotation*
