package pochita.hook

import android.util.Log
import io.github.libxposed.api.XposedInterface
import io.github.libxposed.api.XposedModule
import io.github.libxposed.api.XposedModuleInterface.HotReloadedParam
import io.github.libxposed.api.XposedModuleInterface.HotReloadingParam
import io.github.libxposed.api.XposedModuleInterface.ModuleLoadedParam
import io.github.libxposed.api.XposedModuleInterface.PackageReadyParam

/**
 * LSPosed (API 102) 模块核心入口类。
 *
 * 必须在 META-INF/xposed/java_init.list 中声明此完整类名。
 */
class MainHook : XposedModule() {

    companion object {
        private const val TAG = "PochitaHook"
    }

    /**
     * 模块被注入目标进程后的最初回调。
     */
    override fun onModuleLoaded(param: ModuleLoadedParam) {
        super.onModuleLoaded(param)
        log(Log.INFO, TAG, "Module loaded in process: ${param.processName}, isSystemServer: ${param.isSystemServer}")
    }

    /**
     * 当目标应用的类加载器就绪、准备实例化 Application 时回调。
     * 绝大部分业务 Hook 建议在此生命周期执行。
     */
    override fun onPackageReady(param: PackageReadyParam) {
        super.onPackageReady(param)
        log(Log.INFO, TAG, "Package ready: ${param.packageName}, classloader: ${param.classLoader}")

        // 1. 示例：跨进程读取模块在 UI 端保存的 RemotePreferences 配置 (只读)
        try {
            val prefs = getRemotePreferences("config")
            val isModuleEnabled = prefs.getBoolean("enable_hook", true)
            log(Log.DEBUG, TAG, "Config read: enable_hook=$isModuleEnabled")
            if (!isModuleEnabled) return
        } catch (t: Throwable) {
            log(Log.WARN, TAG, "RemotePreferences not available or failed to load: ${t.message}")
        }

        // 2. 示例：Hook 目标方法 (基于 OkHttp 式链式拦截器)
        // try {
        //     val targetClass = param.classLoader.loadClass("com.example.TargetClass")
        //     val targetMethod = targetClass.getDeclaredMethod("targetMethod", String::class.java)
        //
        //     hook(targetMethod)
        //         .setPriority(XposedInterface.PRIORITY_DEFAULT)
        //         .setExceptionMode(XposedInterface.ExceptionMode.PROTECTIVE)
        //         .intercept { chain ->
        //             val arg0 = chain.args.getOrNull(0)
        //             log(Log.DEBUG, TAG, "Before calling targetMethod with arg: $arg0")
        //
        //             // 执行原方法或下一个拦截器
        //             val result = chain.proceed()
        //
        //             log(Log.DEBUG, TAG, "After calling targetMethod, result: $result")
        //             result
        //         }
        // } catch (t: Throwable) {
        //     log(Log.ERROR, TAG, "Hook targetMethod failed", t)
        // }
    }

    /**
     * API 102 热重载生命周期回调：在旧模块代码中触发。
     * 返回 true 声明旧代码已准备好卸载，允许框架加载新版模块代码。
     */
    override fun onHotReloading(param: HotReloadingParam): Boolean {
        log(Log.INFO, TAG, "Module is hot reloading...")
        return true
    }

    /**
     * API 102 热重载生命周期回调：在新模块代码中触发。
     */
    override fun onHotReloaded(param: HotReloadedParam) {
        super.onHotReloaded(param)
        log(Log.INFO, TAG, "Module hot reloaded successfully!")
    }
}
