package com.example.expensetracker.fragments.add

import android.graphics.Color
import android.os.Bundle
import android.util.Log
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
import com.example.expensetracker.database.Entity
import com.example.expensetracker.databinding.FragmentAddtransactionBinding


class addtransaction : Fragment() {

    private  var addtransaction:FragmentAddtransactionBinding?=null
    private val binding get() = addtransaction!!

    private val sharedViewModel: sharedViewModel by viewModels()
    private val mainViewModel: mainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

    addtransaction = FragmentAddtransactionBinding.inflate(layoutInflater,container,false)




        setupspinnerdropdown()

        binding.addtransactionbutton.setOnClickListener {
            addtransactiontodb()
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

    private fun addtransactiontodb() {
        val title = binding.titletxt.editText?.text.toString()
        val amount = binding.amounttxt.editText?.text.toString().toDouble()
        val transactionType = binding.spinnerTranscation.selectedItem.toString()
        val selecteddate = binding.edttxtdate.editText?.text.toString()
        val note = binding.edttxtNote.editText?.text.toString()

        val data = Entity(0,title,amount,sharedViewModel.parseTransactionTypeFromposition(transactionType),selecteddate,note)
        Log.d("validationkey","$title,$amount,$transactionType,$selecteddate,$note")

        if (sharedViewModel.userValidation(title,amount,transactionType,selecteddate,note)){
            mainViewModel.insertTransaction(data)
            Toast.makeText(requireContext(),"Added $title to the $transactionType",Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addtransaction_to_dashBoard)
        }else{
            Toast.makeText(requireContext(),"PLEASE FILL THE FIELDS",Toast.LENGTH_SHORT).show()

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