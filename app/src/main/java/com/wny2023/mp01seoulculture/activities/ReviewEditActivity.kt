package com.wny2023.mp01seoulculture.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wny2023.mp01seoulculture.databinding.ActivityReviewEditBinding

class ReviewEditActivity : AppCompatActivity() {

    val binding: ActivityReviewEditBinding by lazy { ActivityReviewEditBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}