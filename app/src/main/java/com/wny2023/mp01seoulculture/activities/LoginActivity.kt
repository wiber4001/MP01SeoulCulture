package com.wny2023.mp01seoulculture.activities

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.wny2023.mp01seoulculture.G
import com.wny2023.mp01seoulculture.R
import com.wny2023.mp01seoulculture.databinding.ActivityLoginBinding
import com.wny2023.mp01seoulculture.models.Member
import com.wny2023.mp01seoulculture.network.RetrofitHelper
import com.wny2023.mp01seoulculture.network.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {

    val binding:ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    //서버와 비교할 Memeber값 초기화
    var memberI  = Member("","","","","")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //영문만 입력가능하게 필터 추가
        var inputFilter = InputFilter { source, start, end, dest, dstart, dend ->
            val ps = Pattern.compile("^[a-zA-Z0-9]+\$")
            if (!ps.matcher(source).matches()) {
                Toast.makeText(this, "영문만 입력가능", Toast.LENGTH_SHORT).show()
                ""
            }else source
        }
        binding.etId.filters = arrayOf(inputFilter)

        //로그인 버튼 클릭
        binding.btnLogin.setOnClickListener {
            memberI.id=binding.etId.text.toString()
            memberI.pass=binding.etPass.text.toString()
            loginVerify(memberI) }

    }//onCreate()


    //로그인 검증
    private fun confirmLogin(member:Member){
        val builder =AlertDialog.Builder(this)
        builder.setTitle("로그인 정보").setMessage("${member.id}\n로 로그인합니다.\n")
            .setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                var intent =Intent(this@LoginActivity,MainActivity::class.java)
                G.member = memberI
                intent.putExtra("object",member)
                startActivity(intent)
            }).setNegativeButton("취소",DialogInterface.OnClickListener { dialog, which ->
                Toast.makeText(this@LoginActivity, "취소하셨습니다.", Toast.LENGTH_SHORT).show()
            }).setCancelable(false).create()
        builder.show()
    }

    //로그인 검증
    private fun loginVerify(member: Member){
        //서버에서 불러오기
        //1.레트로핏 객체 생성
        val retrofit:Retrofit = RetrofitHelper.getRetrofitInstance("http://wny2023.dothome.co.kr")
        var retrofitService:RetrofitService = retrofit.create(RetrofitService::class.java)
        //2.콜객체 생성
        var call:Call<Member> = retrofitService.loginProf(member.id,member.pass)
        //3.콜백함수 부르기
        call.enqueue(object :Callback<Member>{
            override fun onResponse(call: Call<Member>, response: Response<Member>) {
                var echo:Member = response.body() as Member
                Log.i("ECHOMY","${echo.imgUrl}")
                if(echo.id.length!=0){
                    confirmLogin(echo)
                }else{
                    Toast.makeText(this@LoginActivity, "가입정보가 확인되지 않습니다.", Toast.LENGTH_SHORT).show()
                    return
                }
            }
            override fun onFailure(call: Call<Member>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

    }
}