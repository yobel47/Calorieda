package com.bell.calorieda.data.api

import com.bell.calorieda.data.response.Response
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @Headers(
        "x-app-id:761545d2",
        "x-app-key:9c446a74a908b605a2767a15e244cca3",
        "x-remote-user-id:0"
    )
    @POST("natural/nutrients")
    fun postQuery(
        @Field("query") query: String?
    ): Call<Response>
}