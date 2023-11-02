package com.example.challenge2_binar.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challenge2_binar.R
import com.example.challenge2_binar.adapter.KeranjangAdapter
import com.example.challenge2_binar.databinding.FragmentKeranjangBinding
import com.example.challenge2_binar.viewModel.KeranjangViewModel
import org.koin.android.ext.android.inject


class KeranjangFragment : Fragment() {
    private lateinit var binding: FragmentKeranjangBinding
    private val keranjangViewModel: KeranjangViewModel by inject()
    private lateinit var keranjangAdapter: KeranjangAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentKeranjangBinding.inflate(inflater, container, false)

        setupRecyclerView()

        binding.btnPesan.setOnClickListener {
            findNavController().navigate(R.id.action_keranjangFragment_to_konfirmasiPesananFragment)
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        keranjangAdapter = KeranjangAdapter(requireContext(),keranjangViewModel)
        binding.rvKeranjang.setHasFixedSize(true)
        binding.rvKeranjang.layoutManager = LinearLayoutManager(requireContext())
        binding.rvKeranjang.adapter = keranjangAdapter

        keranjangViewModel.getAllitems.observe(viewLifecycleOwner) {
            keranjangAdapter.data(it)
        }

        keranjangViewModel.itemLiveData.observe(viewLifecycleOwner) {

        }

        keranjangViewModel.totalPrice.observe(viewLifecycleOwner) {
            binding.tvTotalHargaPesanan.text = it.toString()
        }
    }
}