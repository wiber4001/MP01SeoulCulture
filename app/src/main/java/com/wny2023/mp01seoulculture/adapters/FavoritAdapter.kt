package com.wny2023.mp01seoulculture.adapters

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.wny2023.mp01seoulculture.G
import com.wny2023.mp01seoulculture.activities.ContentDetailActivity
import com.wny2023.mp01seoulculture.databinding.RecyclerContentItemBinding
import com.wny2023.mp01seoulculture.models.Item
import com.wny2023.mp01seoulculture.network.RetrofitHelper
import com.wny2023.mp01seoulculture.network.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class FavoritAdapter (var context:Activity, var items:MutableList<Item>): Adapter<FavoritAdapter.VH> () {

    inner class VH(var binding:RecyclerContentItemBinding): ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritAdapter.VH {
        return VH(RecyclerContentItemBinding.inflate(LayoutInflater.from(context),parent,false))
    }
    override fun getItemCount(): Int = items.size
    override fun onBindViewHolder(holder: FavoritAdapter.VH, position: Int) {
        var item:Item = items[position]
        holder.binding.tvEvaddr.text=item.PLACE
        holder.binding.tvEvdistrict.text=item.GUNAME
        holder.binding.tvEvduring.text=item.DATE
        holder.binding.tvEvtitle.text=item.TITLE
        holder.binding.tvEvprogram.text=item.PROGRAM
        Glide.with(context).load(item.MAIN_IMG).into(holder.binding.imgEvent)

        holder.binding.root.setOnClickListener({
            var p="false"
            //MySQL에서 즐겨찾기 정보 불러오기
            var retrofit1: Retrofit = RetrofitHelper.getRetrofitInstance("http://wny2023.dothome.co.kr")
            var retrofitService1 = retrofit1.create(RetrofitService::class.java)
            var call1: Call<Item>? = retrofitService1.favoritConfirm(
                G.member!!.id,
                G.member!!.pass,item.TITLE)
            call1?.enqueue(object : Callback<Item> {
                override fun onResponse(call: Call<Item>, response: Response<Item>) {
                    response.body()?.also {
                        p="true"
//                        Toast.makeText(context, "즐겨찾기가 되어있습니다.${p}", Toast.LENGTH_SHORT).show()
                        val intent=Intent(context,ContentDetailActivity::class.java)
                        intent.putExtra("object",item)
                        intent.putExtra("favoritChk",p)
                        intent.run { context.startActivity(intent) }
                        return
                    }
//                    Toast.makeText(context, "즐겨찾기가 되어있지않습니다.${p}", Toast.LENGTH_SHORT).show()
                    val intent=Intent(context,ContentDetailActivity::class.java)
                    intent.putExtra("object",item)
                    intent.run { context.startActivity(intent) }
                }
                override fun onFailure(call: Call<Item>, t: Throwable) {
                    Toast.makeText(context, "error:${t.message}", Toast.LENGTH_SHORT).show()
                    Log.i("mp01error",t.message.toString())
                }
            })
        })
    }

}