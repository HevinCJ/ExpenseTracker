package com.example.expensetracker.Repository

import androidx.lifecycle.LiveData
import com.example.expensetracker.database.Entity
import com.example.expensetracker.database.ExpenseDao
import kotlin.math.exp

class ExpenseRepository(private val expenseDao: ExpenseDao) {

     fun insert(entity: Entity){
        expenseDao.insert(entity)
    }

     fun update(entity: Entity){
        expenseDao.update(entity)
    }

     fun delete(entity: Entity){
        expenseDao.delete(entity)
    }

    val getalldata:LiveData<List<Entity>> = expenseDao.getalldatafromtable()

    fun getAllIncome(): LiveData<Double> {
        return expenseDao.getAllIncome("Income")
    }

    fun getAllExpense(): LiveData<Double> {
        return expenseDao.getAllIncome("Expense")
    }

    fun getAllBalance():LiveData<Double>{
        return expenseDao.getBalance()
    }
}