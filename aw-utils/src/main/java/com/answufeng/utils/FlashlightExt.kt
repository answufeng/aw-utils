package com.answufeng.utils

import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager

private fun Context.cameraManager(): CameraManager =
    getSystemService(Context.CAMERA_SERVICE) as CameraManager

private fun Context.flashCameraId(): String? {
    val cm = cameraManager()
    return cm.cameraIdList.firstOrNull { id ->
        cm.getCameraCharacteristics(id).get(CameraCharacteristics.FLASH_INFO_AVAILABLE) == true
    }
}

/**
 * 设备是否存在可用作手电筒的闪光灯（[CameraCharacteristics.FLASH_INFO_AVAILABLE]）。
 */
fun Context.isTorchSupported(): Boolean = flashCameraId() != null

/**
 * 打开或关闭手电筒（[CameraManager.setTorchMode]）。
 *
 * **权限**：标准 AOSP 上通常无需权限；部分厂商机型需声明 `android.permission.CAMERA` 或需在系统设置中授权相机，失败时返回 `false`。
 *
 * @return 是否未抛异常地完成调用（不表示硬件状态已稳定）
 */
fun Context.setTorchEnabled(enabled: Boolean): Boolean {
    val id = flashCameraId() ?: return false
    return try {
        cameraManager().setTorchMode(id, enabled)
        true
    } catch (_: Exception) {
        false
    }
}
