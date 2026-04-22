package com.answufeng.utils

import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * SharedPreferences 属性委托工具，以 Kotlin 属性语法读写 SP。
 *
 * ### 定义
 * ```kotlin
 * object AppPrefs : SpDelegate("app_prefs") {
 *     var token by string("token", "")
 *     var userId by int("user_id", 0)
 *     var isFirstLaunch by boolean("first_launch", true)
 * }
 * ```
 *
 * ### 初始化（Application.onCreate 中调用一次）
 * ```kotlin
 * AppPrefs.init(applicationContext)
 * ```
 *
 * ### 读写
 * ```kotlin
 * AppPrefs.token = "abc123"
 * val t = AppPrefs.token   // "abc123"
 * ```
 *
 * > **注意**：未调用 [init] 就访问属性会抛出 [IllegalStateException]。
 *
 * @param name SharedPreferences 文件名
 */
@Deprecated(
    message = "推荐使用 aw-store 的 MmkvDelegate / AwStore（MMKV），详见 aw-utils README「弃用 API 迁移」",
    level = DeprecationLevel.WARNING
)
open class SpDelegate(private val name: String) {

    @Volatile
    private lateinit var sp: SharedPreferences

    val isInitialized: Boolean get() = ::sp.isInitialized

    fun init(context: Context) {
        sp = context.applicationContext.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    private fun ensureInitialized() {
        check(::sp.isInitialized) {
            "SpDelegate '$name' 尚未初始化，请先调用 init(context)"
        }
    }

    fun clear() {
        ensureInitialized()
        sp.edit().clear().apply()
    }

    fun remove(key: String) {
        ensureInitialized()
        sp.edit().remove(key).apply()
    }

    /**
     * 批量编辑 SharedPreferences，所有操作在同一个事务中提交。
     *
     * ```kotlin
     * AppPrefs.edit {
     *     putString("token", "new_token")
     *     putInt("user_id", 123)
     *     remove("temp_key")
     * }
     * ```
     */
    fun edit(block: SharedPreferences.Editor.() -> Unit) {
        ensureInitialized()
        sp.edit().apply(block).apply()
    }

    fun string(key: String, default: String = "") = object : ReadWriteProperty<Any?, String> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): String {
            ensureInitialized()
            return sp.getString(key, default) ?: default
        }
        override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
            ensureInitialized()
            sp.edit().putString(key, value).apply()
        }
    }

    fun int(key: String, default: Int = 0) = object : ReadWriteProperty<Any?, Int> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): Int {
            ensureInitialized()
            return sp.getInt(key, default)
        }
        override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
            ensureInitialized()
            sp.edit().putInt(key, value).apply()
        }
    }

    fun long(key: String, default: Long = 0L) = object : ReadWriteProperty<Any?, Long> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): Long {
            ensureInitialized()
            return sp.getLong(key, default)
        }
        override fun setValue(thisRef: Any?, property: KProperty<*>, value: Long) {
            ensureInitialized()
            sp.edit().putLong(key, value).apply()
        }
    }

    fun float(key: String, default: Float = 0f) = object : ReadWriteProperty<Any?, Float> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): Float {
            ensureInitialized()
            return sp.getFloat(key, default)
        }
        override fun setValue(thisRef: Any?, property: KProperty<*>, value: Float) {
            ensureInitialized()
            sp.edit().putFloat(key, value).apply()
        }
    }

    fun boolean(key: String, default: Boolean = false) = object : ReadWriteProperty<Any?, Boolean> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): Boolean {
            ensureInitialized()
            return sp.getBoolean(key, default)
        }
        override fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) {
            ensureInitialized()
            sp.edit().putBoolean(key, value).apply()
        }
    }

    /**
     * Set<String> 类型属性委托。
     *
     * 注意：Android 的 `getStringSet` 返回的是内部引用，
     * 直接修改该集合可能导致存储异常。本委托在读取时返回防御性拷贝。
     *
     * @param key SP 键名
     * @param default 默认值，默认为空集合
     */
    fun stringSet(key: String, default: Set<String> = emptySet()) = object : ReadWriteProperty<Any?, Set<String>> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): Set<String> {
            ensureInitialized()
            return sp.getStringSet(key, default)?.toSet() ?: default
        }
        override fun setValue(thisRef: Any?, property: KProperty<*>, value: Set<String>) {
            ensureInitialized()
            sp.edit().putStringSet(key, LinkedHashSet(value)).apply()
        }
    }
}
