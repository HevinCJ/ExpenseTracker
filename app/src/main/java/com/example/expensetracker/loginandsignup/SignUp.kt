package com.example.expensetracker.loginandsignup


import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.expensetracker.R
import com.example.expensetracker.ViewModel.sharedViewModel
import com.example.expensetracker.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SignUp : Fragment() {
    private var signUpBinding:FragmentSignUpBinding?=null
    private val binding get() = signUpBinding!!

    private var auth:FirebaseAuth?=null


    private val sharedviewmodel:sharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       signUpBinding = FragmentSignUpBinding.inflate(inflater,container,false)

        auth = FirebaseAuth.getInstance()





        binding.signupbtn.setOnClickListener{
            val email = binding.edttextemail.text.toString()
            val password = binding.edttextpassword.text.toString()
            val confirmpassword = binding.edttextpasswordconfirm.text.toString()

            if (email.isEmpty()) binding.edttextemail.error = "please fill the Email"
            if (password.isEmpty()) binding.edttextpassword.error = "please fill the Password"
            if (confirmpassword.isEmpty()) binding.edttextpasswordconfirm.error = "please fill the Password"

            authenticationForSignUp(email,password,confirmpassword)
        }


        binding.skipnowbutton1.setOnClickListener {
            findNavController().navigate(R.id.action_signUp_to_dashBoard)
        }

        binding.txtviewloginclicker.setOnClickListener {
            findNavController().navigate(R.id.action_signUp_to_login)
        }

        return(binding.root)
    }


    private fun authenticationForSignUp(email:String,password:String,confirmpassword:String) {

        if (sharedviewmodel.firebaseuservalidation(email, password, confirmpassword) && sharedviewmodel.firebasecheckPasswordCorrect(password,confirmpassword)){
            auth?.createUserWithEmailAndPassword(email,password)?.addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(requireContext(),"Successfully Registered",Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_signUp_to_dashBoard)
                }else{
                    Toast.makeText(requireContext(),it.exception?.message,Toast.LENGTH_SHORT).show()
                }
            }
        }else{
            Toast.makeText(requireContext(),"Please check the credentials",Toast.LENGTH_SHORT).show()
        }
    }




}