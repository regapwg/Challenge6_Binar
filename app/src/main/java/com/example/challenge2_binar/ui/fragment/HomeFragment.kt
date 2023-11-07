package com.example.challenge2_binar.ui.fragment



import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challenge2_binar.R
import com.example.challenge2_binar.util.ListViewSharedPreference
import com.example.challenge2_binar.adapter.CategoryMenuAdapter
import com.example.challenge2_binar.adapter.ListMenuAdapter
import com.example.challenge2_binar.databinding.FragmentHomeBinding
import com.example.challenge2_binar.user.User
import com.example.challenge2_binar.util.Resources
import com.example.challenge2_binar.viewModel.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject



class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var kategoriMenuAdapter : CategoryMenuAdapter
    private lateinit var menuAdapter: ListMenuAdapter
    private lateinit var  auth: FirebaseAuth
    private lateinit var  database: DatabaseReference
    private lateinit var uid : String
    private lateinit var user : User
    private  val viewModel: HomeViewModel by inject()
    private val listViewSharedPreference: ListViewSharedPreference by inject()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        loadDataCategory()
        loadDataList()
        setupRvCategory()

        viewModel.isGrid.value = listViewSharedPreference.getPreferences()
        viewModel.isGrid.observe(viewLifecycleOwner) {
            setPrefLayout()
        }

        binding.rvMenu.setHasFixedSize(true)
        grid()
        setPrefLayout()

        binding.btnProfile.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }

        return binding.root
    }

    private fun loadDataCategory() {
        Log.d("dataCategory", "list category from database")
        lifecycleScope.launch {
            viewModel.readCategory.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    kategoriMenuAdapter.setData(database.first().categoryMenu)
                    binding.progressBarCategory.isVisible = false
                } else {
                    remoteGetCategory()
                }
            }
        }
    }

    private fun remoteGetCategory(){
        viewModel.getCategoryMenu()
        viewModel.categoryMenu.observe(viewLifecycleOwner){response->
            when (response){
                is Resources.Success -> {
                    Log.e("SimpleNetworking", Gson().toJson(response.data))
                    binding.progressBarCategory.isVisible = false
                    response.data?.let { kategoriMenuAdapter.setData(it) }
                }
                is Resources.Error -> {
                    binding.progressBarCategory.isVisible = false
                    Log.e("SimpleNetworking", response.message.toString())
                    loadDataList()
                }
                is Resources.Loading -> {
                    binding.progressBarCategory.isVisible = true
                }
            }
        }
    }



    private fun loadDataList() {
        Log.d("dataMenu", "list menu from database")
        lifecycleScope.launch {
            viewModel.readMenu.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    menuAdapter.setData(database.first().listMenu)
                    binding.progressBarMenu.isVisible = false
                } else {
                    remoteGetList()
                }
            }
        }
    }

    private fun remoteGetList(){
        viewModel.getListMenu()
        viewModel.listMenu.observe(viewLifecycleOwner){response->
            when (response){
                is Resources.Success -> {
                    Log.e("SimpleNetworking", Gson().toJson(response.data))
                    binding.progressBarMenu.isVisible = false
                    response.data?.let { menuAdapter.setData(it) }
                }
                is Resources.Error -> {
                    binding.progressBarMenu.isVisible = false
                    Log.e("SimpleNetworking", response.message.toString())
                    loadDataList()
                }
                is Resources.Loading -> {
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
        val setLayout = viewModel.isGrid.value?: listViewSharedPreference.getPreferences()

        viewLayout(setLayout)
        buttonLayout.setOnClickListener {
            val updateLayout = !setLayout
            viewModel.isGrid.value = updateLayout
            listViewSharedPreference.setPreferences(updateLayout)
        }
    }

    private fun setupRvCategory(){
        kategoriMenuAdapter = CategoryMenuAdapter(requireContext())
        binding.rvMenuKategori.setHasFixedSize(true)
        binding.rvMenuKategori.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvMenuKategori.adapter = kategoriMenuAdapter
    }

    private fun linear() {
        binding.rvMenu.layoutManager = LinearLayoutManager(requireActivity())
        menuAdapter = ListMenuAdapter(this@HomeFragment, viewModel.isGrid.value ?: false, listener = { pickItem ->
                val bundle = bundleOf("pickItem" to pickItem)
                findNavController().navigate(R.id.action_homeFragment_to_detailMenuFragment, bundle)
        })
        binding.rvMenu.adapter = menuAdapter
        remoteGetList()
    }

    private fun grid() {
        binding.rvMenu.layoutManager = GridLayoutManager(requireActivity(), 2)
        menuAdapter = ListMenuAdapter(this@HomeFragment, viewModel.isGrid.value ?: false, listener = { pickItem ->
            val bundle = bundleOf("pickItem" to pickItem)
            findNavController().navigate(R.id.action_homeFragment_to_detailMenuFragment, bundle)
        })
        binding.rvMenu.adapter = menuAdapter
        remoteGetList()
    }

}