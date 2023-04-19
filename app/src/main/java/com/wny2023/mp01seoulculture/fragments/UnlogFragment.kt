package com.wny2023.mp01seoulculture.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.wny2023.mp01seoulculture.databinding.FragmentContentBinding
import com.wny2023.mp01seoulculture.databinding.FragmentUnlogBinding

class UnlogFragment:Fragment() {
    lateinit var binding: FragmentUnlogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= FragmentUnlogBinding.inflate(layoutInflater)
    }//onCreate
}