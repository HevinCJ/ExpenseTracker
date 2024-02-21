package com.example.expensetracker.fragments.adapter

import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.example.expensetracker.Utils.TransactionType
import com.example.expensetracker.firebasedatabase.UserData
import com.example.expensetracker.fragments.DashBoardDirections

class BindingAdapter {


    companion object{


        @BindingAdapter("android:parseTransactionTypetoString")
        @JvmStatic
        fun  parseTransactionTypetoString(view:TextView,type: TransactionType){
            val type = when(type){
                TransactionType.Income -> "Income"
                TransactionType.Expense -> "Expense"

            }

            view.text=type
        }



        @BindingAdapter("android:navigateToUpdateFrag")
        @JvmStatic
        fun navigateToUpdateFrag(layout:ConstraintLayout,entity: UserData){
            layout.setOnClickListener {
                val action = DashBoardDirections.actionDashBoardToUpdateFragment(entity)
                layout.findNavController().navigate(action)
            }
        }

    }

}