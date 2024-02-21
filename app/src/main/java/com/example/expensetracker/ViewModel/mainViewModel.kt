package com.example.expensetracker.ViewModel


import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.Repository.ExpenseRepository
import com.example.expensetracker.firebasedatabase.UserData
import com.example.expensetracker.fragments.DashBoard
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch

class mainViewModel(application: Application,dashBoard: DashBoard):AndroidViewModel(application) {

    private var databasereference:DatabaseReference?=null
    private var auth:FirebaseAuth?=null
    private lateinit var authid:String
    val userData = MutableLiveData<List<UserData>>()
    val _expense = MutableLiveData<Double>()
    val income = MutableLiveData<Double>()
    val balance = MutableLiveData<Double>()



    init {
        auth = FirebaseAuth.getInstance()
        authid = auth?.currentUser?.uid.toString()
        databasereference = FirebaseDatabase.getInstance().getReference("Users")
        getDataFromDatabase()
        getAllExpense()
        getAllIncome()
        _expense.observeForever { getAllBalance() }
        income.observeForever { getAllBalance() }
    }

    constructor(application: Application) : this(application,DashBoard())


    fun getDataFromDatabase(){

        viewModelScope.launch {
            databasereference?.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val userlist = mutableListOf<UserData>()
                    for (data in snapshot.children) {
                        val user = data.getValue(UserData::class.java)
                        user?.let {
                            userlist.add(user)
                        }
                    }
                    userData.value=userlist
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        }
    }

    fun getAllExpense(){
        viewModelScope.launch {
            val typeQuery = databasereference?.orderByChild("type")?.equalTo("Expense")

            typeQuery?.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val amountsList = mutableListOf<Double>()

                    for (transactionSnapshot in snapshot.children) {
                        val userData = transactionSnapshot.getValue(UserData::class.java)
                        userData?.let {
                            amountsList.add(it.amount.toDouble())
                        }
                    }


                    _expense.value= amountsList.sum()

                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }
    }

    fun getAllIncome(){
        viewModelScope.launch {
            val typeQuery = databasereference?.orderByChild("type")?.equalTo("Income")

            typeQuery?.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val amountsList = mutableListOf<Double>()

                    for (transactionSnapshot in snapshot.children) {
                        val userData = transactionSnapshot.getValue(UserData::class.java)
                        userData?.let {
                            amountsList.add(it.amount.toDouble())
                        }
                    }


                    income.value= amountsList.sum()

                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }
    }

    fun getAllBalance() {
        viewModelScope.launch {
            val expense = _expense.value ?: 0.0
            val income = income.value ?: 0.0

            val balanceValue = income - expense
            balance.postValue(balanceValue)

            Log.d("balancevalue", balanceValue.toString())
        }
    }




}