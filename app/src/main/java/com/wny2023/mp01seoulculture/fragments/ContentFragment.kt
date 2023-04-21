package com.wny2023.mp01seoulculture.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wny2023.mp01seoulculture.R
import com.wny2023.mp01seoulculture.R.*
import com.wny2023.mp01seoulculture.R.layout.*
import com.wny2023.mp01seoulculture.adapters.ContentAdapter
import com.wny2023.mp01seoulculture.databinding.FragmentContentBinding
import com.wny2023.mp01seoulculture.models.Item
import com.wny2023.mp01seoulculture.models.Response
import com.wny2023.mp01seoulculture.network.RetrofitHelper
import com.wny2023.mp01seoulculture.network.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.create

class ContentFragment: Fragment(){

    var items:MutableList<Item> = mutableListOf()
    lateinit var contentAdapter: ContentAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var binding:FragmentContentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=FragmentContentBinding.inflate(layoutInflater)

    }//onCreate


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loadData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var eventTypes: Array<out String> =resources.getStringArray(array.content_type)
        var eventDistricts: Array<out String> =resources.getStringArray(array.district)
//        val adapter1 = ArrayAdapter(requireContext(),)
        var adapter1=ArrayAdapter(requireContext(),R.layout.list_simple,eventTypes)
        var adapter2=ArrayAdapter(requireContext(),R.layout.list_simple,eventDistricts)
        binding.etEventdistrict.setAdapter(adapter2)
        binding.etEventtype.setAdapter(adapter1)
    }

    private fun loadData(){
        //retrofit
        var retrofit:Retrofit = RetrofitHelper
            .getRetrofitInstance("http://openapi.seoul.go.kr:8088")
        var retrofitService = retrofit.create(RetrofitService::class.java)

        var call: Call<Response> = retrofitService.loadServerAll()
        call.enqueue(object : Callback<Response> {
            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                var responseAPI: Response? = response.body()
                if (responseAPI != null) {
                    var itemsAPI:MutableList<Item> = responseAPI.culturalEventInfo.row!!
                    Toast.makeText(requireContext(), "Total:${itemsAPI.size} 개", Toast.LENGTH_SHORT).show()
                    items=itemsAPI
                    contentAdapter = ContentAdapter(requireActivity(),items)
                    binding.containerRecycler.adapter = contentAdapter
                    recyclerView=view?.findViewById(R.id.container_recycler)!!
                    recyclerView.adapter=contentAdapter
                    recyclerView.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)

                } else{
                    Toast.makeText(requireContext(), "API불러오기실패", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Response>, t: Throwable) {
                Toast.makeText(requireContext(), "Error:${t.message}", Toast.LENGTH_SHORT).show()
                Log.i("ErrorAPI","Error:${t.message}")

            }
        })
    }//loadData()

}