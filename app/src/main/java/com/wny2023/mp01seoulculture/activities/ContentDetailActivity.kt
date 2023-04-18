package com.wny2023.mp01seoulculture.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.wny2023.mp01seoulculture.G
import com.wny2023.mp01seoulculture.R
import com.wny2023.mp01seoulculture.databinding.ActivityContentDetailBinding
import com.wny2023.mp01seoulculture.models.Item
import com.wny2023.mp01seoulculture.models.Member
import com.wny2023.mp01seoulculture.network.RetrofitHelper
import com.wny2023.mp01seoulculture.network.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ContentDetailActivity : AppCompatActivity() {

    val binding:ActivityContentDetailBinding by lazy { ActivityContentDetailBinding.inflate(layoutInflater) }

    lateinit var itemClick:Item
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        itemClick=intent?.getSerializableExtra("object") as Item

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

        binding.btnToggle.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                clickFavorit()
            } else {
                unclickFavorit()
            }
        }

    }//onCreate()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_option,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
            R.id.menu_map -> mapSearch()
            R.id.menu_favorit -> clickFavorit()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun mapSearch(){

    }

    private fun clickFavorit(){
        var retrofit:Retrofit = RetrofitHelper.getRetrofitInstance("Http://wny2023.dothome.co.kr")
        var retrofitService:RetrofitService = retrofit.create(RetrofitService::class.java)
        var datapart = HashMap<String,String>()
        G.member?.let {
            datapart.put("id", it.id)
            datapart.put("pass",it.pass)
        }
        datapart.put("TITLE",itemClick.TITLE)
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
