package com.example.challenge2_binar.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.challenge2_binar.R
import com.example.challenge2_binar.databinding.FragmentProfileBinding
import com.example.challenge2_binar.user.User
import com.example.challenge2_binar.util.LoginSharedPreference
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.koin.android.ext.android.inject


class ProfileFragment : Fragment() {

    private var _binding : FragmentProfileBinding? = null
    private val binding get()  = _binding!!
    private lateinit var  auth: FirebaseAuth
    private lateinit var  database: DatabaseReference
    private lateinit var uid : String
    private lateinit var user : User

    private val sharedPreference: LoginSharedPreference by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        database = FirebaseDatabase.getInstance().getReference("user")

        if(uid.isNotEmpty()){
            getDataUser()
        }

        binding.btnLogout.setOnClickListener {
            logout()
        }
    }

    private fun getDataUser() {
        database.child(uid).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                user = snapshot.getValue(User::class.java)!!
                binding.txtUsername.text = user.username
                binding.txtEmail.text = user.email
                binding.txtNoTelp.text = user.noTlp
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun logout() {
        sharedPreference.clearPreferences()
        auth = FirebaseAuth.getInstance()
        auth.signOut()
        findNavController().navigate(R.id.action_profileFragment_to_loginActivity)
        activity?.finish()

    }


}