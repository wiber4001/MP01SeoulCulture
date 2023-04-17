package com.wny2023.mp01seoulculture.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.wny2023.mp01seoulculture.activities.ContentDetailActivity
import com.wny2023.mp01seoulculture.databinding.RecyclerContentItemBinding
import com.wny2023.mp01seoulculture.models.Item

class ContentAdapter (var context:Activity, var items:MutableList<Item>): Adapter<ContentAdapter.VH> () {

    inner class VH(var binding:RecyclerContentItemBinding): ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentAdapter.VH {
        return VH(RecyclerContentItemBinding.inflate(LayoutInflater.from(context),parent,false))
    }
    override fun getItemCount(): Int = items.size
    override fun onBindViewHolder(holder: ContentAdapter.VH, position: Int) {
        var item:Item = items[position]
        holder.binding.tvEvaddr.text=item.PLACE
        holder.binding.tvEvdistrict.text=item.GUNAME
        holder.binding.tvEvduring.text=item.DATE
        holder.binding.tvEvtitle.text=item.TITLE
        holder.binding.tvEvprogram.text=item.PROGRAM
        Glide.with(context).load(item.MAIN_IMG).into(holder.binding.imgEvent)

        holder.binding.root.setOnClickListener(View.OnClickListener {
            val intent=Intent(context,ContentDetailActivity::class.java)
            intent.putExtra("object",item)
            intent.run { context.startActivity(intent) }
        })
    }

}