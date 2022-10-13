package com.ctsi.vip.lib.framework.http.response

/**
 * Created by GaoHW at 2022-6-27.
 *
 * Desc:
 */
class BeanResponse<T> {
    var code: String? = null
    var msg: String? = null
    var data: T? = null
}