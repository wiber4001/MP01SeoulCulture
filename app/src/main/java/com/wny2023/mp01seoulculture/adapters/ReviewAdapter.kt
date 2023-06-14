package com.wny2023.mp01seoulculture.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.wny2023.mp01seoulculture.databinding.RecyclerReviewItemBinding
import com.wny2023.mp01seoulculture.models.Review

class ReviewAdapter(var context: Activity, var items:MutableList<Review>) :Adapter<ReviewAdapter.VH>() {

    inner class VH(var binding:RecyclerReviewItemBinding):ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(RecyclerReviewItemBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun getItemCount(): Int =items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        var item:Review =items[position]
        Glide.with(context).load(item.reviewImgs[0]).into(holder.binding.imgEvent)
        holder.binding.tvEvtitle.text=item.reviewTitle
        holder.binding.tvPlaceEval2.text=item.reviewPlace
        holder.binding.tvEquipEval2.text=item.reviewEquip
        holder.binding.tvContEval2.text=item.reviewContent
        holder.binding.tvLongreview.text=item.reviewLong
    }
}