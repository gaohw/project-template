package com.ctsi.vip.lib.framework.utils

import com.ctsi.vip.lib.framework.http.response.BeanResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.StringReader
import java.lang.reflect.Type

/**
 * Created by GaoHW at 2022-6-28.
 *
 * Desc:
 */
object JsonUtils {

    val gson: Gson = Gson()

    fun <T> toJson(obj: T): String = gson.toJson(obj)

    fun <T> list2Json(list: List<T>): String = toJson(list)

    fun <T> fromJson(json: String, obj: Type): T = gson.fromJson(json, obj)

    fun <T> fromJson(json: String, obj: Class<T>): T = gson.fromJson(json, obj)

    inline fun <reified T> jsonToBeanResponse(json: String?): BeanResponse<T>? {
        if (json == null) {
            return null
        }
        val typeBean = object : TypeToken<BeanResponse<T>>() {}.type
        val typeT = object : TypeToken<T>() {}.type

        val response = fromJson<BeanResponse<T>>(json, typeBean)
        val dataJson = toJson(response.data)

        return response.apply {
            data = gson.fromJsonWithoutT<T>(dataJson, typeT)
        }
    }

    inline fun <reified T> jsonToBean(json: String?): T? {
        if (json == null) {
            return null
        }
        return gson.fromJsonWithoutT<T>(json, object : TypeToken<T>() {}.type)
    }

    inline fun <reified T> Gson.fromJsonWithoutT(json: String?, typeOfT: Type): T? {
        if (json == null) {
            return null
        }
        val reader = StringReader(json)
        return fromJson(reader, typeOfT)
    }
}