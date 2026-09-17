# Pochita LSPosed Template 🚀

现代、轻量、开箱即用的 **LSPosed 模块**脚手架模板。

基于 **LSPosed 规范 (API 102)** 与 **Jetpack Compose** 构建，采用单层扁平化架构，解耦业务代码与应用包名，无需复杂的反射即可快速孵化 LSPosed 模块。

---

## 🛠️ 技术栈与特性

- **模块规范**：LSPosed API 102 官方规范 (`io.github.libxposed`)
- **Hook 机制**：OkHttp 式链式拦截器（`hook.intercept { chain -> ... }`）+ 异常保护模式
- **热重载支持**：原生支持 LSPosed Hot Reload，模块更新免重启目标进程
- **界面与设计**：Jetpack Compose + Material 3 + Navigation 3
- **环境与构建**：[mise](https://mise.jdx.dev/) 统一环境管理 + AGP + Kotlin

---

## ⚡ 快速开始

### 1. 准备环境
确保本地已安装 [mise](https://mise.jdx.dev/)，在项目根目录执行：
```bash
mise install
```

### 2. 构建与安装
```bash
mise run debug    # 编译 Debug APK
mise run install  # 安装到已连接的手机或模拟器
mise run clean    # 清理构建缓存
```

---

## 🎯 如何基于本模板开发新模块？

### 1. 修改模块基础信息
- **修改应用桌面名称**：`app/src/main/res/values/strings.xml`
  ```xml
  <string name="app_name">你的模块名称</string>
  ```
- **修改模块包名**：`app/build.gradle.kts`
  ```kotlin
  defaultConfig {
      applicationId = "pochita.yourmodule"
  }
  ```

### 2. 配置 LSPosed 描述文件 (`app/src/main/resources/META-INF/xposed/`)
遵循 LSPosed 规范，统一在此目录下声明：
- **`scope.list`**：配置模块目标作用域包名（每行一个）：
  ```text
  com.android.settings
  com.android.systemui
  ```
- **`module.prop`**：模块基础属性：
  ```properties
  minApiVersion=102
  targetApiVersion=102
  defaultExceptionMode=protective
  staticScope=false
  autoHotReload=true
  ```
- **`java_init.list`**：Hook 入口类声明（默认为 `pochita.hook.MainHook`）。

---

## 📂 项目结构

```text
pochita-lsposed-template/
├── mise.toml                    # 工具链与自动化构建任务
├── settings.gradle.kts          # 依赖源配置
├── gradle/
│   └── libs.versions.toml       # Version Catalog (LSPosed 统一依赖版本)
└── app/
    ├── build.gradle.kts         # 模块构建配置 (minSdk 26, compileOnly API)
    └── src/main/
        ├── AndroidManifest.xml  # 应用清单 (自动由 service AAR 合并 XposedProvider)
        ├── resources/META-INF/xposed/
        │   ├── module.prop      # 模块属性与热重载配置
        │   ├── java_init.list   # Hook 入口类路径
        │   └── scope.list       # 目标作用域包名列表
        ├── res/                 # 资源文件 (图标、主题、字符串)
        └── java/pochita/
            ├── hook/            # 核心 Hook 业务层
            │   └── MainHook.kt  # LSPosed 模块入口实现
            ├── MainActivity.kt  # 模块设置页入口 Activity
            ├── Navigation.kt    # Navigation 3 页面路由
            └── ui/              # 模块管理与配置 UI (Compose)
```
