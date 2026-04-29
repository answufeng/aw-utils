# EncodeExt — Base64 / Hex / URL / HTML 编解码

> 源码：[EncodeExt.kt](../src/main/java/com/answufeng/utils/EncodeExt.kt)

## Base64

```kotlin
"hello".encodeBase64()                    // 编码
"aGVsbG8=".decodeBase64()                 // 解码为 ByteArray
"aGVsbG8=".decodeBase64ToString()         // 解码为 String

byteArrayOf(0x0A, 0xFF.toByte()).toBase64()
```

> 默认使用 `Base64.NO_WRAP` 标志位，可通过 `flags` 参数自定义。

## Hex

```kotlin
byteArrayOf(0x0F, 0xAB.toByte()).toHexString()  // "0fab"（查表法高性能）
"0fab".hexToByteArray()                          // ByteArray
```

> `hexToByteArray()` 在长度非偶数或包含非法字符时抛出 `IllegalArgumentException`。

## URL 编解码

```kotlin
"hello world".urlEncode()       // "hello+world"
"你好".urlEncode()              // "%E4%BD%A0%E5%A5%BD"
"hello+world".urlDecode()       // "hello world"
"%E4%BD%A0%E5%A5%BD".urlDecode() // "你好"
```

> 默认 UTF-8 编码，可通过 `charset` 参数指定。

## HTML 编解码

```kotlin
"<b>hi</b>".htmlEncode()        // "&lt;b&gt;hi&lt;/b&gt;"
"a & b".htmlEncode()            // "a &amp; b"
"&lt;b&gt;".htmlDecode()        // "<b>"
"&amp;".htmlDecode()            // "&"
```

> `htmlEncode()` 使用 `TextUtils.htmlEncode()`；`htmlDecode()` 使用 `Html.fromHtml()`，支持 Named entity 和 Numeric entity。

## 已弃用 API

| 弃用 API | 替代方案 |
|----------|----------|
| `String.decodeBase64String()` | `decodeBase64ToString()` |
