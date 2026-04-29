package com.answufeng.utils

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue

@Deprecated(
    message = "Use Number.dpToPx(context) for correctness or Number.dp for simplicity",
    replaceWith = ReplaceWith("dp.dpToPx(this)")
)
fun Context.dp2px(dp: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()
}

@Deprecated(
    message = "Use Number.spToPx(context) for correctness or Number.sp for simplicity",
    replaceWith = ReplaceWith("sp.spToPx(this)")
)
fun Context.sp2px(sp: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, resources.displayMetrics).toInt()
}

@Deprecated(
    message = "Use Int.pxToDp(context) instead",
    replaceWith = ReplaceWith("px.pxToDp(this)")
)
fun Context.px2dp(px: Int): Float {
    return px / resources.displayMetrics.density
}

@Deprecated(
    message = "Use Int.pxToSp(context) instead",
    replaceWith = ReplaceWith("px.pxToSp(this)")
)
@Suppress("DEPRECATION")
fun Context.px2sp(px: Int): Float {
    return px / resources.displayMetrics.scaledDensity
}

/**
 * 将 dp 值转换为 px（四舍五入）。
 *
 * 使用 `Resources.getSystem()`，不依赖 Context。
 * 在多窗口/折叠屏场景下可能不准确，推荐使用 [dpToPx]。
 */
@AwExperimentalApi
val Number.dp: Int
    get() = (toFloat() * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

/**
 * 将 dp 值转换为 px（浮点数）。
 *
 * 使用 `Resources.getSystem()`，不依赖 Context。
 * 在多窗口/折叠屏场景下可能不准确。
 */
@AwExperimentalApi
val Number.dpF: Float
    get() = toFloat() * Resources.getSystem().displayMetrics.density

/**
 * 将 sp 值转换为 px（四舍五入）。
 *
 * 使用 `Resources.getSystem()`，不依赖 Context。
 * 在多窗口/折叠屏场景下可能不准确，推荐使用 [spToPx]。
 */
@Suppress("DEPRECATION")
@AwExperimentalApi
val Number.sp: Int
    get() = (toFloat() * Resources.getSystem().displayMetrics.scaledDensity + 0.5f).toInt()

/**
 * 将 sp 值转换为 px（浮点数）。
 *
 * 使用 `Resources.getSystem()`，不依赖 Context。
 * 在多窗口/折叠屏场景下可能不准确。
 */
@Suppress("DEPRECATION")
@AwExperimentalApi
val Number.spF: Float
    get() = toFloat() * Resources.getSystem().displayMetrics.scaledDensity

/**
 * 将 dp 值转换为 px（Context 感知，推荐使用）。
 *
 * 使用当前 Context 的 DisplayMetrics，多窗口/折叠屏安全。
 */
fun Number.dpToPx(context: Context): Int =
    (toFloat() * context.resources.displayMetrics.density + 0.5f).toInt()

/**
 * 将 sp 值转换为 px（Context 感知，推荐使用）。
 *
 * 使用当前 Context 的 DisplayMetrics，多窗口/折叠屏安全。
 */
@Suppress("DEPRECATION")
fun Number.spToPx(context: Context): Int =
    (toFloat() * context.resources.displayMetrics.scaledDensity + 0.5f).toInt()

/**
 * 将 px 值转换为 dp。
 */
fun Int.pxToDp(context: Context): Float =
    this / context.resources.displayMetrics.density

/**
 * 将 px 值转换为 sp。
 */
@Suppress("DEPRECATION")
fun Int.pxToSp(context: Context): Float =
    this / context.resources.displayMetrics.scaledDensity

/** 屏幕宽度（px）。API 30+ 使用 WindowMetrics，低版本回退 displayMetrics。 */
val Context.screenWidth: Int
    get() = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
        (getSystemService(android.view.WindowManager::class.java)?.currentWindowMetrics?.bounds?.width()?.toInt())
            ?: resources.displayMetrics.widthPixels
    } else {
        resources.displayMetrics.widthPixels
    }

/** 屏幕高度（px）。API 30+ 使用 WindowMetrics，低版本回退 displayMetrics。 */
val Context.screenHeight: Int
    get() = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
        (getSystemService(android.view.WindowManager::class.java)?.currentWindowMetrics?.bounds?.height()?.toInt())
            ?: resources.displayMetrics.heightPixels
    } else {
        resources.displayMetrics.heightPixels
    }

/** 状态栏高度（px）。 */
val Context.statusBarHeight: Int
    get() {
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0) resources.getDimensionPixelSize(resourceId) else 0
    }

/** 导航栏高度（px）。 */
val Context.navigationBarHeight: Int
    get() {
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (resourceId > 0) resources.getDimensionPixelSize(resourceId) else 0
    }

/** 判断当前是否为深色模式。 */
val Context.isDarkMode: Boolean
    get() = (resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK) ==
            android.content.res.Configuration.UI_MODE_NIGHT_YES

/** 判断外部存储是否可用（已挂载，含只读）。
 *
 * 注意：`Environment.getExternalStorageState()` 在 API 30+ 已废弃，
 * 推荐使用 `Environment.isExternalStorageLegacy()` 或分区存储 API。
 */
val Context.isExternalStorageAvailable: Boolean
    get() = android.os.Environment.getExternalStorageState() in
        setOf(android.os.Environment.MEDIA_MOUNTED, android.os.Environment.MEDIA_MOUNTED_READ_ONLY)

/** 判断外部存储是否可写（已挂载且非只读）。
 *
 * 注意：`Environment.getExternalStorageState()` 在 API 30+ 已废弃，
 * 推荐使用分区存储 API（`MediaStore` / `StorageManager`）。
 */
val Context.isExternalStorageWritable: Boolean
    get() = android.os.Environment.getExternalStorageState() == android.os.Environment.MEDIA_MOUNTED

/** 屏幕密度（如 2.0、3.0、3.5 等）。 */
val Context.screenDensity: Float
    get() = resources.displayMetrics.density

/** 屏幕密度 DPI（如 320、480、640 等）。 */
val Context.screenDensityDpi: Int
    get() = resources.displayMetrics.densityDpi

/** 是否为高密度屏幕（densityDpi >= 320，即 xhdpi 及以上）。 */
val Context.isHighDensity: Boolean
    get() = resources.displayMetrics.densityDpi >= 320
