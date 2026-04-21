# aw-utils Consumer ProGuard Rules
# 此文件由库的使用者（宿主应用）使用，用于确保库中的类在混淆时不会被错误移除或重命名

# ===========================================================
# 公共 API 保留
# ===========================================================

# 精确保留公共 API 類型名稱，成員允許混淆以避免過度保留
-keep public class com.answufeng.utils.SpDelegate
-keep public class com.answufeng.utils.AwLog
-keep public class com.answufeng.utils.NetworkType
-keep public class com.answufeng.utils.PasswordStrength
-keep public class com.answufeng.utils.AwExperimentalApi



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

# 保留常用擴展函式承載類（避免 API 被誤刪）
-keep class com.answufeng.utils.*Kt { public *; }

# ===========================================================
# Kotlin 反射和属性委托
# ===========================================================

# 屬性委託類型由 Kotlin 標準庫提供，無需額外 keep

# ===========================================================
# 注解保留
# ===========================================================

# 僅保留本庫注解
-keepattributes *Annotation*
-keep class com.answufeng.utils.AwExperimentalApi { *; }

# Enum classes used in public API
-keep class com.answufeng.utils.NetworkType { *; }
-keep class com.answufeng.utils.PasswordStrength { *; }
