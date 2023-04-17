package com.wny2023.mp01seoulculture.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
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
//    lateinit var contentAdapter: ContentAdapter?
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadData()

    }//onCreate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }//onViewCreated

    private fun loadData(){
        //retrofit
        var retrofit:Retrofit = RetrofitHelper
            .getRetrofitInstance("http://openapi.seoul.go.kr:8088/565842635777696236346c4b424264/json/")
        var retrofitService = retrofit.create(RetrofitService::class.java)
        var call: Call<Response> = retrofitService.loadServerAll()
        call.enqueue(object : Callback<Response> {
            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                TODO("Not yet implemented")
            }

            override fun onFailure(call: Call<Response>, t: Throwable) {
                Toast.makeText(requireContext(), "${t.message}", Toast.LENGTH_SHORT).show()
            }

        })


    }
}