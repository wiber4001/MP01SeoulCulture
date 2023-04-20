package com.wny2023.mp01seoulculture.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wny2023.mp01seoulculture.databinding.FragmentFavoritUnlogBinding

class FavoritUnlogFragment:Fragment() {
    lateinit var binding: FragmentFavoritUnlogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= FragmentFavoritUnlogBinding.inflate(layoutInflater)
    }//onCreate

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }
}