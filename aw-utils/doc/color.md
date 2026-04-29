# ColorExt — 颜色转换 / 透明度 / 混合

> 源码：[ColorExt.kt](../src/main/java/com/answufeng/utils/ColorExt.kt)

## 颜色 Int ↔ 十六进制

```kotlin
color.toHexColor()         // "#FF1F71B3"（含透明度）
color.toHexColorNoAlpha()  // "#1F71B3"（不含透明度）

"#FF0000".toColorInt()     // Color.RED
"#F00".toColorInt()        // 简写格式
"#80FF0000".toColorInt()   // 带透明度
```

> `toColorInt()` 支持 `#RGB`、`#ARGB`、`#RRGGBB`、`#AARRGGBB`（`#` 可选），解析失败返回 `null`。

## 透明度

```kotlin
color.withAlpha(128)  // 调整透明度（0-255）
```

## 颜色混合

```kotlin
val blended = Color.RED.blend(Color.BLUE, ratio = 0.5f)  // 红蓝各半
```

> `ratio` 范围 0.0-1.0：0.0 完全为当前颜色，1.0 完全为 color2。
