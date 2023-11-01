package com.example.challenge2_binar.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challenge2_binar.R
import com.example.challenge2_binar.databinding.ItemMenuGridBinding
import com.example.challenge2_binar.databinding.ItemMenuListBinding
import com.example.challenge2_binar.ui.fragment.HomeFragment
import com.example.challenge2_binar.produk.ListData

class NewAdapter(
    private val context: HomeFragment,
    private var data: List<ListData?>,
    private val isGrid: Boolean,
    private var listener: (ListData) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (isGrid) {
            val binding =
                ItemMenuGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            GridMenuHolder(binding)

        } else {
            val binding =
                ItemMenuListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            LinearMenuHolder(binding)
        }

    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (isGrid) {
            val gridHolder = holder as GridMenuHolder
            gridHolder.onBind(data[position] as ListData)

            val listenerItem = data[position]

            Glide.with(context)
                .load(data[position]?.image_url)
                .into(holder.image)

            holder.itemView.setOnClickListener {
                listener(listenerItem as ListData)
            }

        } else {
            val linearholder = holder as LinearMenuHolder
            linearholder.onBind(data[position] as ListData)
            val listenerItem = data[position]

            Glide.with(context)
                .load(data[position]?.image_url)
                .into(holder.image)

            holder.itemView.setOnClickListener {
                listener(listenerItem as ListData)
            }
        }
    }

    override fun getItemCount(): Int = data.size


    @SuppressLint("NotifyDataSetChanged")
    fun setData(datalist: List<ListData?>) {
        this.data = datalist
        notifyDataSetChanged()
    }

//    interface OnAdapterListener{
//        fun onClick(data: ListData?)
//    }


    class GridMenuHolder(private val binding: ItemMenuGridBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val image: ImageView = itemView.findViewById(R.id.imgViewCategory)
        fun onBind(menuList: ListData) {
            binding.tvMenu.text = menuList.nama
            binding.tvHarga.text = menuList.harga_format.toString()
        }

    }

    class LinearMenuHolder(private val binding: ItemMenuListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val image: ImageView = itemView.findViewById(R.id.img_view_list)
        fun onBind(menuList: ListData) {
            binding.tvMenuu.text = menuList.nama
            binding.tvHarga.text = menuList.harga_format.toString()
        }


    }
}

