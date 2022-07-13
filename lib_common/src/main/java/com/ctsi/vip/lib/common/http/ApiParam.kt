package com.ctsi.vip.lib.common.http

import com.ctsi.vip.lib.common.utils.JsonUtil
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.Serializable
import java.util.*

class ApiParam : Serializable {

    private val params = TreeMap<String, Any>()

    companion object {
        fun create(): ApiParam {
            return ApiParam()
        }
    }

    fun addParam(key: String, value: String): ApiParam {
        params[key] = value
        return this
    }

    fun addParam(key: String, value: JSONArray): ApiParam {
        params[key] = value
        return this
    }

    fun addParam(key: String, value: Int): ApiParam {
        params[key] = value
        return this
    }

    fun addParam(key: String, value: Boolean): ApiParam {
        params[key] = value
        return this
    }


    fun addParam(key: String, value: Long): ApiParam {
        params[key] = value
        return this
    }


    fun addParam(key: String, value: Double): ApiParam {
        params[key] = value
        return this
    }

    fun addParam(key: String, value: File): ApiParam {
        params[key] = value
        return this
    }


    fun addParam(key: String, value: ArrayList<File>): ApiParam {
        params[key] = value
        return this
    }

    fun addParam(key: String, value: LongArray): ApiParam {
        params[key] = value
        return this
    }

    fun addParam(key: String, value: IntArray): ApiParam {
        params[key] = value
        return this
    }

    fun addParam(key: String, value: Enum<*>): ApiParam {
        params[key] = value
        return this
    }

    fun addParam(key: String, value: Map<String, Any>): ApiParam {
        params[key] = value
        return this
    }

    fun addParam(key: String, value: JSONObject): ApiParam {
        params[key] = value
        return this
    }

    fun getParams(): Map<String, Any> = params

    fun getBody(): RequestBody {
        return JsonUtil.toJson(params).toRequestBody("application/json; charset=utf-8".toMediaType())
    }
}