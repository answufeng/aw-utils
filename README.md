# aw-utils

Android utility extension library. Zero business logic, pure Kotlin extensions covering strings, dates, files, network, device, encoding, and more.

## Installation

Add the dependency in your module-level `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.answufeng:aw-utils:1.0.0")
}
```

Make sure you have the JitPack repository in your root `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}
```

## Features

- StringExt: Phone/email/ID card validation, masking, MD5/SHA-256
- DateExt: Format, parse, friendly time, date comparison
- FileExt: Size formatting, MD5/SHA-256, safe delete, stream write
- NetworkExt: Network state check, Flow-based state observation
- DeviceExt: Brand, model, OS version
- ContextExt: dp/sp conversion, screen dimensions
- EncodeExt: Base64, Hex encoding/decoding
- SystemExt: Clipboard, keyboard, Toast, app info, system intents
- ViewExt: Throttle click, visibility control
- ActivityExt: Generic start, safe extras
- CollectionExt: ifNotEmpty, safeJoinToString

## Usage

```kotlin
// String validation & masking
"13812345678".isPhoneNumber()  // true
"13812345678".maskPhone()       // 138****5678
"test@mail.com".isEmail()      // true

// Date formatting
System.currentTimeMillis().toFriendlyTime()  // "3 minutes ago"

// File operations
file.md5()
file.friendlySize()  // "1.5 MB"

// Network
context.isNetworkAvailable()
context.observeNetworkState().collect { /* reactive */ }

// Device info
deviceBrand   // "Xiaomi"
deviceModel   // "MI 11"

// Throttle click
view.onClick(interval = 500) { /* handle */ }
```

## License

Apache License 2.0. See [LICENSE](LICENSE) for details.
