# Pochita Android Template 🚀

现代、轻量、开箱即用的 Android 原生开发脚手架模板。

基于最新的 Android 技术栈构建，采用单层扁平化目录架构，彻底解耦代码实现与应用包名，适合作为个人快速孵化新 App 的母版工程。

---

## 🛠️ 技术栈与环境

- **运行时环境**：通过 [mise](https://mise.jdx.dev/) 统一管理
- **构建工具链**：Android Gradle Plugin (AGP) + Kotlin + Gradle Toolchains
- **界面与设计**：Jetpack Compose + Material 3
- **路由导航**：[Navigation 3](https://developer.android.com/guide/navigation)
- **架构模式**：MVI / MVVM（ViewModel + StateFlow + Repository 数据层）

---

## ⚡ 快速开始

### 1. 准备环境
确保本地已安装 [mise](https://mise.jdx.dev/)，进入项目后执行：
```bash
mise install
```

### 2. 构建与运行
```bash
mise run debug    # 编译 Debug APK
mise run install  # 安装到已连接的手机或模拟器
mise run clean    # 清理构建缓存
```

---

## 🎯 如何使用本模板开发新应用？

使用该模板开发新应用时，**内部代码逻辑与包名无需任何全局替换**，只需 2 步：

1. **修改应用桌面名称**：  
   打开 `app/src/main/res/values/strings.xml`：
   ```xml
   <string name="app_name">你的新应用名称</string>
   ```

2. **修改应用包名**：  
   打开 `app/build.gradle.kts`：
   ```kotlin
   defaultConfig {
       applicationId = "pochita.yourapp"
   }
   ```

---

## 📂 项目结构

```text
pochita-android-template/
├── mise.toml                    # 工具链与自动化构建任务配置
├── settings.gradle.kts          # 依赖源与项目级配置
├── gradle/
│   ├── libs.versions.toml       # Version Catalog 统一依赖管理
│   └── wrapper/                 # Gradle Wrapper 运行时
└── app/
    ├── build.gradle.kts         # 模块级构建脚本
    └── src/main/
        ├── AndroidManifest.xml  # 应用清单文件
        ├── res/                 # 资源文件 (图标、字符串、主题)
        └── java/pochita/        # 核心源码
            ├── MainActivity.kt  # 入口 Activity
            ├── Navigation.kt    # Navigation 3 页面路由
            ├── NavigationKeys.kt# 路由 Key 声明
            ├── data/            # 数据仓库层
            ├── theme/           # Material 3 主题
            └── ui/main/         # 页面 UI (MainScreen) 与 ViewModel
```
