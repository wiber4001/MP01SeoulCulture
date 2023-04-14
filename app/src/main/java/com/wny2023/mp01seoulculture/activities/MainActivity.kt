package com.wny2023.mp01seoulculture.activities

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.wny2023.mp01seoulculture.R
import com.wny2023.mp01seoulculture.databinding.ActivityMainBinding
import com.wny2023.mp01seoulculture.databinding.HeaderDnvBinding
import com.wny2023.mp01seoulculture.models.Member

class MainActivity : AppCompatActivity() {

    val binding:ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    lateinit var drawerToggle: ActionBarDrawerToggle

    var memberIn =Member("","","","","")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //intent에서 넘겨받은 멤버 정보 받음
        memberIn = intent?.getSerializableExtra("object") as Member

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
        Log.i("PROFILE","${memberIn.imgUrl}")
//        Glide.with(this).load(memberIn.imgUrl).into(dnvHeaderBinding.imgProfile)
        //3.아이디
        dnvHeaderBinding.tvId.text=memberIn.id

        //drawerNavigation메뉴 버튼 설정
        binding.menuDnv.setNavigationItemSelectedListener { item->
            when(item.itemId){
                R.id.item_favorit -> {
                    Toast.makeText(this, "내 즐겨찾기를 엽니다.", Toast.LENGTH_SHORT).show()
                    clickFavorit()
                }
                R.id.item_review -> {
                    Toast.makeText(this, "내 리뷰를 엽니다.", Toast.LENGTH_SHORT).show()
                    clickReview()
                }
                R.id.item_edit -> {
                    Toast.makeText(this, "내 프로필 사진을 변경", Toast.LENGTH_SHORT).show()
                    clickPhotoEdit()
                }
                R.id.item_logout -> {
                    var builder =AlertDialog.Builder(this)
                    builder.setMessage("로그아웃하시겠습니까?\n확인:첫화면으로")
                        .setPositiveButton("확인",DialogInterface.OnClickListener { dialogInterface, i ->
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
    }//onCreate()

    private fun clickFavorit(){

    }
    private fun clickPhotoEdit(){

    }
    private fun clickReview(){

    }
}