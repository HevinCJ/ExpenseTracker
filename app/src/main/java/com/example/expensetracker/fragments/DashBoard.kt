package com.example.expensetracker.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.R
import com.example.expensetracker.Utils.TransactionType
import com.example.expensetracker.ViewModel.mainViewModel
import com.example.expensetracker.ViewModel.sharedViewModel
import com.example.expensetracker.databinding.FragmentDashBoardBinding
import com.example.expensetracker.firebasedatabase.UserData
import com.example.expensetracker.fragments.adapter.TransactionAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class DashBoard : Fragment() {
    private lateinit var binding: FragmentDashBoardBinding
    private  val sharedViewModel: sharedViewModel by viewModels()
    private val adapter: TransactionAdapter by lazy { TransactionAdapter() }
    private var databasereference:DatabaseReference?=null
    private var auth:FirebaseAuth?=null
    private val mainviewmodel:mainViewModel by viewModels()





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashBoardBinding.inflate(layoutInflater,container,false)

        auth = FirebaseAuth.getInstance()
        databasereference = FirebaseDatabase.getInstance().getReference("Users")






        mainviewmodel.userData.observe(viewLifecycleOwner){user->
            adapter.setdata(user)
        }

        mainviewmodel._expense.observe(viewLifecycleOwner){expense->
            binding.txtviewexpense.text= expense.toString()
        }

        mainviewmodel.income.observe(viewLifecycleOwner){income->
            binding.txtviewincome.text=income.toString()
        }

        mainviewmodel.balance.observe(viewLifecycleOwner){balance->
            binding.txtviewbalance.text=balance.toString()
            Log.d("balance",balance.toString())
        }

        swipeToDelete(binding.recyclerView)


       binding.addFloatingButton.setOnClickListener {
           findNavController().navigate(R.id.action_dashBoard_to_addtransaction)
       }


        setuprecyclerview()


        return(binding.root)
    }

    private fun setuprecyclerview() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
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
                val item = adapter.datalist[viewHolder.adapterPosition]
                deleteDataFromFirebase(item)

            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeToDelete)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun deleteDataFromFirebase(item: UserData?) {
        lifecycleScope.launch(Dispatchers.IO){
            if (item != null) {
                databasereference?.child(item.id)?.removeValue()?.addOnSuccessListener {
                    showsnackbar(item)
                }?.addOnFailureListener {
                    Toast.makeText(requireContext(),"Failed to Updata:${item.title}",Toast.LENGTH_SHORT).show()
                }
            }
        }

    }


    private fun showsnackbar(item: UserData?) {
        val snackbar = Snackbar.make(requireView(),"Deleted ${item?.title}",Snackbar.LENGTH_LONG)
        snackbar.setAction("Undo"){
            if (item != null) {
                addToFirebaseSnackbar(item)
            }
        }.show()
    }

    private fun addToFirebaseSnackbar(item:UserData) {
        lifecycleScope.launch(Dispatchers.IO){
            val user = UserData(item.id,item.title,item.amount,item.type,item.date,item.note)

            databasereference?.child(item.id)?.setValue(user)?.addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(requireContext(),"Added ${item.title}",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(requireContext(),it.exception?.message.toString(),Toast.LENGTH_SHORT).show()
                }
            }
        }

    }



}