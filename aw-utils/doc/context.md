# ContextExt — dp/sp 转换 / 屏幕尺寸 / 深色模式 / 外部存储 / 屏幕密度

> 源码：[ContextExt.kt](../src/main/java/com/answufeng/utils/ContextExt.kt)

## 尺寸转换

### Context 感知（推荐）

```kotlin
100.dpToPx(context)   // dp → px
14.spToPx(context)    // sp → px
48.pxToDp(context)    // px → dp
48.pxToSp(context)    // px → sp
```

> 多窗口/折叠屏安全，使用当前 Context 的 DisplayMetrics。

### 无 Context（实验性）

```kotlin
@OptIn(AwExperimentalApi::class)
val px = 100.dp     // 使用 Resources.getSystem()
val pxF = 100.dpF   // Float 版本
val spx = 14.sp
val spxF = 14.spF
```

> ⚠️ 使用 `Resources.getSystem()`，多窗口/折叠屏场景下可能不准确。

## 屏幕尺寸

```kotlin
context.screenWidth          // 屏幕宽度（px）
context.screenHeight         // 屏幕高度（px）
context.statusBarHeight      // 状态栏高度（px）
context.navigationBarHeight  // 导航栏高度（px）
```

> API 30+ 使用 `WindowMetrics` 获取屏幕尺寸，低版本回退 `displayMetrics`。

## 深色模式

```kotlin
context.isDarkMode  // Boolean
```

## 外部存储

```kotlin
context.isExternalStorageAvailable  // 已挂载（含只读）
context.isExternalStorageWritable   // 可读写
```

> ⚠️ `Environment.getExternalStorageState()` 在 API 30+ 已废弃，推荐使用分区存储 API（`MediaStore` / `StorageManager`）。

## 屏幕密度

```kotlin
context.screenDensity      // 2.0、3.0、3.5 等
context.screenDensityDpi   // 320、480、640 等
context.isHighDensity      // densityDpi >= 320（xhdpi 及以上）
```

## 已弃用 API

| 弃用 API | 替代方案 |
|----------|----------|
| `Context.dp2px()` | `Number.dpToPx(context)` |
| `Context.sp2px()` | `Number.spToPx(context)` |
| `Context.px2dp()` | `Int.pxToDp(context)` |
| `Context.px2sp()` | `Int.pxToSp(context)` |

## 相关

- [ScreenExt](screen.md) — 屏幕亮度 / 常亮 / 横竖屏
- [ViewExt](view.md) — View 尺寸设置
