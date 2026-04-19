# aw-utils consumer ProGuard rules

# Extension function files (public API)
-keepclassmembers class com.answufeng.utils.** {
    public *;
}

# SpDelegate (deprecated, consumers may still use)
-keepclassmembers class * extends com.answufeng.utils.SpDelegate {
    <fields>;
}

# Experimental API annotation
-keep @interface com.answufeng.utils.AwExperimentalApi
-keep @com.answufeng.utils.AwExperimentalApi class * { *; }
-keepclassmembers class * {
    @com.answufeng.utils.AwExperimentalApi *;
}

# Enum classes used in public API
-keep class com.answufeng.utils.NetworkType { *; }
-keep class com.answufeng.utils.PasswordStrength { *; }

# SpDelegate property delegate (only aw-utils delegates, not all ReadWriteProperty)
-keepclassmembers class com.answufeng.utils.SpDelegate {
    *;
}

-keepattributes Signature, *Annotation*
