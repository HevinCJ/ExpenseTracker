package com.example.expensetracker.loginandsignup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.expensetracker.R
import com.example.expensetracker.ViewModel.sharedViewModel
import com.example.expensetracker.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class Login : Fragment() {
    private var fragmentlogin:FragmentLoginBinding?=null
    private val binding get() = fragmentlogin!!

    private var auth:FirebaseAuth?=null

    private val sharedviewmodel:sharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentlogin = FragmentLoginBinding.inflate(inflater,container,false)

        auth= FirebaseAuth.getInstance()

        binding.skipnowbutton1.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_dashBoard)
        }

        if (auth?.currentUser!=null){
            findNavController().navigate(R.id.action_login_to_dashBoard)
            binding.txtviewname.text=auth!!.currentUser?.displayName.toString()

        }


        binding.loginbutton.setOnClickListener {
            val email= binding.edttextemail.text.toString()
            val password = binding.edttextpassword.text.toString()

            if (email.isEmpty()) binding.edttextemail.error="please fill the email"
            if (password.isEmpty()) binding.edttextpassword.error="please fill the password"
            loginIntoFirebase(email,password)

        }




        return(binding.root)
    }

    private fun loginIntoFirebase(email:String,password:String) {


        if (sharedviewmodel.firebaseuservalidation(email,password,password)){
            auth?.signInWithEmailAndPassword(email,password)?.addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(requireContext(),"Successfully Logged Im",Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_login_to_dashBoard)
                    binding.edttextpassword.text.clear()
                }else{
                    Toast.makeText(requireContext(),it.exception?.localizedMessage,Toast.LENGTH_SHORT).show()
                }
            }
        }else{
            Toast.makeText(requireContext(),"please check the credentials",Toast.LENGTH_SHORT).show()
        }

    }


}