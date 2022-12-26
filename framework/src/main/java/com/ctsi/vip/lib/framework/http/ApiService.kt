package com.ctsi.vip.lib.framework.http

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET
    fun doGet(@Url url: String, @QueryMap params: Map<String, @JvmSuppressWildcards Any>): Call<ResponseBody>

    @POST
    @FormUrlEncoded
    fun doPost(@Url url: String, @FieldMap params: Map<String, @JvmSuppressWildcards Any>): Call<ResponseBody>

    @POST
    fun doJsonPost(@Url url: String, @Body body: RequestBody): Call<ResponseBody>

    @PUT
    @FormUrlEncoded
    fun doPut(@Url url: String, @FieldMap params: Map<String, @JvmSuppressWildcards Any>): Call<ResponseBody>

    @PUT
    fun doJsonPut(@Url url: String, @Body body: RequestBody): Call<ResponseBody>

    @HTTP(method = "DELETE", hasBody = true)
    fun doJsonDelete(@Url url: String, @Body body: RequestBody): Call<ResponseBody>

    @POST
    @Multipart
    fun upload(@Url url: String?, @Part file: MultipartBody.Part?): Call<ResponseBody>

    @GET
    @Streaming
    fun download(@Url url: String): Call<ResponseBody>
}