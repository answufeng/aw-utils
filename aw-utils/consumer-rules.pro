# aw-utils Consumer ProGuard Rules
# 此文件由库的使用者（宿主应用）使用，用于确保库中的类在混淆时不会被错误移除或重命名

# ===========================================================
# 公共 API 保留
# ===========================================================

# 保留所有公共扩展函数和属性
-keep class com.answufeng.utils.** { *; }

# 保留 @AwExperimentalApi 注解（用于 Opt-In 检查）
-keep class com.answufeng.utils.AwExperimentalApi { *; }

# ===========================================================
# SpDelegate 相关
# ===========================================================

# 保留 SpDelegate 及其子类的公共方法
-keep class com.answufeng.utils.SpDelegate { *; }

# ===========================================================
# AwLog 相关
# ===========================================================

# 保留 AwLog 枚举和公共方法
-keep class com.answufeng.utils.AwLog { *; }
-keepclassmembers class com.answufeng.utils.AwLog$Level { *; }

# ===========================================================
# 扩展函数和属性
# ===========================================================

# 保留扩展函数（混淆工具可能错误地移除未被直接调用的扩展）
-keepclassmembers class kotlin.jvm.functions.Function0 { *; }
-keepclassmembers class kotlin.jvm.functions.Function1 { *; }
-keepclassmembers class kotlin.jvm.functions.Function2 { *; }

# ===========================================================
# Android 相关
# ===========================================================

# 保留 View 扩展函数可能访问的内部类
-keep class android.view.View { *; }
-keep class android.content.Context { *; }

# 保留 SpannableString 相关类
-keep class android.text.SpannableString { *; }
-keep class android.text.Spanned { *; }

# 保留 Process 类相关方法（用于获取进程名）
-keep class android.os.Process { *; }

# ===========================================================
# Kotlin 反射和属性委托
# ===========================================================

# 保留 Kotlin 属性委托相关的 ReadWriteProperty 实现
-keep class kotlin.properties.ReadWriteProperty { *; }

# 保留 KProperty 相关的反射 API
-keep class kotlin.reflect.KProperty { *; }

# ===========================================================
# 注解保留
# ===========================================================

# 保留所有自定义注解
-keepattributes Annotation
-keep class * extends java.lang.annotation.Annotation { *; }

# Enum classes used in public API
-keep class com.answufeng.utils.NetworkType { *; }
-keep class com.answufeng.utils.PasswordStrength { *; }
