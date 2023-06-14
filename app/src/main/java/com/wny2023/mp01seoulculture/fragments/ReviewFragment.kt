package com.wny2023.mp01seoulculture.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.wny2023.mp01seoulculture.G
import com.wny2023.mp01seoulculture.R
import com.wny2023.mp01seoulculture.activities.ReviewEditActivity
import com.wny2023.mp01seoulculture.adapters.ReviewAdapter
import com.wny2023.mp01seoulculture.databinding.FragmentReviewBinding
import com.wny2023.mp01seoulculture.models.Item
import com.wny2023.mp01seoulculture.models.Review


class ReviewFragment:Fragment() {

    var items:MutableList<Review> = mutableListOf()
    lateinit var recyclerView: RecyclerView
    lateinit var reviewAdapter: ReviewAdapter
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

    fun loadReview(){
        var firebase =FirebaseFirestore.getInstance()
        var collectionRef= firebase.collection("reviews")
        collectionRef.get().addOnSuccessListener {
            Toast.makeText(requireContext(), "aaa"+it.documents.size, Toast.LENGTH_SHORT).show()
            for( snapshot in it.documents ){
                snapshot.toObject<Review>()?.let { it1 -> items.add(it1) }
            }

            reviewAdapter= ReviewAdapter(requireActivity(), items)
            binding.containerReview.adapter=reviewAdapter
            recyclerView=view?.findViewById(R.id.container_review)!!
            recyclerView.adapter=reviewAdapter
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "loadfailed:${it}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadReview()
    }

}