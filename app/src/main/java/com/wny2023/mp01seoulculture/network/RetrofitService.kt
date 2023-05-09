package com.wny2023.mp01seoulculture.network

import com.wny2023.mp01seoulculture.models.Item
import com.wny2023.mp01seoulculture.models.KakaoSearchPlaceResponse
import com.wny2023.mp01seoulculture.models.Member
import com.wny2023.mp01seoulculture.models.NaverBlogReviewResponse
import com.wny2023.mp01seoulculture.models.Response
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path
import retrofit2.http.Query

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

    @GET("/565842635777696236346c4b424264/json/culturalEventInfo/1/500/{CODENAME}")
    fun loadServerPart(@Path("CODENAME") CODENAME:String) : Call<Response>

    //즐겨찾기 서버에 저장하기
    @Multipart
    @POST("mpproject/favoritSendDB.php")
    fun sendFavServer(@PartMap dataPart: Map<String, String>) :Call<String>

    //즐겨찾기 서버에서 삭제하기
    @FormUrlEncoded
    @POST("mpproject/favoritDeleteDB.php")
    fun deleteFavServer(@Field("id") id:String, @Field("pass") pass:String, @Field("TITLE") TITLE:String?): Call<String?>?

    //즐겨찾기 전체 불러오기
    @FormUrlEncoded
    @POST("mpproject/favoritLoadDB.php")
    fun loadFavServer(@Field("id") id:String, @Field("pass") pass:String): Call<ArrayList<Item>>?

    //즐겨찾기 누른것 확인하기
    @FormUrlEncoded
    @POST("mpproject/favoritCheckDB.php")
    fun favoritConfirm(@Field("id") id:String, @Field("pass") pass:String, @Field("TITLE") TITLE:String): Call<Item>?

    //회원탈퇴하기-회원정보삭제
    @FormUrlEncoded
    @POST("mpproject/deleteMembers.php")
    fun deleteAcoountServer(@Field("id") id:String, @Field("pass") pass:String): Call<String?>?

    //네이버블로그검색
    //헤더값이 고정적이라면, 굳이 매번 파라미터로 받을 이유가 없음
    @Headers("X-Naver-Client-Id:z80Up7KE8hFszjYucF1y","X-Naver-Client-Secret:_AczEHOvb7")
    @GET("/v1/search/blog.json?display=50")
    fun searchData(@Query("query") query: String) : Call<NaverBlogReviewResponse>

}