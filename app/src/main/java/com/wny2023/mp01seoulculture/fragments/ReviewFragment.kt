package com.wny2023.mp01seoulculture.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.fragment.app.Fragment
import com.wny2023.mp01seoulculture.G
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnReviewAdd.setOnClickListener { view->
            if(G.member?.id==null) {
                Toast.makeText(requireContext(), "회원 전용 기능입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else{
                var intent =Intent(requireContext(),ReviewEditActivity::class.java)
                intent.run { context?.startActivity(intent) }
                Log.d("FAB","intent작동되나")
            }

        }
    }

}