package com.wny2023.mp01seoulculture.activities


import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.wny2023.mp01seoulculture.R
import com.wny2023.mp01seoulculture.databinding.ActivitySignupBinding
import com.wny2023.mp01seoulculture.models.Member
import com.wny2023.mp01seoulculture.network.RetrofitHelper
import com.wny2023.mp01seoulculture.network.RetrofitService
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.File
import java.util.regex.Pattern

class SignupActivity : AppCompatActivity() {

    val binding:ActivitySignupBinding by lazy { ActivitySignupBinding.inflate(layoutInflater) }
    var memberNew =Member("","","","","")

    //사진선택하는 변수
    val photoPicker = registerForActivityResult(ActivityResultContracts.PickVisualMedia()){ uri->
        if(uri!=null){
            if (uri != null) {
                Toast.makeText(this, "프로필 사진이 선택되었습니다.", Toast.LENGTH_SHORT).show()
                Glide.with(this).load(uri.toString()).into(binding.imgProfile)
                memberNew.imgUrl = ablouteImgPath(uri)
            } else {
                Log.d("PhotoPicker", "No media selected")
                Toast.makeText(this, "프로필 사진이 선택되지 않았습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //사진 절대경로 만들어주는 함수
    fun ablouteImgPath(path: Uri): String {
        var proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        var c: Cursor? = contentResolver.query(path, proj, null, null, null)
        var index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()
        var result = index?.let { c?.getString(it) }

        return result!!
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //1.사진선택버튼
        binding.btnSlectphoto.setOnClickListener { photoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }
        //2.아이디입력
        //영문,숫자만가능하게 필터걸기
        var inputFilter = InputFilter { source, start, end, dest, dstart, dend ->
            val ps = Pattern.compile("^[0-9a-zA-Z]+\$")
            if (!ps.matcher(source).matches()) {
                Toast.makeText(this, "영문,숫자만 입력가능", Toast.LENGTH_SHORT).show()
                source.dropLast(1)
            }else source
        }
        binding.etId.filters = arrayOf(inputFilter)
        //3.이메일입력
        binding.etEmail.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                checkEmail()
            }
        })
        //4.비밀번호입력
        binding.etPass1.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Toast.makeText(this@SignupActivity, "영문,숫자,!@#\$%^만 입력가능", Toast.LENGTH_SHORT).show()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
        //영문,숫자,특수문자만 가능하게 필터걸기
        var inputFilter2 = InputFilter { source, start, end, dest, dstart, dend ->
            val ps = Pattern.compile("^[0-9a-zA-Z!@#\$%^]+\$")
            if (!ps.matcher(source).matches()) {
                Toast.makeText(this, "영문,숫자,!@#\$%^만 입력가능", Toast.LENGTH_SHORT).show()
                ""
            }else source
        }
        binding.etPass1.filters = arrayOf(inputFilter2)

        //5.비밀번호확인
        binding.etPass2.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                var pass1:String =binding.etPass1.text.toString()
                var pass2:String =binding.etPass2.text.toString()
                passCheck(pass1,pass2)
            }

        })
        //6.회원가입버튼
        binding.btnSignup.setOnClickListener { signUp() }

    }//onCreate()

    //이메일 검증용 변수
    val emailValidation = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    fun checkEmail():Boolean{
        var email = binding.etEmail.text.toString().trim() //공백제거
        val p= Pattern.matches(emailValidation, email)
        if (p) {
            //이메일 형태가 정상일 경우
            binding.etEmail.setTextColor(R.color.mp_800.toInt())
            memberNew.email = binding.etEmail.text.toString()
            return true
        } else {
            //이메일 형태가 비정형일경우
            binding.etEmail.setTextColor(R.color.mp_red.toInt())
            return false
        }
    }

    //비밀번호 확인
    private fun passCheck(pass1:String, pass2:String){
        val p= pass1.equals(pass2)
        if (p) {
            Toast.makeText(this, "비밀번호가 일치합니다.", Toast.LENGTH_SHORT).show()
            memberNew.pass= binding.etPass2.text.toString()
            val imm:InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.etPass2.windowToken,0)
            return
        } else {
//            Toast.makeText(this, "비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show()
            return
        }

    }

    //회원가입
    private fun signUp(){
        memberNew.id = binding.etId.text?.toString() ?:""
        memberNew.path= "InApp"
        infoConfirm()


    }

    //정보 입력 확인

    private fun infoConfirm() {
        if (memberNew.id.length == 0) {
            Toast.makeText(this, "아이디는 필수입니다.", Toast.LENGTH_SHORT).show()
            binding.etId.requestFocus()
            return
        } else if (memberNew.pass.length == 0) {
            Toast.makeText(this, "비밀번호를 확인해주십시오.", Toast.LENGTH_SHORT).show()
            binding.etPass1.requestFocus()
            return
        } else {
            var retrofit: Retrofit =
                RetrofitHelper.getRetrofitInstance("http://wny2023.dothome.co.kr")
            var retrofitService: RetrofitService = retrofit.create(RetrofitService::class.java)
            var call: Call<String> = retrofitService.idConfirm(memberNew.id)
            call.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    var p: Boolean = response.body().toBoolean()
                    if (p) {
                        var builder = AlertDialog.Builder(this@SignupActivity)
                        builder.setTitle("신규가입 가능합니다.")
                            .setMessage("id: ${memberNew.id}\npass:${memberNew.pass}\n신규회원가입")
                            .setPositiveButton("확인", { dialog, which ->
                                    //1.레트로핏 객체 생성
                                    val retrofit: Retrofit =
                                        RetrofitHelper.getRetrofitInstance("Http://wny2023.dothome.co.kr")
                                    //2.레트로핏 서비스 객체 생성
                                    val retrofitService: RetrofitService =
                                        retrofit.create(RetrofitService::class.java)
                                    //3.call객체 생성해서 보내기
                                    //data부분
                                    var datapart = HashMap<String, String>()
                                    datapart.put("id", memberNew.id)
                                    datapart.put("pass", memberNew.pass)
                                    datapart.put("email", memberNew.email)
                                    datapart.put("path", memberNew.path)
                                    //file부분
                                    var filepart: MultipartBody.Part? = null
                                    if (memberNew.imgUrl != null) {
                                        val file = File(memberNew.imgUrl)
                                        val requestFile =
                                            RequestBody.create(MediaType.parse("image/*"), file)
                                        filepart = MultipartBody.Part.createFormData("img",file.name, requestFile)
                                    }
                                    var call: Call<String> = retrofitService.sendServer(datapart, filepart)
                                    call.enqueue(object : Callback<String> {
                                        override fun onResponse(call: Call<String>, response: Response<String>) {
//                                            var msg: String = response.body().toString()
                                            var intent = Intent(this@SignupActivity,LoginActivity::class.java)
                                            intent.putExtra("object", memberNew)
                                            startActivity(intent)
                                            finish()
                                        }
                                        override fun onFailure(call: Call<String>, t: Throwable) {
                                            Toast.makeText(this@SignupActivity,"${t.message}",Toast.LENGTH_SHORT).show()
                                        }
                                    })
                                }).setNegativeButton(
                                "취소",
                                DialogInterface.OnClickListener { dialog, which ->
                                    Toast.makeText(this@SignupActivity,"가입을 취소합니다.",Toast.LENGTH_SHORT).show()
                                }).create()
                        builder.show()
                    } else {
                        Toast.makeText(this@SignupActivity, "이미 가입된 아이디입니다.", Toast.LENGTH_SHORT).show()
                        binding.etId.requestFocus()
                        return
                    }
                }
                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(this@SignupActivity, "서버오류, 가입처리 불가", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}