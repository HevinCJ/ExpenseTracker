package com.example.expensetracker.fragments.add

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.expensetracker.R
import com.example.expensetracker.ViewModel.mainViewModel
import com.example.expensetracker.ViewModel.sharedViewModel
import com.example.expensetracker.databinding.FragmentAddtransactionBinding
import com.example.expensetracker.firebasedatabase.UserData

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class addtransaction : Fragment() {

    private  var addtransaction:FragmentAddtransactionBinding?=null
    private val binding get() = addtransaction!!

    private val sharedViewModel: sharedViewModel by viewModels()
    private val mainViewModel: mainViewModel by viewModels()

    private var databasereference:DatabaseReference?=null
    private var auth:FirebaseAuth?=null
    private lateinit var id:String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

    addtransaction = FragmentAddtransactionBinding.inflate(layoutInflater,container,false)

        auth = FirebaseAuth.getInstance()
        databasereference = FirebaseDatabase.getInstance().getReference("Users")
        id = databasereference?.push()?.key.toString()




        setupspinnerdropdown()

        binding.addtransactionbutton.setOnClickListener {
            addtransactiontoFirebase()
        }



        getcurrentdate()



        return(binding.root)
    }

    private fun getcurrentdate() {
        binding.btndatepicker.setOnClickListener {
            sharedViewModel.showDatePickerDialogue(requireContext()){selecteddate->
                   binding.edttxtdate.editText?.setText(selecteddate)

            }
        }
    }

    private fun addtransactiontoFirebase() {
        val title = binding.titletxt.editText?.text.toString()
        val amount = binding.amounttxt.editText?.text.toString()
        val transactionType = binding.spinnerTranscation.selectedItem.toString()
        val date = binding.edttxtdate.editText?.text.toString()
        val note = binding.edttxtNote.editText?.text.toString()


        val user = UserData(id,title,amount,transactionType,date,note)

        if (sharedViewModel.userValidation(title,amount,transactionType,date,note)) {
            databasereference?.child(id)?.setValue(user)?.addOnCompleteListener {
                if (it.isSuccessful) {
                    findNavController().navigate(R.id.action_addtransaction_to_dashBoard)
                    Toast.makeText(requireContext(), "Added $title", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(
                        requireContext(),
                        it.exception?.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }else{
            Toast.makeText(requireContext(),"Please Fill the Fields", Toast.LENGTH_SHORT).show()
        }


    }






    private fun setupspinnerdropdown() {
        val items = resources.getStringArray(R.array.transactiontype)

        val spinnerAdapter = object :
            ArrayAdapter<String>(requireContext(),android.R.layout.simple_spinner_item,items){


            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view: TextView = super.getDropDownView(position, convertView, parent) as TextView
                view.setTextColor(Color.BLACK)
                return view
            }
        }

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerTranscation.adapter = spinnerAdapter

    }
}