package com.wny2023.mp01seoulculture.activities

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.wny2023.mp01seoulculture.R
import com.wny2023.mp01seoulculture.databinding.ActivityMainUnlogBinding
import com.wny2023.mp01seoulculture.fragments.ContentUnlogFragment
import com.wny2023.mp01seoulculture.fragments.ReviewFragment
import com.wny2023.mp01seoulculture.fragments.FavoritUnlogFragment

class MainUnlogActivity : AppCompatActivity() {

    val binding:ActivityMainUnlogBinding by lazy { ActivityMainUnlogBinding.inflate(layoutInflater) }

    var fragements = mutableListOf(ContentUnlogFragment(), FavoritUnlogFragment(), ReviewFragment())

    lateinit var bnv: BottomNavigationView

    lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.containerToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_menu_24)
        //drawertoggle 버튼 설정
        drawerToggle=
            ActionBarDrawerToggle(this,binding.root,binding.containerToolbar,R.string.open,R.string.close)
        drawerToggle.syncState()
        binding.root.addDrawerListener(drawerToggle)
        //navigationView 설정
        binding.menuDnv.setNavigationItemSelectedListener { item->
            when(item.itemId){
                R.id.item_favorit -> {
                    Toast.makeText(this, "회원전용 서비스입니다.", Toast.LENGTH_SHORT).show()
                    clickFavorit()
                }
                R.id.item_review -> {
                    Toast.makeText(this, "(구현예정) 리뷰를 엽니다.", Toast.LENGTH_SHORT).show()
                    clickReview()
                }
                R.id.item_edit -> {
                    Toast.makeText(this, "회원전용 서비스입니다.", Toast.LENGTH_SHORT).show()
                    clickPhotoEdit()
                }
                R.id.item_logout -> {
                    var builder = AlertDialog.Builder(this)
                    builder.setMessage("(회원전용)로그아웃?\n확인:첫화면으로")
                        .setPositiveButton("확인",
                            DialogInterface.OnClickListener { dialogInterface, i ->
                            startActivity(Intent(this,IntroActivity::class.java))
                        }).setNegativeButton("취소",
                            DialogInterface.OnClickListener { dialogInterface, i ->
                            Toast.makeText(this, "로그아웃 취소", Toast.LENGTH_SHORT).show()
                        }).create()
                    builder.show()
                }
            }
            binding.root.closeDrawer(binding.menuDnv)
            false
        }
        supportFragmentManager.beginTransaction().add(R.id.container_fragments,fragements[0]).commit()
        //bottom navigation에 연결하기
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

    }
    private fun clickPhotoEdit(){

    }
    private fun clickReview(){

    }
}