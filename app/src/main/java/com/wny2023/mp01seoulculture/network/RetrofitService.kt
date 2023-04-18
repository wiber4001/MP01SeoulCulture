package com.wny2023.mp01seoulculture.network

import com.wny2023.mp01seoulculture.models.Member
import com.wny2023.mp01seoulculture.models.Response
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path

interface RetrofitService {

    //로그인 하기
    @FormUrlEncoded
    @POST("mpproject/loginProf.php")
    fun loginProf(@Field("id") id:String, @Field("pass") pass:String): Call<Member>

    //기존 가입 확인
    @FormUrlEncoded
    @POST("mpproject/loadMembers.php")
    fun idConfirm(@Field("id") id:String) :Call<String>

    //회원가입정보 서버저장
    @Multipart
    @POST("mpproject/signupPostDB.php")
    fun sendServer(@PartMap dataPart:Map<String,String>, @Part filePart: MultipartBody.Part?) :Call<String>

    //행사정보 json으로 불러오기
    @GET("/565842635777696236346c4b424264/json/culturalEventInfo/1/500/")
    fun loadServerAll() : Call<Response>

    //즐겨찾기 서버에 저장하기
    @Multipart
    @POST("mpproject/favoritSendDB.php")
    fun sendFavServer(@PartMap dataPart: Map<String, String>) :Call<String>

    //즐겨찾기 서버에서 삭제하기
    @FormUrlEncoded
    @POST("mpproject/favoritDeleteDB.php")
    fun deleteFavServer(@Field("id") id:String, @Field("pass") pass:String, @Field("TITLE") TITLE:String?): Call<String?>?

    //즐겨찾기 불러오기
    @FormUrlEncoded
    @POST("mpproject/favoritLoadDB.php")
    fun loadFavServer(@Field("id") id:String, @Field("pass") pass:String): Call<String>?

    //즐겨찾기한 행사정보 json으로 불러오기
    @GET("/565842635777696236346c4b424264/json/culturalEventInfo/1/500/ /{TITLE}")
    fun loadServerFav(@Path("TITLE") TITLE: String?) : Call<Response>

}