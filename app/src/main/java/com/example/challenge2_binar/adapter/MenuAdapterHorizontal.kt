package com.example.challenge2_binar.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challenge2_binar.R
import com.example.challenge2_binar.databinding.ItemKategoriMenuBinding
import com.example.challenge2_binar.ui.fragment.HomeFragment
import com.example.challenge2_binar.modelCategory.KategoriData


class MenuAdapterHorizontal(
    private val context: HomeFragment,
    private var arrayList: List<KategoriData?>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemKategoriMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuKategoriHolder(binding)
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewKategoriHolder = holder as MenuKategoriHolder
        viewKategoriHolder.bindContent(arrayList[position] as KategoriData)

        Glide.with(context)
            .load(arrayList[position]?.image_url)
            .into(holder.image)
    }

    override fun getItemCount(): Int = arrayList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<KategoriData?>){
        this.arrayList = data
        notifyDataSetChanged()
    }

    class MenuKategoriHolder(private val binding: ItemKategoriMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val image: ImageView = itemView.findViewById(R.id.imgViewCategory)


        fun bindContent(kategoriData: KategoriData) {

//        binding.imgView.setImageResource(kategoriData.image_url)
            binding.tvMenuKategori.text = kategoriData.nama
        }
    }


}


