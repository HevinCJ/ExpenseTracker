package com.example.expensetracker.fragments.add

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.database.Entity
import com.example.expensetracker.databinding.TransactionAdapterBinding

class TransactionAdapter:RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    var datalist = emptyList<Entity>()

     class TransactionViewHolder( val binding: TransactionAdapterBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(entity: Entity?){
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

    fun setdata(entity: List<Entity>){
        val diffutil = DiffUtilAdd(datalist,entity)
        val result = DiffUtil.calculateDiff(diffutil)
        result.dispatchUpdatesTo(this)
        this.datalist = entity
    }

}