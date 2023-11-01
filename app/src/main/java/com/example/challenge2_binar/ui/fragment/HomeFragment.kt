package com.example.challenge2_binar.ui.fragment



import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challenge2_binar.R
import com.example.challenge2_binar.util.SharedPreference
import com.example.challenge2_binar.adapter.CategoryMenuAdapter
import com.example.challenge2_binar.adapter.ListMenuAdapter
import com.example.challenge2_binar.api.APIClient
import com.example.challenge2_binar.databinding.FragmentHomeBinding
import com.example.challenge2_binar.modelCategory.KategoriMenu
import com.example.challenge2_binar.user.User
import com.example.challenge2_binar.util.Status
import com.example.challenge2_binar.viewModel.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var kategoriMenuAdapter : CategoryMenuAdapter
    private lateinit var menuAdapter: ListMenuAdapter
    private lateinit var sharedPreference: SharedPreference
    private lateinit var  auth: FirebaseAuth
    private lateinit var  database: DatabaseReference
    private lateinit var uid : String
    private lateinit var user : User
    private  val viewModel: HomeViewModel by inject()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.btnProfile.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }

        kategoriMenuAdapter = CategoryMenuAdapter(this@HomeFragment, arrayListOf())
        binding.rvMenuKategori.setHasFixedSize(true)
        binding.rvMenuKategori.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvMenuKategori.adapter = kategoriMenuAdapter
        remoteGetCategory()
        remoteGetList()


        sharedPreference = SharedPreference(requireContext())
        viewModel.isGrid.value = sharedPreference.getPreferences()
        viewModel.isGrid.observe(viewLifecycleOwner) {
            setPrefLayout()
        }

        binding.rvMenu.setHasFixedSize(true)
        grid()
        setPrefLayout()

        return binding.root
    }

    private fun remoteGetCategory(){
        APIClient.instance.getCategory()
            .enqueue(object : Callback<KategoriMenu> {
                override fun onResponse(
                    call: Call<KategoriMenu>,
                    response: Response<KategoriMenu>
                ) {
                    val body = response.body()
                    Log.e("SimpleNetworking", Gson().toJson(body))
                    body?.let {
                        val data = body.data
                        val status = body.status != null
                        if (status) {
                            if (!data.isNullOrEmpty()) {
                                binding.progressBarCategory.isVisible = false
                                kategoriMenuAdapter.setData(data)
                            }
                        }
                    }
                }
                override fun onFailure(call: Call<KategoriMenu>, t: Throwable) {
                    binding.progressBarCategory.isVisible = false
                    Log.e("SimpleNetworking", t.message.toString())
                }
            })
    }

    @SuppressLint("FragmentLiveDataObserve")
    fun remoteGetList(){
        viewModel.getAllCategory().observe(this){

            when (it.status){
                Status.SUCCESS -> {
                    Log.e("SimpleNetworking", Gson().toJson(it.data))
                    binding.progressBarMenu.isVisible = false
                    it.data?.data?.let { it1 -> menuAdapter.setData(it1) }
                }
                Status.ERROR -> {
                    binding.progressBarMenu.isVisible = false
                    Log.e("SimpleNetworking", it.message.toString())
                }
                Status.LOADING -> {
                    binding.progressBarMenu.isVisible = true
                }
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        database = FirebaseDatabase.getInstance().getReference("user")

        if(uid.isNotEmpty()){
            getDataUser()
        }
    }

    private fun getDataUser() {
        database.child(uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                user = snapshot.getValue(User::class.java)!!
                binding.tvUsername.text = user.username
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }


    private fun viewLayout(isGrid: Boolean){
        if (isGrid) {
            grid()
            binding.imageList.setImageResource( R.drawable.ic_linear)
        } else {
            linear()
            binding.imageList.setImageResource( R.drawable.ic_grid)
        }
    }

    private fun setPrefLayout() {
        val buttonLayout = binding.imageList
        val setLayout = viewModel.isGrid.value?: sharedPreference.getPreferences()

        viewLayout(setLayout)
        buttonLayout.setOnClickListener {
            val updateLayout = !setLayout
            viewModel.isGrid.value = updateLayout
            sharedPreference.setPreferences(updateLayout)
        }
    }


    private fun linear() {
        binding.rvMenu.layoutManager = LinearLayoutManager(requireActivity())
        menuAdapter = ListMenuAdapter(this@HomeFragment,arrayListOf(), viewModel.isGrid.value ?: false, listener = { pickItem ->
                val bundle = bundleOf("pickItem" to pickItem)
                findNavController().navigate(R.id.action_homeFragment_to_detailMenuFragment, bundle)
        })
        binding.rvMenu.adapter = menuAdapter
        remoteGetList()
    }


    private fun grid() {
        binding.rvMenu.layoutManager = GridLayoutManager(requireActivity(), 2)
        menuAdapter = ListMenuAdapter(this@HomeFragment,arrayListOf(), viewModel.isGrid.value ?: false, listener = { pickItem ->
            val bundle = bundleOf("pickItem" to pickItem)
            findNavController().navigate(R.id.action_homeFragment_to_detailMenuFragment, bundle)
        })
        binding.rvMenu.adapter = menuAdapter
        remoteGetList()
    }

}