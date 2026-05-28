# aw-utils Consumer ProGuard Rules
# 此文件由库的使用者（宿主应用）使用，用于确保库中的类在混淆时不会被错误移除或重命名
#
# 设计说明：
# - `com.answufeng.utils.*Kt`：Kotlin 顶层扩展会生成 `*Kt` 承载类；保留 public 成员可减少
#   R8 删除「仅从 Java 或未内联路径引用」的入口方法时的风险。
# - kotlinx-coroutines 自带 consumer 规则，此处不重复。

# ===========================================================
# 公共 API 保留
# ===========================================================

-keep public class com.answufeng.utils.NetworkType { *; }
-keep public class com.answufeng.utils.PasswordStrength { *; }
-keep public class com.answufeng.utils.SystemSettings { *; }
-keep public class com.answufeng.utils.AwExperimentalApi { *; }
-keep public class com.answufeng.utils.BitmapFactoryOptionsCompat { *; }
-keep public class com.answufeng.utils.Rom { *; }

# ===========================================================
# 扩展函数和属性
# ===========================================================

-keep class com.answufeng.utils.*Kt { public *; }

# ===========================================================
# 注解保留
# ===========================================================

-keepattributes *Annotation*
-keep class com.answufeng.utils.AwExperimentalApi { *; }
