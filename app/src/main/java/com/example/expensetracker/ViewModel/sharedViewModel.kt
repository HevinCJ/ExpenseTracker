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

    fun userValidation(title: String, Amount: Double, transactiontype: String, selecteddate: String, note: String): Boolean {
        Log.d("validation",title)
        return if ((TextUtils.isEmpty(title) || TextUtils.isEmpty(Amount.toString()) || TextUtils.isEmpty(transactiontype) || TextUtils.isEmpty(selecteddate) || TextUtils.isEmpty(note)))
          {
                false
            }else{
            !((TextUtils.isEmpty(title) || TextUtils.isEmpty(Amount.toString()) || TextUtils.isEmpty(transactiontype) || TextUtils.isEmpty(selecteddate) || TextUtils.isEmpty(note)))

        }
    }

    fun parseTransactionTypeFromposition(transactionType: String): TransactionType {
        return when (transactionType) {
            "Income" -> TransactionType.Income
            "Expense" -> TransactionType.Expense
            else -> TransactionType.Expense
        }
    }



    fun parseTransactionTypeFromType(transactionType: TransactionType): Int {
        return when (transactionType) {
            TransactionType.Expense -> 0
            TransactionType.Income -> 1
        }
    }





}