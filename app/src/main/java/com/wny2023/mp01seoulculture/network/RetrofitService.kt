package com.wny2023.mp01seoulculture.network

import com.wny2023.mp01seoulculture.models.Member
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RetrofitService {
    //로그인 검증
    @FormUrlEncoded
    @POST("mpproject/loginProf.php")
    fun loginProf(@Field("id") id:String, @Field("pass") pass:String): Call<Member>
}