package com.wny2023.mp01seoulculture.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wny2023.mp01seoulculture.R
import com.wny2023.mp01seoulculture.R.*
import com.wny2023.mp01seoulculture.R.layout.*
import com.wny2023.mp01seoulculture.adapters.ContentAdapter
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        items=loadData()

    }//onCreate

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        contentAdapter = ContentAdapter(requireActivity(),items)
        return inflater.inflate(fragment_content,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView= view?.findViewById(R.id.container_recycler)!!
        recyclerView.adapter = contentAdapter
        recyclerView.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)

    }
    private fun loadData():MutableList<Item>{
        //retrofit
        var retrofit:Retrofit = RetrofitHelper
            .getRetrofitInstance("http://openapi.seoul.go.kr:8088")
        var retrofitService = retrofit.create(RetrofitService::class.java)
//        retrofitService.loadServerAll2().enqueue(object :Callback<String>{
//            override fun onResponse(call: Call<String>, response: retrofit2.Response<String>) {
//                var s=response.body()
//                Toast.makeText(requireContext(), "API:${s}", Toast.LENGTH_SHORT).show()
//                Log.i("API","API:${s}")
//            }
//
//            override fun onFailure(call: Call<String>, t: Throwable) {
//                Toast.makeText(requireContext(), "Error:${t.message}", Toast.LENGTH_SHORT).show()
//                Log.i("ErrorAPI","Error:${t.message}")
//            }
//
//        })
        var call: Call<Response> = retrofitService.loadServerAll()
        call.enqueue(object : Callback<Response> {
            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                var responseAPI: Response? = response.body()
                if (responseAPI != null) {
                    var itemsAPI:MutableList<Item> = responseAPI.culturalEventInfo.row!!
                    items= itemsAPI
                    Toast.makeText(requireContext(), "Error:${itemsAPI.size}", Toast.LENGTH_SHORT).show()
                    Log.i("ErrorAPI","${responseAPI}")
                    return
                } else{
                    Toast.makeText(requireContext(), "API불러오기실패", Toast.LENGTH_SHORT).show()
                    return
                }
            }
            override fun onFailure(call: Call<Response>, t: Throwable) {
                Toast.makeText(requireContext(), "Error:${t.message}", Toast.LENGTH_SHORT).show()
                Log.i("ErrorAPI","Error:${t.message}")
                return
            }

        })
        return items

    }
}