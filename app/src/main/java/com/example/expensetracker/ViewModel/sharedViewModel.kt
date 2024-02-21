package com.example.expensetracker.ViewModel

import android.app.Application
import android.app.DatePickerDialog
import android.content.Context
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.expensetracker.Utils.TransactionType
import java.util.Calendar

class sharedViewModel(application: Application):AndroidViewModel(application) {




    fun showDatePickerDialogue(context: Context, onDateSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialogue = DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = "$selectedDay-${selectedMonth+1}-$selectedYear"
                onDateSelected(formattedDate)
            },
            year,
            month,
            day
        )

        datePickerDialogue.show()
    }

    fun userValidation(title: String, Amount: String, transactiontype: String, selecteddate: String, note: String): Boolean {
        Log.d("validation",title)
        return if ((TextUtils.isEmpty(title) || TextUtils.isEmpty(Amount) || TextUtils.isEmpty(transactiontype) || TextUtils.isEmpty(selecteddate) || TextUtils.isEmpty(note)))
          {
                false
            }else{
            !((TextUtils.isEmpty(title) || TextUtils.isEmpty(Amount) || TextUtils.isEmpty(transactiontype) || TextUtils.isEmpty(selecteddate) || TextUtils.isEmpty(note)))

        }
    }

    fun parseTransactionTypeFromposition(transactionType: String): TransactionType {
        return when (transactionType) {
            "Income" -> TransactionType.Income
            "Expense" -> TransactionType.Expense
            else -> TransactionType.Expense
        }
    }


    fun parseTransactionTypeFromType(transactionType: String): Int {
        return when (transactionType) {
           "Expense" -> 0
           "Income" -> 1
            else -> {
                return 0
            }
        }
    }


     fun firebasecheckPasswordCorrect(password:String, confirmpassword:String):Boolean{
        return password==confirmpassword
    }

     fun firebaseuservalidation(email:String, password: String, confirmpassword: String):Boolean{
        return if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmpassword)){
            false
        }
        else{
            true
        }

    }





}