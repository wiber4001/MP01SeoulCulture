package com.wny2023.mp01seoulculture.activities

import android.accounts.Account
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.wny2023.mp01seoulculture.AccountPref
import com.wny2023.mp01seoulculture.R
import com.wny2023.mp01seoulculture.databinding.ActivityMainBinding
import com.wny2023.mp01seoulculture.databinding.HeaderDnvBinding
import com.wny2023.mp01seoulculture.fragments.ContentFragment
import com.wny2023.mp01seoulculture.fragments.FavoritFragment
import com.wny2023.mp01seoulculture.fragments.ReviewFragment
import com.wny2023.mp01seoulculture.models.AccountState
import com.wny2023.mp01seoulculture.models.KakaoSearchPlaceResponse
import com.wny2023.mp01seoulculture.models.Member
import java.lang.Integer.parseInt

class MainActivity : AppCompatActivity() {

    val binding:ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    lateinit var drawerToggle: ActionBarDrawerToggle

    var accountPref: AccountState = AccountState("","","")

    var fragements = mutableListOf(ContentFragment(),FavoritFragment(),ReviewFragment())

    lateinit var bnv:BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //sharedPreference에 저장된 정보 불러오기
        accountPref.id=AccountPref.prefs.getString("id","no id")
        accountPref.email=AccountPref.prefs.getString("email", "no email")
        accountPref.imgUrl=AccountPref.prefs.getString("imgUrl", "no image")

        //사이드 drawer navigation메뉴설정
        //앱바를 툴바로 변경하고 navigation 아이콘 설정
        setSupportActionBar(binding.containerToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_menu_24)
        //drawertoggle 버튼 설정
        drawerToggle=
            ActionBarDrawerToggle(this,binding.root,binding.containerToolbar,R.string.open,R.string.close)
        drawerToggle.syncState()
        binding.root.addDrawerListener(drawerToggle)
        //navigationView 설정
        //navigation 헤더 설정
        //1.헤더 바인딩
        val dnvHeaderBinding: HeaderDnvBinding = HeaderDnvBinding.bind(binding.menuDnv.getHeaderView(0))
        //2.프로필사진
        var urlGlide:String ="http://wny2023.dothome.co.kr/mpproject/${accountPref.imgUrl}"
        Glide.with(this).load(urlGlide).into(dnvHeaderBinding.imgProfile)
        //3.아이디
        dnvHeaderBinding.tvId.text=AccountPref.prefs.getString("id","noid")


        //drawerNavigation메뉴 버튼 설정
        binding.menuDnv.setNavigationItemSelectedListener { item->
            when(item.itemId){
                R.id.item_favorit -> {
                    Toast.makeText(this, "내 즐겨찾기를 엽니다.", Toast.LENGTH_SHORT).show()
                    clickFavorit()
                }
                R.id.item_review -> {
                    Toast.makeText(this, "리뷰를 보러갑니다.", Toast.LENGTH_SHORT).show()
                    clickReview()
                }
                R.id.item_edit -> {
                    Toast.makeText(this, "프로필 사진을 변경", Toast.LENGTH_SHORT).show()
                    clickPhotoEdit()
                }
                R.id.item_logout -> {
                    var builder =AlertDialog.Builder(this)
                    builder.setMessage("로그아웃하시겠습니까?\n확인:첫화면으로")
                        .setPositiveButton("확인",DialogInterface.OnClickListener { dialogInterface, i ->
                            AccountPref.prefs.setString("id","")
                            AccountPref.prefs.setString("email","")
                            AccountPref.prefs.setString("imgUrl","")
                            startActivity(Intent(this,IntroActivity::class.java))
                        }).setNegativeButton("취소",DialogInterface.OnClickListener { dialogInterface, i ->
                            Toast.makeText(this, "로그아웃 취소", Toast.LENGTH_SHORT).show()
                        }).create()
                    builder.show()
                }
            }
            binding.root.closeDrawer(binding.menuDnv)
            false
        }

        //fragments 보여주기
        //intent로 넘겨받은것이 있으면 넘겨받은 화면 보여주기
        if(intent?.getStringExtra("fragment")!=null){
            var fragname =intent.getStringExtra("fragment")
            var fragnum:Int = parseInt(fragname)
            supportFragmentManager.beginTransaction().add(R.id.container_fragments,fragements[fragnum]).commit()
        }else{
        supportFragmentManager.beginTransaction().add(R.id.container_fragments,fragements[0]).commit()}

        //bottom navigation에 연결하여 fragment보여주기
        bnv=binding.menuBnv
        bnv.setOnItemSelectedListener{item->
            when(item.itemId){
                R.id.bnv_main -> supportFragmentManager.beginTransaction().replace(R.id.container_fragments,fragements[0]).commit()
                R.id.bnv_favorit -> supportFragmentManager.beginTransaction().replace(R.id.container_fragments,fragements[1]).commit()
                R.id.bnv_review -> supportFragmentManager.beginTransaction().replace(R.id.container_fragments,fragements[2]).commit()
            }
            true
        }
    }//onCreate()

    //구현예정
    private fun clickFavorit(){
        bnv.selectedItemId=R.id.bnv_favorit
    }
    private fun clickPhotoEdit(){

    }
    private fun clickReview(){
        bnv.selectedItemId=R.id.bnv_review
    }
}