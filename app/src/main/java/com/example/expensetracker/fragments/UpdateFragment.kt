package com.example.expensetracker.fragments

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
import androidx.navigation.fragment.navArgs
import com.example.expensetracker.R
import com.example.expensetracker.Utils.TransactionType
import com.example.expensetracker.ViewModel.mainViewModel
import com.example.expensetracker.ViewModel.sharedViewModel
import com.example.expensetracker.databinding.FragmentUpdateBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.lang.IllegalArgumentException


class UpdateFragment : Fragment() {

    private  var updatefrag:FragmentUpdateBinding ?=null
    private val binding get() = updatefrag!!

    private var database:DatabaseReference?=null
    private var auth:FirebaseAuth?=null

    private val args by navArgs<UpdateFragmentArgs>()
    private val sharedviewmodel:sharedViewModel by viewModels()
    private val mainviewmodel:mainViewModel by viewModels()
    private lateinit var id:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        updatefrag = FragmentUpdateBinding.inflate(layoutInflater,container,false)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Users")
        id = args.currenttransaction.id

        setupspinnerdropdown()
        showPreviewData()
        getcurrentdate()



        binding.updatetransactionbutton.setOnClickListener {
            updateTransaction()
        }




        return binding.root
    }


    private fun updateTransaction() {
        val title = binding.titletxt.editText?.text.toString()
        val amount = binding.amounttxt.editText?.text.toString()
        val type = binding.spinnerTranscation.selectedItem.toString()
        val date = binding.edttxtdate.editText?.text.toString()
        val note = binding.edttxtNote.editText?.text.toString()



        val user = mutableMapOf<String,Any>(
            "title" to title,
            "amount" to amount,
            "type" to type,
            "date" to date,
            "note" to note
        )

        if (sharedviewmodel.userValidation(title,amount,type,date,note)){
            database?.child(id)?.updateChildren(user)
                ?.addOnSuccessListener {
                    Toast.makeText(requireContext(), " Updated:$title", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_updateFragment_to_dashBoard)
                }
                ?.addOnFailureListener {
                    Toast.makeText(requireContext(), "Failed to Update:$title", Toast.LENGTH_SHORT).show()
                }
        }else{
            Toast.makeText(requireContext(), "Please fill the fields", Toast.LENGTH_SHORT).show()
        }


    }


    private fun showPreviewData() {
        binding.titletxt.editText?.setText(args.currenttransaction.title)
        binding.amounttxt.editText?.setText(args.currenttransaction.amount)
        binding.spinnerTranscation.setSelection(sharedviewmodel.parseTransactionTypeFromType(args.currenttransaction.type))
        binding.edttxtdate.editText?.setText(args.currenttransaction.date)
        binding.edttxtNote.editText?.setText(args.currenttransaction.note)
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

    private fun getcurrentdate() {
        binding.btndatepicker.setOnClickListener {
            sharedviewmodel.showDatePickerDialogue(requireContext()){selecteddate->
                binding.edttxtdate.editText?.setText(selecteddate)

            }
        }
    }


}