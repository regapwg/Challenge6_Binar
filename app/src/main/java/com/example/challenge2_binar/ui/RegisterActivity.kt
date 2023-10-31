package com.example.challenge2_binar.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.challenge2_binar.databinding.ActivityRegisterBinding
import com.example.challenge2_binar.user.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class RegisterActivity : AppCompatActivity() {

    lateinit var binding : ActivityRegisterBinding
    private lateinit var  auth: FirebaseAuth
    private lateinit var database : FirebaseDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        binding.btnRegister.setOnClickListener {
            val username = binding.inputUsername.text.toString()
            val email = binding.inputEmail.text.toString()
            val password = binding.inputPassword.text.toString()
            val noTlp = binding.inputNoTlp.text.toString()


            if(username.isEmpty()){
                binding.inputUsername.error = "Input nama anda terlebih dahulu!"
                binding.inputUsername.requestFocus()
                return@setOnClickListener
            }

            if(email.isEmpty() ){
                binding.inputEmail.error = "Input email terlebih dahulu!"
                binding.inputEmail.requestFocus()
                return@setOnClickListener
            }

            if(password.isEmpty()){
                binding.inputPassword.error = "Input password terlebih dahulu!"
                binding.inputPassword.requestFocus()
                return@setOnClickListener
            }

            if(noTlp.isEmpty()){
                binding.inputNoTlp.error = "Input nomer telepon terlebih dahulu!"
                binding.inputNoTlp.requestFocus()
                return@setOnClickListener
            }

            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.inputEmail.error = "Input email tidak valid!"
                binding.inputEmail.requestFocus()
                return@setOnClickListener
            }


            if(password.length < 6){
                binding.inputPassword.error = "Input password minimal 6 karakter!"
                binding.inputPassword.requestFocus()
                return@setOnClickListener
            }

            registerFirebase(email, password, username, noTlp)
        }
    }

    private fun registerFirebase(email: String, password: String, username: String, noTlp: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { it ->
                if (it.isSuccessful){
                    val databaseRef = database.reference.child("user").child(auth.currentUser!!.uid)
                    val user = User(username,  email, noTlp)

                    databaseRef.setValue(user).addOnCompleteListener{
                        if(it.isSuccessful){
                            Toast.makeText(this, "Register Berhasil!", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)

                        }else{
                            Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}