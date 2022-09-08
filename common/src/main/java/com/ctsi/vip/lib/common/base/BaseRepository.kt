package com.ctsi.vip.lib.common.base

import com.ctsi.vip.lib.common.http.ApiService
import com.ctsi.vip.lib.common.http.response.BeanResponse
import com.ctsi.vip.lib.common.http.HttpConstants
import com.ctsi.vip.lib.common.http.RetrofitManager
import com.ctsi.vip.lib.common.http.response.JsonResponse
import com.ctsi.vip.lib.common.utils.JsonUtils
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Call

/**
 * Created by GaoHW at 2022-6-27.
 *
 * Desc:
 */
open class BaseRepository {

    val apiService = createService(ApiService::class.java)

    fun <T> createService(clazz: Class<T>): T = RetrofitManager.create(clazz)

    suspend inline fun <reified T : Any> request(crossinline call: suspend () -> Call<ResponseBody>): BeanResponse<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response = call.invoke().execute()
                val result: BeanResponse<T> =
                    if (response.isSuccessful && response.body() != null) {
                        val body = response.body()!!.string()
                        try {
                            JsonUtils.fromJson(body, object : TypeToken<BeanResponse<T>>() {}.type)
                        } catch (e: Exception) {
                            if (T::class.java == java.lang.String::class.java) {
                                BeanResponse<T>().apply {
                                    code = HttpConstants.Status.Success
                                    data = body as T
                                }
                            } else {
                                try {
                                    JsonUtils.fromJson(body, object : TypeToken<T>() {}.type)
                                } catch (e: Exception) {
                                    BeanResponse<T>().apply {
                                        code = HttpConstants.Status.JsonParseError
                                        msg = e.message
                                    }
                                }
                            }
                        }
                    } else {
                        BeanResponse<T>().apply {
                            code = "${response.code()}"
                            msg = response.message()
                        }
                    }
                result
            } catch (e: Exception) {
                BeanResponse<T>().apply {
                    code = HttpConstants.Status.NetError
                    msg = e.message
                }
            }
        }.apply {
            //这儿可以对返回结果errorCode做一些特殊处理，比如token失效等，可以通过抛出异常的方式实现
            when (code) {
                HttpConstants.Status.TokenInvalidError -> throw TokenInvalidException()
            }
        }
    }

    suspend fun requestJson(call: suspend () -> Call<ResponseBody>): JsonResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response = call.invoke().execute()
                val result: JsonResponse =
                    if (response.isSuccessful && response.body() != null) {
                        val body = response.body()!!.string()
                        try {
                            JsonResponse(body)
                        } catch (e: Exception) {
                            JsonResponse().apply {
                                put("code", HttpConstants.Status.JsonParseError)
                                put("msg", e.message)
                            }
                        }
                    } else {
                        JsonResponse().apply {
                            put("code", response.code())
                            put("msg", response.message())
                        }
                    }
                result
            } catch (e: Exception) {
                JsonResponse().apply {
                    put("code", HttpConstants.Status.NetError)
                    put("msg", e.message)
                }
            }
        }.apply {
            when (getCode()) {
                HttpConstants.Status.TokenInvalidError -> throw TokenInvalidException()
            }
        }
    }

    class TokenInvalidException(msg: String = "Token Invalid") : Exception(msg)
}