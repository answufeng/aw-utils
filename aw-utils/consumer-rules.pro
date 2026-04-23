# aw-utils Consumer ProGuard Rules
# 此文件由库的使用者（宿主应用）使用，用于确保库中的类在混淆时不会被错误移除或重命名
#
# 设计说明：
# - 保留少数「宿主可能用反射或 JNI 按类名查找」的类（SpDelegate、AwLog、枚举等）。
# - `com.answufeng.utils.*Kt`：Kotlin 顶层扩展会生成 `*Kt` 承载类；保留 public 成员可减少
#   R8 删除「仅从 Java 或未内联路径引用」的入口方法时的风险；未使用 `-keepclassmembers class * { *; }` 以免过度保留实现细节。
# - kotlinx-coroutines 自带 consumer 规则，此处不重复。

# ===========================================================
# 公共 API 保留
# ===========================================================

# 精确保留公共 API 类名称，成员允许混淆以避免过度保留
-keep public class com.answufeng.utils.SpDelegate
-keep public class com.answufeng.utils.AwLog
-keep public class com.answufeng.utils.NetworkType { *; }
-keep public class com.answufeng.utils.PasswordStrength { *; }
-keep public class com.answufeng.utils.AwExperimentalApi { *; }
-keep public class com.answufeng.utils.BitmapFactoryOptionsCompat { *; }
-keep public class com.answufeng.utils.Rom { *; }

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

# 保留常用扩展函数承载类（避免 API 被误删）
-keep class com.answufeng.utils.*Kt { public *; }

# ===========================================================
# 注解保留
# ===========================================================

# 仅保留本库注解
-keepattributes *Annotation*
-keep class com.answufeng.utils.AwExperimentalApi { *; }
