package com.example.expensetracker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [Entity::class], version = 1, exportSchema = false)
@TypeConverters(TransactionTypeConvertor::class)
abstract class ExpenseDatabase:RoomDatabase() {

    abstract fun expensedao(): ExpenseDao


    companion object{

            private var INSTANCE: ExpenseDatabase?=null

            fun getinstance(context: Context): ExpenseDatabase {
                val tempinstance = INSTANCE

                if (tempinstance!=null) return tempinstance
                synchronized(this){
                    val instance = Room.databaseBuilder(context, ExpenseDatabase::class.java,"Expense_Database").fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                    return instance
                }


            }

        }


    }
