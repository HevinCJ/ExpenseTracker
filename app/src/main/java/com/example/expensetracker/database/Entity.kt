package com.example.expensetracker.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.expensetracker.Utils.TransactionType
import kotlinx.parcelize.Parcelize

@Entity(tableName = "ExpenseTracker")
@Parcelize
data class Entity(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val title:String,
    val amount: Double,
    val type: TransactionType,
    val time: String,
    val note:String
):Parcelable
