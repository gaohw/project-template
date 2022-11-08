package com.ctsi.vip.lib.framework.http.response

/**
 * Class : PageResponse
 * Create by GaoHW at 2022-11-3 14:23.
 * Description: 分页查询返回体封装
 */
class PageResponse<T> {

    constructor()

    constructor(page: Int) {
        this.page = page
    }

    var data: ArrayList<T>? = null
    var page: Int = 0
    var total: Int = 0
}