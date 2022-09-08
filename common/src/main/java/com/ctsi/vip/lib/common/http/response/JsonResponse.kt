package com.ctsi.vip.lib.common.http.response

import org.json.JSONObject

/**
 * Created by GaoHW at 2022-7-4.
 *
 * Desc:
 */
class JsonResponse : JSONObject {

    constructor() : super()

    constructor(json: String) : super(json)

    fun getCode(): String = optString("code", "")

    fun getMsg(): String? = optString("msg", "")
}