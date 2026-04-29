# MetaDataExt — Manifest meta-data 读取

> 源码：[MetaDataExt.kt](../src/main/java/com/answufeng/utils/MetaDataExt.kt)

## Application meta-data

```kotlin
context.getApplicationMetaDataString("api_key")      // String?
context.getApplicationMetaDataInt("version", 0)       // Int
context.getApplicationMetaDataBoolean("debug", false) // Boolean
```

## Activity meta-data

```kotlin
activity.getActivityMetaDataString("theme")  // String?
```

## Service / Receiver meta-data

```kotlin
context.getServiceMetaDataString<MyService>("key")
context.getReceiverMetaDataString<MyReceiver>("key")
// 或指定 Class
context.getServiceMetaDataString(MyService::class.java, "key")
```

> 所有方法在异常时返回默认值 / null，不会崩溃。
