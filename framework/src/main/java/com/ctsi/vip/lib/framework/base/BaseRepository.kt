package com.ctsi.vip.lib.framework.base

import com.ctsi.vip.lib.framework.http.ApiService
import com.ctsi.vip.lib.framework.http.HttpConstants
import com.ctsi.vip.lib.framework.http.RetrofitManager
import com.ctsi.vip.lib.framework.http.response.BeanResponse
import com.ctsi.vip.lib.framework.utils.JsonUtils
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

    suspend inline fun <reified T> request(noinline call: suspend () -> Call<ResponseBody>): BeanResponse<T> {
        val response = try {
            val result = requestOrigin(call)
            JsonUtils.jsonToBeanResponse<T>(result)!!
        } catch (e: Exception) {
            BeanResponse<T>().apply {
                code = HttpConstants.Error.UnknownError
                msg = e.message
            }
        }
        when (response.code) {
            HttpConstants.Error.TokenInvalidError -> throw TokenInvalidException()
        }
        return response
    }

    suspend inline fun <reified T> requestCus(noinline call: suspend () -> Call<ResponseBody>): T? {
        return try {
            JsonUtils.jsonToBean<T>(requestOrigin(call))
        } catch (e: Exception) {
            null
        }
    }

    suspend fun requestOrigin(call: suspend () -> Call<ResponseBody>): String {
        return withContext(Dispatchers.IO) {
            try {
                val request = call.invoke().execute()
                val response: String = if (request.isSuccessful && request.body() != null) {
                    request.body()!!.string()
                } else {
                    generateErrorJson("${request.code()}", request.errorBody()?.string())
                }
                response
            } catch (e: Exception) {
                generateErrorJson(HttpConstants.Error.NetError, e.message)
            }
        }
    }

    private fun generateErrorJson(code: String, errMsg: String?): String {
        return "{\"code\":\"${code}\", \"msg\" : \"${errMsg}\"}"
    }

    class TokenInvalidException(msg: String = "Token Invalid") : Exception(msg)
}