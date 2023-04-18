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
    lateinit var titles:List<String>
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
        var call1: Call<String>? =
            retrofitService1.loadFavServer(G.member!!.id, G.member!!.pass)
        call1?.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: retrofit2.Response<String>) {
                response.body()?.also {
                    titles = it.split(",")
                    for (i in 0 until  titles.size){
                        var retrofit: Retrofit = RetrofitHelper.getRetrofitInstance("http://openapi.seoul.go.kr:8088")
                        var retrofitService =retrofit.create (RetrofitService::class.java)
                        var call: Call<Response> =retrofitService.loadServerFav(titles[i])
                        call.enqueue(object :Callback<Response>{
                            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                                var responseAPI: Response? = response.body()
                                if (responseAPI != null) {
                                    var itemsAPI: MutableList<Item> = responseAPI.culturalEventInfo.row!!
                                    items = itemsAPI
                                    favoritAdapter = FavoritAdapter(requireActivity(),items)
                                    binding.containerRecycler.adapter = favoritAdapter
                                    recyclerView=view?.findViewById(R.id.container_recycler)!!
                                    recyclerView.adapter=favoritAdapter
                                    recyclerView.layoutManager= LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
                                }else{
                                    Toast.makeText(requireContext(), "API불러오기실패", Toast.LENGTH_SHORT).show()
                                }
                            }

                            override fun onFailure(call: Call<Response>, t: Throwable) {
                                Toast.makeText(requireContext(), "Error:${t.message}", Toast.LENGTH_SHORT).show()
                                Log.i("mp01API","Error:${t.message}")
                            }
                        })
                    }
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(requireContext(), "서버오류:${t.message}", Toast.LENGTH_SHORT).show()
                Log.i("mp01server",t.message.toString())
            }
        })
    }

//    private fun loadAPI(title:String){
//
//
//    }
}