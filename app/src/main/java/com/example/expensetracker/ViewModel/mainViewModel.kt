package com.example.expensetracker.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.Repository.ExpenseRepository
import com.example.expensetracker.database.Entity
import com.example.expensetracker.database.ExpenseDao
import com.example.expensetracker.database.ExpenseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class mainViewModel(application: Application):AndroidViewModel(application) {

    private var expenseRepository: ExpenseRepository
    private var expenseDao: ExpenseDao = ExpenseDatabase.getinstance(application).expensedao()
    val getalldata:LiveData<List<Entity>>
    val getallIncome:LiveData<Double>
    val getallExpense:LiveData<Double>
    val getbalance:LiveData<Double>

    init {
        expenseRepository = ExpenseRepository(expenseDao)
        getalldata = expenseRepository.getalldata
        getallIncome = expenseRepository.getAllIncome()
        getallExpense = expenseRepository.getAllExpense()
        getbalance=expenseRepository.getAllBalance()
    }


     fun insertTransaction(entity: Entity){
        viewModelScope.launch(Dispatchers.IO){
            expenseRepository.insert(entity)
        }
    }

     fun updateTransaction(entity: Entity){
        viewModelScope.launch(Dispatchers.IO){
            expenseRepository.update(entity)
        }
    }

     fun deleteTransaction(entity: Entity){
        viewModelScope.launch(Dispatchers.IO){
            expenseRepository.delete(entity)
        }
    }









}