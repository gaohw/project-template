package com.ctsi.vip.lib.common.utils.upgrade

import com.blankj.utilcode.util.PathUtils
import java.io.File

/**
 * Class : Version
 * Create by GaoHW at 2022-7-6 17:13.
 * Description:
 */
internal class Version {

    var url: String? = null
    var code: String? = null
    var desc: String? = null
    var targetPath: String? = null

    var isForce: Boolean = false

    fun getUniqueKey(): String {
        return toString()
    }

    fun getRemoteUrl(): String {
        if (url?.startsWith("http") == true) {
            return url as String
        }
        return "http://$url"
    }

    fun getStoragePath(): String {
        if (targetPath.isNullOrEmpty()) {
            return "${PathUtils.getCachePathExternalFirst()}${File.separator}upgrade${File.separator}code"
        }
        return targetPath as String
    }
}