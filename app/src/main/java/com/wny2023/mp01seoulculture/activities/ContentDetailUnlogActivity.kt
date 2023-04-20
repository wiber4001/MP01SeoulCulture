package com.wny2023.mp01seoulculture.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem

import android.widget.Toast

import com.bumptech.glide.Glide
import com.wny2023.mp01seoulculture.G
import com.wny2023.mp01seoulculture.GItem
import com.wny2023.mp01seoulculture.R

import com.wny2023.mp01seoulculture.databinding.ActivityContentDetailBinding
import com.wny2023.mp01seoulculture.models.Item

import com.wny2023.mp01seoulculture.network.RetrofitHelper
import com.wny2023.mp01seoulculture.network.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ContentDetailUnlogActivity : AppCompatActivity() {

    val binding:ActivityContentDetailBinding by lazy { ActivityContentDetailBinding.inflate(layoutInflater) }
    lateinit var itemClick:Item

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        itemClick=intent?.getSerializableExtra("object") as Item
        Log.i("intentMP01","${intent?.getStringExtra("favoritChk")}")

        GItem.g_item=itemClick

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        //화면설정
        Glide.with(this).load(itemClick.MAIN_IMG).into(binding.imgMain)
        binding.tvDistrict.text=itemClick.GUNAME
        binding.tvFee.text=itemClick.USE_FEE
        binding.tvPlace.text=itemClick.PLACE
        binding.tvDuring.text=itemClick.DATE
        binding.tvLink.text=itemClick.ORG_LINK
        binding.tvProgram.text=itemClick.PROGRAM
        binding.tvTitle.text=itemClick.TITLE

        //좋아요버튼 클릭 이벤트
        binding.btnToggle.isChecked =false

    }//onCreate()

    override fun onResume() {
        super.onResume()
        binding.btnToggle.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                Toast.makeText(this, "즐겨찾기에 추가(회원전용 서비스)", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "즐겨찾기 삭제(회원전용 서비스)", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_option,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
            R.id.menu_map -> mapSearch()
            R.id.menu_favorit -> Toast.makeText(this, "회원전용 서비스 입니다.", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun mapSearch(){
        Toast.makeText(this, "(기능구현예정)지도 검색 서비스를 엽니다.", Toast.LENGTH_SHORT).show()
    }

}
