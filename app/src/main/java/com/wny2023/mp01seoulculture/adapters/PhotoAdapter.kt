package com.wny2023.mp01seoulculture.adapters

import android.app.Activity
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.wny2023.mp01seoulculture.databinding.RecyclerPhotoBinding
import com.wny2023.mp01seoulculture.models.Photos

class PhotoAdapter(var context:Activity, var items:MutableList<Uri>):Adapter<PhotoAdapter.VH> (){

    inner class VH(var binding: RecyclerPhotoBinding):ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(RecyclerPhotoBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun getItemCount(): Int =items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        var item:Uri= items[position]
        Glide.with(context).load(item).into(holder.binding.photoRecycle)
    }
}