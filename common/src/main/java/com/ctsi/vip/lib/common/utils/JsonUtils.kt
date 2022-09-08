package com.ctsi.vip.lib.common.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

/**
 * Created by GaoHW at 2022-6-28.
 *
 * Desc:
 */
object JsonUtils {

    private val gson: Gson = Gson()

    fun <T> toJson(obj: T): String = gson.toJson(obj)

    fun <T> fromJson(json: String, obj: Type): T = gson.fromJson(json, obj)

    fun <T> fromJson(json: String, obj: Class<T>): T = gson.fromJson(json, obj)

    fun <T> json2List(json: String): List<T> {
        return gson.fromJson(json, object : TypeToken<LinkedList<T>>() {}.type)
    }

    fun <T> list2Json(list: List<T>): String {
        return toJson(list)
    }
}