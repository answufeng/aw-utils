package com.answufeng.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build

/**
 * 判断指定包名的应用是否已安装。
 */
fun Context.isAppInstalled(packageName: String): Boolean {
    return try {
        packageManager.getApplicationInfoCompat(packageName, 0)
        true
    } catch (_: PackageManager.NameNotFoundException) {
        false
    }
}

/**
 * 判断当前应用是否为 Debug 构建。
 */
fun Context.isAppDebug(): Boolean {
    return try {
        val info = packageManager.getApplicationInfoCompat(packageName, 0)
        (info.flags and android.content.pm.ApplicationInfo.FLAG_DEBUGGABLE) != 0
    } catch (_: Exception) {
        false
    }
}

/**
 * 判断指定包名的应用是否为 Debug 构建。
 */
fun Context.isAppDebug(packageName: String): Boolean {
    return try {
        val info = packageManager.getApplicationInfoCompat(packageName, 0)
        (info.flags and android.content.pm.ApplicationInfo.FLAG_DEBUGGABLE) != 0
    } catch (_: Exception) {
        false
    }
}

/**
 * 启动指定包名的应用。
 *
 * @return 是否成功启动。如果目标应用未安装或没有启动入口，返回 `false`。
 */
fun Context.launchApp(packageName: String): Boolean {
    val intent = packageManager.getLaunchIntentForPackage(packageName) ?: return false
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    return safeStartActivity(intent)
}

/**
 * 获取指定包名应用的图标。
 */
fun Context.getAppIcon(packageName: String): Drawable? {
    return try {
        packageManager.getApplicationIcon(packageName)
    } catch (_: Exception) {
        null
    }
}

/**
 * 获取指定包名应用的应用名。
 */
fun Context.getAppName(packageName: String): String {
    return try {
        val info = packageManager.getApplicationInfo(packageName, 0)
        packageManager.getApplicationLabel(info).toString()
    } catch (_: Exception) {
        ""
    }
}

/**
 * 打开指定包名应用的应用详情设置页。
 *
 * @return 是否成功打开设置页
 */
fun Context.openAppDetailSettings(packageName: String = this.packageName): Boolean {
    val intent = Intent(
        android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    )
    return safeStartActivity(intent)
}

/**
 * 判断当前应用是否在前台运行。
 *
 * 注意：Android 10+ `runningAppProcesses` 返回信息受限，
 * 此方法在高版本系统上可能不可靠。
 * 推荐使用 `ProcessLifecycleOwner` 方案替代。
 *
 * 需要 `GET_TASKS` 权限（API 21 以下），API 21+ 使用 ActivityLifecycleCallbacks 更可靠。
 */
@Deprecated(
    message = "Android 10+ runningAppProcesses 受限，推荐使用 ProcessLifecycleOwner 方案替代",
    level = DeprecationLevel.WARNING
)
fun Context.isAppForeground(): Boolean {
    val am = getSystemService(Context.ACTIVITY_SERVICE) as? android.app.ActivityManager
        ?: return false
    val info = am.runningAppProcesses ?: return false
    return info.any {
        it.processName == packageName && it.importance == android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
    }
}

/**
 * 判断指定包名的应用是否为系统应用。
 */
fun Context.isSystemApp(packageName: String): Boolean {
    return try {
        val info = packageManager.getApplicationInfoCompat(packageName, 0)
        (info.flags and android.content.pm.ApplicationInfo.FLAG_SYSTEM) != 0
    } catch (_: Exception) {
        false
    }
}

/**
 * 获取应用签名证书的 SHA-1 指纹（40 位小写十六进制，无冒号分隔）。
 *
 * 与 `keytool -list -v` 中 **SHA1** 指纹的十六进制部分一致（仅大小写可能不同）。
 */
fun Context.getAppSignatureSHA1(packageName: String = this.packageName): String {
    return try {
        val info = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            packageManager.getPackageInfoCompat(packageName, PackageManager.GET_SIGNING_CERTIFICATES)
                .signingInfo
        } else {
            @Suppress("DEPRECATION")
            packageManager.getPackageInfoCompat(packageName, PackageManager.GET_SIGNATURES).signatures
        }
        val signatures = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            (info as android.content.pm.SigningInfo).apkContentsSigners
        } else {
            @Suppress("DEPRECATION", "UNCHECKED_CAST")
            info as Array<android.content.pm.Signature>
        }
        signatures.firstOrNull()?.let { sig ->
            java.security.MessageDigest.getInstance("SHA-1").digest(sig.toByteArray()).toHexString()
        } ?: ""
    } catch (_: Exception) {
        ""
    }
}

/**
 * 获取应用版本名。
 */
fun Context.getAppVersionName(packageName: String = this.packageName): String {
    return try {
        packageManager.getPackageInfoCompat(packageName, 0).versionName ?: ""
    } catch (_: PackageManager.NameNotFoundException) {
        ""
    }
}

/**
 * 获取应用版本号。
 */
fun Context.getAppVersionCode(packageName: String = this.packageName): Long {
    return try {
        val info = packageManager.getPackageInfoCompat(packageName, 0)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            info.longVersionCode
        } else {
            @Suppress("DEPRECATION")
            info.versionCode.toLong()
        }
    } catch (_: PackageManager.NameNotFoundException) {
        0L
    }
}

/**
 * 获取应用最小 SDK 版本。
 */
fun Context.getAppMinSdkVersion(packageName: String = this.packageName): Int {
    return try {
        val info = packageManager.getApplicationInfoCompat(packageName, 0)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            info.minSdkVersion
        } else {
            0
        }
    } catch (_: Exception) {
        0
    }
}

/**
 * 获取应用目标 SDK 版本。
 */
fun Context.getAppTargetSdkVersion(packageName: String = this.packageName): Int {
    return try {
        val info = packageManager.getApplicationInfoCompat(packageName, 0)
        info.targetSdkVersion
    } catch (_: Exception) {
        0
    }
}
