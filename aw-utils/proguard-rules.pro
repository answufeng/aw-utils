# aw-utils ProGuard rules (release build)
# Consumer-facing rules are in consumer-rules.pro

# ===========================================================
# 保留公共 API 和扩展函数
# ===========================================================

# 保留所有公共类
-keep class com.answufeng.utils.** { *; }

# 保留 Kotlin 反射和元数据
-keepattributes *Annotation*
-keepattributes RuntimeVisibleAnnotations
-keepattributes RuntimeInvisibleAnnotations
-keepattributes EnclosingMethod
-keepattributes InnerClasses
-keepattributes Signature
-keepattributes Exceptions
-keep class kotlin.Metadata { *; }

# ===========================================================
# 保留枚举和 sealed class
# ===========================================================

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# ===========================================================
# 保留扩展函数和 DSL 相关类
# ===========================================================

-keep class kotlin.jvm.functions.** { *; }
-keepclassmembers class kotlin.** { *; }
-keep class kotlin.properties.** { *; }

# ===========================================================
# 保留 Android 系统类
# ===========================================================

-keep class android.content.** { *; }
-keep class android.os.** { *; }
-keep class android.view.** { *; }
-keep class android.graphics.** { *; }
-keep class android.net.** { *; }
-keep class android.provider.** { *; }
-keep class android.text.** { *; }
-keep class android.util.** { *; }
-keep class android.widget.** { *; }

# ===========================================================
# 保留 Java 核心类
# ===========================================================

-keep class java.io.** { *; }
-keep class java.net.** { *; }
-keep class java.text.** { *; }
-keep class java.util.** { *; }
-keep class java.security.** { *; }
-keep class java.math.** { *; }
