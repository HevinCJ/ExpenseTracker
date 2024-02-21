package com.example.expensetracker.firebasedatabase


import android.os.Parcelable
import androidx.room.PrimaryKey
import com.example.expensetracker.Utils.TransactionType
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserData(
    val id:String,
    val title: String ,
    val amount: String,
    val type: String,
    val date: String,
    val note: String
):Parcelable {
    constructor() : this( "","", "", "", "", "")
}
