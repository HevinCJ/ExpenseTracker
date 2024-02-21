package com.example.expensetracker.fragments.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.expensetracker.firebasedatabase.UserData


class DiffUtilAdd(private val oldlist:List<UserData?>,private val newlist:List<UserData?>):DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldlist.size
    }

    override fun getNewListSize(): Int {
        return newlist.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldlist[oldItemPosition]?.id == newlist[newItemPosition]?.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldlist[oldItemPosition] == newlist[newItemPosition]
    }
}