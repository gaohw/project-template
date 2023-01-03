package com.ctsi.android.lib.im.bean

/**
 * Class : GroupBean
 * Create by GaoHW at 2023-1-3 14:03.
 * Description:
 */
class GroupBean {
    var groupId: String? = null
    var groupName: String? = null
    var members: List<UserBean>? = null
    var memberCount: Int = 0
}