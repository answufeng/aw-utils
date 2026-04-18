# aw-utils consumer ProGuard rules

-keepclassmembers class com.answufeng.utils.** {
    public *;
}

-keepclassmembers class * extends com.answufeng.utils.SpDelegate {
    <fields>;
}

-keep @interface com.answufeng.utils.AwExperimentalApi
