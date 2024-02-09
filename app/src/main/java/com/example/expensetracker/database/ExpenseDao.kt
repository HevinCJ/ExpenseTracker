package com.example.expensetracker.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface ExpenseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(Entity: Entity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(entity: Entity)

    @Delete
    fun delete(entity: Entity)

    @Query("SELECT * FROM ExpenseTracker ORDER BY ID DESC")
    fun getalldatafromtable():LiveData<List<Entity>>

    @Query("SELECT SUM(amount) FROM ExpenseTracker WHERE type = :type")
    fun getAllIncome(type: String): LiveData<Double>

    @Query("SELECT SUM(amount) FROM ExpenseTracker WHERE type = :type")
    fun getAllExpense(type: String): LiveData<Double>

    @Query("SELECT SUM(CASE WHEN type = 'Income' THEN amount ELSE 0 END) - SUM(CASE WHEN type = 'Expense' THEN amount ELSE 0 END) FROM ExpenseTracker")
    fun getBalance(): LiveData<Double>






}