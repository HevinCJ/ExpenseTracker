package com.example.expensetracker.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.R
import com.example.expensetracker.ViewModel.mainViewModel
import com.example.expensetracker.ViewModel.sharedViewModel
import com.example.expensetracker.database.Entity
import com.example.expensetracker.databinding.FragmentDashBoardBinding
import com.example.expensetracker.fragments.add.TransactionAdapter
import com.google.android.material.snackbar.Snackbar

class DashBoard : Fragment() {
    private lateinit var binding: FragmentDashBoardBinding
    private val transactionAdapter: TransactionAdapter by lazy { TransactionAdapter() }
    private  val viewmodel: mainViewModel by viewModels()
    private  val sharedViewModel: sharedViewModel by viewModels()
    private val adapter:TransactionAdapter by lazy { TransactionAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashBoardBinding.inflate(layoutInflater,container,false)




       binding.addFloatingButton.setOnClickListener {
           findNavController().navigate(R.id.action_dashBoard_to_addtransaction)
       }

        observeDashboard()
        setuprecycleriview()




        return(binding.root)
    }

    private fun observeDashboard() {
        viewmodel.getallIncome.observe(viewLifecycleOwner){ income ->
            if (income != null) {
                binding.txtviewincome.text = income.toString()
            }
        }

        viewmodel.getallExpense.observe(viewLifecycleOwner){ expense ->
            if (expense != null) {
                binding.txtviewexpense.text = expense.toString()
            }
        }

        viewmodel.getbalance.observe(viewLifecycleOwner){ balance ->
            if (balance!=null){
                binding.txtviewbalance.text = balance.toString()
            }
        }

    }



    private fun setuprecycleriview() {
        binding.recyclerView.adapter = transactionAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewmodel.getalldata.observe(viewLifecycleOwner){data->
            transactionAdapter.setdata(data)
        }
        swipeToDelete(binding.recyclerView)
    }



    private fun swipeToDelete(recyclerView: RecyclerView){
        val swipeToDelete = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = transactionAdapter.datalist[viewHolder.adapterPosition]
                viewmodel.deleteTransaction(item)
                showsnackbar(item)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeToDelete)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }




    private fun showsnackbar(item: Entity) {
        val snackbar = Snackbar.make(requireView(),"Deleted ${item.title}",Snackbar.LENGTH_LONG)
        snackbar.setAction("Undo"){
            viewmodel.insertTransaction(item)
        }.show()
    }


}