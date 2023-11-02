package com.example.challenge2_binar.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challenge2_binar.adapter.KonfirmasiKeranjangAdapter
import com.example.challenge2_binar.api.APIClient
import com.example.challenge2_binar.databinding.FragmentKonfirmasiPesananBinding
import com.example.challenge2_binar.api.order.Order
import com.example.challenge2_binar.api.order.OrderRequest
import com.example.challenge2_binar.api.order.OrderResponse
import com.example.challenge2_binar.user.User
import com.example.challenge2_binar.viewModel.KeranjangViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class KonfirmasiPesananFragment : Fragment() {

    private var _binding: FragmentKonfirmasiPesananBinding? = null
    private val binding get() = _binding!!
    private  val keranjangViewModel: KeranjangViewModel by inject()
    private lateinit var keranjangAdapter: KonfirmasiKeranjangAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var uid: String
    private lateinit var user: User

    private var arrayListOrder = ArrayList<Order>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentKonfirmasiPesananBinding.inflate(inflater, container, false)


        setupRecyclerView()
        buttonUpBack()

        val totalHarga = keranjangViewModel.totalPrice
        keranjangViewModel.getAllitems.observe(viewLifecycleOwner) { simpleChart ->
            arrayListOrder.add(
                Order(
                    "", simpleChart[0].itemPrice,
                    simpleChart[0].itemName, simpleChart[0].itemQuantity
                )
            )
        }


        binding.btnPesanFix.setOnClickListener {
            val orderRequest = OrderRequest(
                username = user.username,
                total = totalHarga,
                orders = arrayListOrder
            )

            APIClient.instance.postOrder(orderRequest).enqueue(object : Callback<OrderResponse> {
                override fun onResponse(
                    call: Call<OrderResponse>,
                    response: Response<OrderResponse>
                ) {
                    val order = response.body()
                    val dialog = DialogFragment()
                    dialog.show(childFragmentManager, "dialog")
                    Toast.makeText(context, order?.message, Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                    Toast.makeText(context, "Response gagal", Toast.LENGTH_SHORT).show()
                }
            })
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        database = FirebaseDatabase.getInstance().getReference("user")

        if (uid.isNotEmpty()) {
            getDataUser()
        }
    }


    private fun setupRecyclerView() {
        keranjangAdapter =
            KonfirmasiKeranjangAdapter(requireContext(), keranjangViewModel)
        binding.rvKeranjang.setHasFixedSize(true)
        binding.rvKeranjang.layoutManager = LinearLayoutManager(requireContext())
        binding.rvKeranjang.adapter = keranjangAdapter

        keranjangViewModel.getAllitems.observe(viewLifecycleOwner) {
            keranjangAdapter.data(it)
        }

        keranjangViewModel.itemLiveData.observe(viewLifecycleOwner) {}

        keranjangViewModel.totalPrice.observe(viewLifecycleOwner) {
            binding.tvTotallPembayaran.text = it.toString()
        }
    }


    private fun getDataUser() {
        database.child(uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                user = snapshot.getValue(User::class.java)!!
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }


    private fun buttonUpBack() {
        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}