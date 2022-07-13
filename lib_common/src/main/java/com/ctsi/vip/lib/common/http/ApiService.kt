package com.ctsi.vip.lib.common.http

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET
    fun doGet(@Url url: String, @QueryMap params: Map<String, Any>): Call<ResponseBody>

    @POST
    @FormUrlEncoded
    fun doPost(@Url url: String, @FieldMap params: Map<String, Any>): Call<ResponseBody>

    @POST
    fun doJsonPost(@Url url: String, @Body body: RequestBody): Call<ResponseBody>

    @PUT
    @FormUrlEncoded
    fun doPut(@Url url: String, @FieldMap params: Map<String, Any>): Call<ResponseBody>

    @POST
    fun doJsonPut(@Url url: String, @Body body: RequestBody): Call<ResponseBody>

    @POST
    @Multipart
    fun upload(@Url url: String?, @Part file: Part?): Call<ResponseBody>
}