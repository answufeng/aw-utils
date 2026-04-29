# KeyboardExt — 软键盘可见性检测与监听

> 源码：[KeyboardExt.kt](../src/main/java/com/answufeng/utils/KeyboardExt.kt)

## 判断键盘是否可见

```kotlin
view.isKeyboardVisible()              // 默认阈值 200dp
view.isKeyboardVisible(threshold = 250)

activity.isKeyboardVisible()
fragment.isKeyboardVisible()
```

## 监听键盘变化

```kotlin
val removeListener = view.observeKeyboardVisibility { visible ->
    // visible: Boolean
}
removeListener()  // 取消监听

activity.observeKeyboardVisibility { visible -> ... }
fragment.observeKeyboardVisibility { visible -> ... }
```

> 返回取消监听的函数，应在 `onDestroyView` 中调用。

## 原理

通过 `ViewTreeObserver.OnGlobalLayoutListener` 监听布局变化，比较屏幕可用高度与根视图高度差值判断键盘是否弹出。

## 相关

- [ViewExt](view.md) — `EditText.showKeyboard()` / `View.hideKeyboard()`
- [SystemExt](system.md) — 系统级工具
