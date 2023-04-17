//package com.wny2023.mp01seoulculture.adapters
//
//import android.app.Activity
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import androidx.recyclerview.widget.RecyclerView.Adapter
//import androidx.recyclerview.widget.RecyclerView.ViewHolder
//import com.bumptech.glide.Glide
//import com.wny2023.mp01seoulculture.databinding.RecyclerContentItemBinding
//import com.wny2023.mp01seoulculture.models.Item
//
//class ContentAdapter (var context:Activity, var items:MutableList<Item>): Adapter<ContentAdapter.VH> () {

//    inner class VH(var binding:RecyclerContentItemBinding):RecyclerView.ViewHolder(binding.root){
//        fun bind(item:Item) {
//            binding.tvEvaddr.text=item.place
//            binding.tvEvdistrict.text=item.guname
//            binding.tvEvduring.text=item.date
//            binding.tvEvtitle.text=item.title
//            binding.tvEvprogram.text=item.program
//            Glide.with(context).load(item.main_img).into(binding.imgEvent)
//        }
//    }
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentAdapter.VH {
//        val itemView:View = LayoutInflater.from(context).inflate(R.layout.recycler_item_main,parent,false)
//        return VH(RecyclerContentItemBinding.bind())
//    }
//
//    override fun onBindViewHolder(holder: ContentAdapter.VH, position: Int) {
//        var item:Item = items[position]
//        holder.bind(item)
//    }
//
//    override fun getItemCount(): Int = items.size
//
//}