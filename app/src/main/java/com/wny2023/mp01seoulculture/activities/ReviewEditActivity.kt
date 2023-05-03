package com.wny2023.mp01seoulculture.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.wny2023.mp01seoulculture.R
import com.wny2023.mp01seoulculture.databinding.ActivityReviewEditBinding

class ReviewEditActivity : AppCompatActivity() {

    val binding: ActivityReviewEditBinding by lazy { ActivityReviewEditBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    //뒤로가기 버튼 작동하게 하기
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }


}