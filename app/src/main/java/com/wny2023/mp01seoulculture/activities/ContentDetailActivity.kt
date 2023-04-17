package com.wny2023.mp01seoulculture.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.wny2023.mp01seoulculture.R
import com.wny2023.mp01seoulculture.databinding.ActivityContentDetailBinding
import com.wny2023.mp01seoulculture.models.Item
import com.wny2023.mp01seoulculture.models.Member

class ContentDetailActivity : AppCompatActivity() {

    val binding:ActivityContentDetailBinding by lazy { ActivityContentDetailBinding.inflate(layoutInflater) }

    lateinit var itemClick:Item
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        itemClick=intent?.getSerializableExtra("object") as Item

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(itemClick.TITLE)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)

        //화면설정
        Glide.with(this).load(itemClick.MAIN_IMG).into(binding.imgMain)
        binding.tvDistrict.text=itemClick.GUNAME
        binding.tvFee.text=itemClick.USE_FEE
        binding.tvPlace.text=itemClick.PLACE
        binding.tvDuring.text=itemClick.DATE
        binding.tvLink.text=itemClick.ORG_LINK
        binding.tvProgram.text=itemClick.PROGRAM
        binding.tvTitle.text=itemClick.TITLE

        binding.btnFavorit.setOnClickListener(View.OnClickListener {

        })

    }//onCreate()
}
