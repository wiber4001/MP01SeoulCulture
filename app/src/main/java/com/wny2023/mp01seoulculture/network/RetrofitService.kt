package com.wny2023.mp01seoulculture.network

import com.wny2023.mp01seoulculture.models.Member
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface RetrofitService {

    //로그인 하기
    @FormUrlEncoded
    @POST("mpproject/loginProf.php")
    fun loginProf(@Field("id") id:String, @Field("pass") pass:String): Call<Member>

    //기존 가입 확인
    @FormUrlEncoded
    @POST("mpproject/loadMembers.php")
    fun idConfirm(@Field("id") id:String) :Call<String>

    @Multipart
    @POST("mpproject/signupPostDB.php")
    fun sendServer(@PartMap dataPart:Map<String,String>, @Part filePart: MultipartBody.Part?) :Call<String>
}