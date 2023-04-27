package com.wny2023.mp01seoulculture.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import com.wny2023.mp01seoulculture.activities.ReviewEditActivity
import com.wny2023.mp01seoulculture.databinding.FragmentReviewBinding


class ReviewFragment:Fragment() {

    lateinit var binding: FragmentReviewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=FragmentReviewBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.fab.setOnClickListener { view->
            var intent =Intent(requireContext(),ReviewEditActivity::class.java)
            intent.run { context?.startActivity(intent) }
            Log.d("FAB","intent작동되나")
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        binding.fab.setOnClickListener { view->
//            var intent =Intent(requireContext(),ReviewEditActivity::class.java)
//            intent.run { context?.startActivity(intent) }
//            Log.d("FAB","intent작동되나")
//        }
//    }
}