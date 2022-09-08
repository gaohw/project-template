package com.ctsi.vip.lib.common.utils.upgrade

/**
 * Class : VersionUpgradeListener
 * Create by GaoHW at 2022-7-6 17:16.
 * Description:
 */
interface VersionUpgradeListener {

    fun onUpgradeFailed(cause: String?) {

    }

    fun onUpgradeProgress(curCount: Long, totalCount: Long) {

    }
}