package com.bell.calorieda.data.api

import com.bell.calorieda.BuildConfig.*
import com.bell.calorieda.data.response.Response
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @Headers(
        "x-app-id:$API_ID",
        "x-app-key:$API_KEY",
        "x-remote-user-id:$API_REMOTE"
    )
    @POST("natural/nutrients")
    fun postQuery(
        @Field("query") query: String?
    ): Call<Response>
}