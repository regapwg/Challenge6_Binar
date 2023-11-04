package com.example.challenge2_binar.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challenge2_binar.R
import com.example.challenge2_binar.database.cartDb.SimpleChart
import com.example.challenge2_binar.databinding.ItemKeranjangBinding
import com.example.challenge2_binar.viewModel.KeranjangViewModel
import com.google.android.material.snackbar.Snackbar

class KeranjangAdapter(private val context: Context,
                       private val keranjangViewModel: KeranjangViewModel) :
    RecyclerView.Adapter<KeranjangAdapter.KeranjangHolder>() {

    private var simpleChart: List<SimpleChart> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeranjangHolder {
        val binding =
            ItemKeranjangBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return KeranjangHolder(binding)
    }

    override fun onBindViewHolder(holder: KeranjangHolder, position: Int) {
        val viewKeranjangHolder = simpleChart[position]
        holder.bindContent(viewKeranjangHolder)

        Glide.with(context)
            .load(simpleChart[position].itemImage)
            .into(holder.image)

        holder.btnDelete.setOnClickListener {
                keranjangViewModel.delete(viewKeranjangHolder.itemId)
                Snackbar.make(it, "Item dihapus dari keranjang!", Snackbar.LENGTH_SHORT).show()
        }

        holder.btnPlus.setOnClickListener {
            val updateValue = viewKeranjangHolder.itemQuantity + 1
            viewKeranjangHolder.itemQuantity = updateValue
            keranjangViewModel.update(viewKeranjangHolder)
            holder.quantity.text = updateValue.toString()
            viewKeranjangHolder.totalPrice = viewKeranjangHolder.itemPrice * updateValue
            holder.harga.text = viewKeranjangHolder.totalPrice.toString()
        }

        holder.btnMinus.setOnClickListener {
            if (viewKeranjangHolder.itemQuantity > 1) {
                val updateValue = viewKeranjangHolder.itemQuantity - 1
                viewKeranjangHolder.itemQuantity = updateValue
                viewKeranjangHolder.totalPrice = viewKeranjangHolder.itemPrice * updateValue
                keranjangViewModel.update(viewKeranjangHolder)
                holder.quantity.text = updateValue.toString()
                viewKeranjangHolder.totalPrice = viewKeranjangHolder.itemPrice * updateValue
                holder.harga.text = viewKeranjangHolder.totalPrice.toString()
            }
        }
    }

    override fun getItemCount(): Int = simpleChart.size

    @SuppressLint("NotifyDataSetChanged")
    fun data(simpleChart: List<SimpleChart>) {
        this.simpleChart = simpleChart
        notifyDataSetChanged()
    }

    class KeranjangHolder(private val binding: ItemKeranjangBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val btnDelete: ImageView = itemView.findViewById(R.id.icDelete)
        val btnPlus: ImageView = itemView.findViewById(R.id.icPlus)
        val btnMinus: ImageView = itemView.findViewById(R.id.icMinus)
        val quantity: TextView = itemView.findViewById(R.id.tvTotalPesanann)
        val harga: TextView = itemView.findViewById(R.id.tv_harga)
        val image: ImageView = itemView.findViewById(R.id.img_view_list)


        fun bindContent(simpleChart: SimpleChart) {
            binding.tvMenuu.text = simpleChart.itemName
            binding.tvHarga.text = simpleChart.totalPrice.toString()
            binding.tvTotalPesanann.text = simpleChart.itemQuantity.toString()
        }
    }

}


