# EditTextExt — 文本变化监听 / 长度限制 / 小数过滤 / 键盘动作

> 源码：[EditTextExt.kt](../src/main/java/com/answufeng/utils/EditTextExt.kt)

## 文本变化监听

```kotlin
val watcher = editText.onTextChanged { text ->
    viewModel.onInputChanged(text)
}
editText.removeTextChangedListener(watcher)  // 移除
```

## 长度限制

```kotlin
editText.setMaxLength(50)  // 替换现有 LengthFilter
```

## 输入过滤器

```kotlin
editText.addFilter(customFilter)
editText.addDecimalFilter(2)  // 限制小数位数（如金额输入）
```

## 清除焦点并隐藏键盘

```kotlin
editText.clearFocusAndHideKeyboard()
```

## 键盘动作按钮

```kotlin
editText.setOnEditorAction(EditorInfo.IME_ACTION_SEARCH) {
    performSearch()
}
```

## 相关

- [ViewExt](view.md) — `EditText.showKeyboard()` / `View.hideKeyboard()`
- [KeyboardExt](keyboard.md) — 键盘可见性监听
