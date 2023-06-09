package com.wny2023.mp01seoulculture.activities

import android.content.Intent
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

class ContentDetailActivity : AppCompatActivity() {

    val binding:ActivityContentDetailBinding by lazy { ActivityContentDetailBinding.inflate(layoutInflater) }
    var favChk: Boolean? = false
    lateinit var itemClick:Item

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        itemClick=intent?.getSerializableExtra("object") as Item
        favChk =intent?.getStringExtra("favoritChk").toBoolean()
//        Toast.makeText(this, "${favChk}", Toast.LENGTH_SHORT).show()
        Log.i("intentMP01","${intent?.getStringExtra("favoritChk")}")

        if(favChk as Boolean) binding.btnToggle.isChecked = true
        else binding.btnToggle.isChecked =false

        GItem.g_item=itemClick

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        //화면설정
        Glide.with(this).load(itemClick.MAIN_IMG).into(binding.imgMain)
        binding.tvCodename.text=itemClick.CODENAME
        binding.tvDistrict.text=itemClick.GUNAME
        binding.tvFee.text=itemClick.USE_FEE
        binding.tvPlace.text=itemClick.PLACE
        binding.tvDuring.text=itemClick.DATE
        binding.tvLink.text=itemClick.ORG_LINK
        binding.tvProgram.text=itemClick.PROGRAM
        binding.tvTitle.text=itemClick.TITLE

    }//onCreate()

    override fun onResume() {
        super.onResume()
        binding.btnToggle.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                clickFavorit()
            }else{
                unclickFavorit()
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
            R.id.menu_reviewedit -> reviewEdit()
//                Toast.makeText(this, "(구현예정)리뷰작성기능", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun mapSearch(){
        Toast.makeText(this, "(구현예정) 주소로 장소검색", Toast.LENGTH_SHORT).show()
    }

    private fun reviewEdit(){
        if(G.member?.id==null) {
            Toast.makeText(this, "회원 전용 기능입니다.", Toast.LENGTH_SHORT).show()
            return
        }
        else{
            var intent = Intent(this,ReviewEditActivity::class.java)
            intent.putExtra("title",GItem.g_item?.TITLE)
            startActivity(intent)
            Log.d("review","intent작동되나")
        }
    }

    private fun clickFavorit(){
        var retrofit:Retrofit = RetrofitHelper.getRetrofitInstance("Http://wny2023.dothome.co.kr")
        var retrofitService:RetrofitService = retrofit.create(RetrofitService::class.java)
        var datapart = HashMap<String,String>()
        G.member?.let {
            datapart.put("id", it.id)
            datapart.put("pass",it.pass)
        }
        GItem.g_item?.let {
            datapart.put("TITLE",it.TITLE)
            datapart.put("GUNAME",it.GUNAME)
            datapart.put("PLACE",it.PLACE)
            datapart.put("DATE",it.DATE)
            datapart.put("USE_FEE",it.USE_FEE)
            datapart.put("PROGRAM",it.PROGRAM)
            datapart.put("ORG_LINK",it.ORG_LINK)
            datapart.put("MAIN_IMG",it.MAIN_IMG)
        }

        var call: Call<String> = retrofitService.sendFavServer(datapart)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                var p:Boolean = response.body().toBoolean()
                if(p) {
                    Toast.makeText(this@ContentDetailActivity, "좋아요", Toast.LENGTH_SHORT).show()
                }else Toast.makeText(this@ContentDetailActivity, "서버에 저장하지 못했습니다.", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(this@ContentDetailActivity, "서버오류:${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }//clickFavorit()

    private fun unclickFavorit(){
        var retrofit:Retrofit = RetrofitHelper.getRetrofitInstance("Http://wny2023.dothome.co.kr")
        var retrofitService:RetrofitService = retrofit.create(RetrofitService::class.java)
        var call: Call<String?>? = retrofitService.deleteFavServer(G.member!!.id, G.member!!.pass,itemClick?.TITLE)
        call?.enqueue(object : Callback<String?>{
            override fun onResponse(call: Call<String?>, response: Response<String?>) {
                var msg:String = response.body().toString()
                Toast.makeText(this@ContentDetailActivity, "${msg}", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<String?>, t: Throwable) {
                Toast.makeText(this@ContentDetailActivity, "서버오류:${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }

}
