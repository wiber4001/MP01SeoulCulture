package com.wny2023.mp01seoulculture.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import com.wny2023.mp01seoulculture.databinding.ActivityIntroBinding

//로그인과 회원가입이 안내되는 첫페이지
class IntroActivity : AppCompatActivity() {

    val binding:ActivityIntroBinding by lazy { ActivityIntroBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //1.로그인버튼 클릭시
        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }

        //2.회원가입버튼 클릭시
        binding.btnSignup.setOnClickListener {
            startActivity(Intent(this,SignupActivity::class.java))
        }

        //3.둘러보기버튼 클릭시
        binding.btnUnlogin.setOnClickListener {
            startActivity(Intent(this,MainUnlogActivity::class.java))
            Toast.makeText(this, "앱을 둘러봅니다.", Toast.LENGTH_SHORT).show()
        }

        //4.간편로그인/회원가입 버튼 처리
        binding.imgKakao.setOnClickListener{kakaoLogin()}
        binding.imgGoogle.setOnClickListener{googleLogin()}
        binding.imgNaver.setOnClickListener{naverLogin()}

    }//onCreate()

    private fun kakaoLogin(){

    }

    private fun googleLogin(){

    }

    private fun naverLogin(){

    }

}