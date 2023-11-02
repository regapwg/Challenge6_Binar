package com.example.challenge2_binar.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.challenge2_binar.R
import com.example.challenge2_binar.databinding.FragmentDetailMenuBinding
import com.example.challenge2_binar.api.produk.ListData
import com.example.challenge2_binar.viewModel.DetailViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject

class DetailMenuFragment : Fragment() {
    private var _binding: FragmentDetailMenuBinding? = null
    private val binding get() = _binding!!
    private  val detailViewModel: DetailViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailMenuBinding.inflate(inflater, container, false)

        detailViewModel.counter.observe(viewLifecycleOwner) { result ->
            binding.tvTotal.text = result.toString()
        }

        detailViewModel.totalPrice.observe(viewLifecycleOwner) { newTotalPrice ->
            binding.tvTotalHarga.text = newTotalPrice.toString()
        }


        incrementCount()
        decrementCount()

        return binding.root
    }


    @Suppress("DEPRECATION")
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataDetail = arguments?.getParcelable<ListData?>("pickItem")
        dataDetail?.let {
            binding.tvMenuu.text = dataDetail.nama
            binding.tvNamaToko.text = "Binar Cafe"
            binding.tvHarga.text = dataDetail.harga_format.toString()
            binding.tvDescription.text = dataDetail.detail
            binding.tvTotalHarga.text = dataDetail.harga.toString()
            binding.tvAlamat.text = dataDetail.alamat_resto
            detailViewModel.itemMenu(it)
            Glide.with(this)
                .load(dataDetail.image_url)
                .into(binding.imgDetail)
        }
        submitChart()
        buttonUpBack()
    }

    private fun incrementCount() {
        binding.icPlus.setOnClickListener {
            detailViewModel.incrementCount()
            detailViewModel.updateTotalPrice()

        }
    }

    private fun decrementCount() {
        binding.icMinus.setOnClickListener {
            detailViewModel.decrementCount()
            detailViewModel.updateTotalPrice()
        }
    }

    private fun submitChart() {
        binding.buttonKeranjang.setOnClickListener {
            detailViewModel.addToCart()
            findNavController().navigate(R.id.action_detailMenuFragment_to_keranjangFragment)
            Snackbar.make(it, "Item Ditambahkan ke Keranjang!", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val bottomNavigationView: BottomNavigationView? = activity?.findViewById(R.id.navBarBottom)
        bottomNavigationView?.visibility = View.GONE
    }

    override fun onDetach() {
        super.onDetach()
        val bottomNavigationView: BottomNavigationView? = activity?.findViewById(R.id.navBarBottom)
        bottomNavigationView?.visibility = View.VISIBLE
    }

    private fun buttonUpBack() {
        binding.icBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}