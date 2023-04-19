package com.wny2023.mp01seoulculture.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wny2023.mp01seoulculture.G
import com.wny2023.mp01seoulculture.R
import com.wny2023.mp01seoulculture.adapters.FavoritAdapter
import com.wny2023.mp01seoulculture.databinding.FragmentFavoritBinding
import com.wny2023.mp01seoulculture.models.Item
import com.wny2023.mp01seoulculture.network.RetrofitHelper
import com.wny2023.mp01seoulculture.network.RetrofitService
import com.wny2023.mp01seoulculture.models.Response
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit


class FavoritFragment:Fragment() {
    var items:MutableList<Item> = mutableListOf()
    lateinit var favoritAdapter: FavoritAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var binding: FragmentFavoritBinding

//    lateinit var titles:List<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= FragmentFavoritBinding.inflate(layoutInflater)
    }//onCreate

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loadFavorit()
        return binding.root
    }

    //즐겨찾기 정보 불러오기
    private fun loadFavorit() {
        //MySQL에서 즐겨찾기 정보 불러오기
        var retrofit1: Retrofit = RetrofitHelper.getRetrofitInstance("http://wny2023.dothome.co.kr")
        var retrofitService1 = retrofit1.create(RetrofitService::class.java)
        var call1: Call<ArrayList<Item>>? =
            retrofitService1.loadFavServer(G.member!!.id, G.member!!.pass)
        call1?.enqueue(object :Callback<ArrayList<Item>> {
            override fun onResponse(
                call: Call<ArrayList<Item>>,
                response: retrofit2.Response<ArrayList<Item>>
            ) {
                response.body()?.also {
                    items = it
                    favoritAdapter = FavoritAdapter(requireActivity(),items)
                    binding.containerRecycler.adapter =favoritAdapter
                    recyclerView=view?.findViewById(R.id.container_recycler)!!
                    recyclerView.adapter=favoritAdapter
                    recyclerView.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
                }
            }

            override fun onFailure(call: Call<ArrayList<Item>>, t: Throwable) {
                Toast.makeText(requireContext(), "Error:${t.message}", Toast.LENGTH_SHORT).show()
                Log.i("mp01API","Error:${t.message}")
            }
        })
    }
}