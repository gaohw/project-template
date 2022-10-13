package com.ctsi.vip.lib.framework.utils

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.ctsi.vip.lib.framework.integration.ConfigModule


/**
 * Class : ManifestParser
 * Create by GaoHW at 2022-10-13 10:20.
 * Description:
 */
class ManifestParser constructor(val context: Context?) {
    private val MODULE_VALUE = "ConfigModule"

    private fun parseModule(className: String): ConfigModule {
        val clazz: Class<*> = try {
            Class.forName(className)
        } catch (e: ClassNotFoundException) {
            throw IllegalArgumentException("Unable to find ConfigModule implementation", e)
        }
        val module: Any = try {
            clazz.newInstance()
        } catch (e: InstantiationException) {
            throw RuntimeException("Unable to instantiate ConfigModule implementation for $clazz", e)
        } catch (e: IllegalAccessException) {
            throw RuntimeException("Unable to instantiate ConfigModule implementation for $clazz", e)
        }
        if (module !is ConfigModule) {
            throw RuntimeException("Expected instanceof ConfigModule, but found: $module")
        }
        return module
    }

    fun parse(): List<ConfigModule> {
        val modules = mutableListOf<ConfigModule>()
        try {
            val appInfo: ApplicationInfo? = context?.packageManager
                ?.getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
            if (appInfo?.metaData != null) {
                for (key in appInfo.metaData.keySet()) {
                    if (MODULE_VALUE == appInfo.metaData[key]) {
                        modules.add(parseModule(key))
                    }
                }
            }
        } catch (e: PackageManager.NameNotFoundException) {
            throw RuntimeException("Unable to find metadata to parse ConfigModule", e)
        }
        return modules
    }
}