package com.example.expensetracker.fragments.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.databinding.TransactionAdapterBinding
import com.example.expensetracker.firebasedatabase.UserData

class TransactionAdapter:RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    var datalist : List<UserData?> = emptyList()

     class TransactionViewHolder( val binding: TransactionAdapterBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(entity: UserData?){
            binding.trackerentity = entity
            binding.executePendingBindings()

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        return TransactionViewHolder(TransactionAdapterBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
      return datalist.size
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val currentitem = datalist[position]
        holder.bind(currentitem)
    }

    fun setdata(entity: List<UserData?>){
        val diffutil = DiffUtilAdd(datalist,entity)
        val result = DiffUtil.calculateDiff(diffutil)
        result.dispatchUpdatesTo(this)
        this.datalist = entity
    }

}