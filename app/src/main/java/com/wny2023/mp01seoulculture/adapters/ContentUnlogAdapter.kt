package com.wny2023.mp01seoulculture.adapters

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.wny2023.mp01seoulculture.G
import com.wny2023.mp01seoulculture.activities.ContentDetailActivity
import com.wny2023.mp01seoulculture.activities.ContentDetailUnlogActivity
import com.wny2023.mp01seoulculture.databinding.RecyclerContentItemBinding
import com.wny2023.mp01seoulculture.models.Item
import com.wny2023.mp01seoulculture.network.RetrofitHelper
import com.wny2023.mp01seoulculture.network.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ContentUnlogAdapter (var context:Activity, var items:MutableList<Item>): Adapter<ContentUnlogAdapter.VH> () {

    inner class VH(var binding:RecyclerContentItemBinding): ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentUnlogAdapter.VH {
        return VH(RecyclerContentItemBinding.inflate(LayoutInflater.from(context),parent,false))
    }
    override fun getItemCount(): Int = items.size
    override fun onBindViewHolder(holder: ContentUnlogAdapter.VH, position: Int) {
        var item: Item = items[position]
        holder.binding.tvEvcodename.text=item.CODENAME
        holder.binding.tvEvaddr.text = item.PLACE
        holder.binding.tvEvdistrict.text = item.GUNAME
        holder.binding.tvEvduring.text = item.DATE
        holder.binding.tvEvtitle.text = item.TITLE
        holder.binding.tvEvprogram.text = item.PROGRAM
        Glide.with(context).load(item.MAIN_IMG).into(holder.binding.imgEvent)

        holder.binding.root.setOnClickListener({
            val intent = Intent(context, ContentDetailUnlogActivity::class.java)
            intent.putExtra("object", item)
            intent.run { context.startActivity(intent) }
        })
    }
}