package com.example.expensetracker.database

import androidx.room.TypeConverter
import com.example.expensetracker.Utils.TransactionType

class TransactionTypeConvertor {

    @TypeConverter

    fun fromTransactionType(type: TransactionType):String{
        return type.name
    }

    @TypeConverter
    fun toTransactionType(type:String): TransactionType {
        return TransactionType.valueOf(type)
    }
}