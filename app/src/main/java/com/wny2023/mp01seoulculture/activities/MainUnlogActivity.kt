package com.wny2023.mp01seoulculture.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wny2023.mp01seoulculture.R
import com.wny2023.mp01seoulculture.databinding.ActivityMainUnlogBinding

class MainUnlogActivity : AppCompatActivity() {

    val binding:ActivityMainUnlogBinding by lazy { ActivityMainUnlogBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}