package com.monoexpensetracker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.monoexpensetracker.databinding.ExpenseHistoryCardBinding
import com.monoexpensetracker.dataclass.ExpenseDataClass

class ExpenseAdapter(var datalist: ArrayList<ExpenseDataClass>,var context: Context):
    RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    inner class ExpenseViewHolder (var binding: ExpenseHistoryCardBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        var binding = ExpenseHistoryCardBinding.inflate(LayoutInflater.from(context),parent,false)
        return ExpenseViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return datalist.size
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        holder.binding.expenseName.text = datalist.get(position).ExpenseName
        holder.binding.expenseDate.text = datalist.get(position).ExpenseDate
        holder.binding.expenseValue.text = datalist.get(position).ExpenseValue
        holder.itemView.setOnClickListener()
        {
            Toast.makeText(context,datalist.get(position).ExpenseName, Toast.LENGTH_SHORT).show()
        }
    }
}