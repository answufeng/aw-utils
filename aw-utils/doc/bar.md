# BarExt — 状态栏 / 导航栏 样式与沉浸式

> 源码：[BarExt.kt](../src/main/java/com/answufeng/utils/BarExt.kt)

## 设置颜色与明暗

```kotlin
activity.setStatusBarColorAndStyle(Color.WHITE, darkStatusBarIcons = true)
activity.setNavigationBarColorAndStyle(Color.WHITE, darkNavigationBarIcons = true)
```

## 沉浸式（透明系统栏）

```kotlin
activity.transparentStatusBar()     // 透明状态栏
activity.transparentNavBar()        // 透明导航栏
activity.transparentSystemBars()    // 同时透明状态栏和导航栏
```

> 透明后内容延伸到系统栏下方，需在布局中处理 padding 以避免遮挡。

## 显示/隐藏

```kotlin
activity.hideStatusBar()
activity.showStatusBar()
activity.hideNavBar()
activity.showNavBar()
```

## 可见性判断

```kotlin
activity.isStatusBarVisible()   // Boolean
activity.isNavBarVisible()      // Boolean
```

> 通过 WindowInsets 检测系统栏高度判断可见性。
