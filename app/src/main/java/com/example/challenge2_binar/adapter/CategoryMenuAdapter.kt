package com.example.challenge2_binar.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challenge2_binar.R
import com.example.challenge2_binar.databinding.ItemKategoriMenuBinding
import com.example.challenge2_binar.api.modelCategory.KategoriData
import com.example.challenge2_binar.api.modelCategory.KategoriMenu
import com.example.challenge2_binar.util.MenuDiffUtil


class CategoryMenuAdapter(
    private val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var data = emptyList<KategoriData>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemKategoriMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuKategoriHolder(binding)
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewKategoriHolder = holder as MenuKategoriHolder
        viewKategoriHolder.bindContent(data[position] )

        Glide.with(context)
            .load(data[position].image_url)
            .into(holder.image)
    }

    override fun getItemCount(): Int = data.size



    fun setData(dataCategory: KategoriMenu) {
        val diffUtil = MenuDiffUtil(data, dataCategory.data)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        data = dataCategory.data
        diffUtilResult.dispatchUpdatesTo(this)
    }
    class MenuKategoriHolder(private val binding: ItemKategoriMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val image: ImageView = itemView.findViewById(R.id.imgViewCategory)
        fun bindContent(kategoriData: KategoriData) {
            binding.tvMenuKategori.text = kategoriData.nama
        }
    }


}


