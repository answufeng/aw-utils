# aw-utils Demo 功能矩阵

主界面 **Tab** → Fragment（工具栏菜单 **「演示清单」** 可列出下列 Tab）

| Tab | Fragment | 覆盖 |
|-----|----------|------|
| 字符串 | `StringFragment` | 校验、脱敏、编码等 |
| 日期 | `DateFragment` | 格式化、友好时间等 |
| 文件 | `FileFragment` | 安全删除、哈希等 |
| 设备 | `DeviceFragment` | 设备摘要等 |
| 网络 | `NetworkFragment` | 状态、类型（无网请手测） |
| 视图 | `ViewFragment` | 可见性、防抖等 |
| 富文本 | `SpanFragment` | Span DSL |
| 更多 | `MoreFragment` | 其它扩展 |

**弃用 API**（`AwLog`、`SpDelegate`）见 README「弃用 API 迁移」；新工程优先 **aw-log** / **aw-store**。工具栏 **「演示清单」** 可列出各 Tab。

## 推荐手测（边界与极端场景）

| 场景 | 建议操作 |
|------|----------|
| 无网 | 关数据网络走网络相关扩展，确认不崩溃 |
| 权限 | 需权限的 API 在未授权时的行为 |
| 线程 | 日期/格式化在多线程下压测（ThreadLocal 路径） |
| 弃用 | 确认 `@Deprecated` 提示与迁移文档一致 |
