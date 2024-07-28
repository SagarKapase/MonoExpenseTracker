package com.monoexpensetracker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.monoexpensetracker.R
import com.monoexpensetracker.databinding.ExpenseHistoryCardBinding
import com.monoexpensetracker.dataclass.ExpenseDataClass
import com.monoexpensetracker.model.MoneyViewModel
import java.util.zip.Inflater
import kotlin.math.exp

class ExpenseAdapter(var datalist: ArrayList<ExpenseDataClass>,var context: Context,
    private val moneyViewModel: MoneyViewModel):
    RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    inner class ExpenseViewHolder (var binding: ExpenseHistoryCardBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(expense: ExpenseDataClass)
        {
            binding.expenseName.text = expense.ExpenseName
            binding.expenseDate.text = expense.ExpenseDate
            binding.expenseValue.text = expense.ExpenseValue.toString()
            binding.threeDotsMenu.setOnClickListener{view ->
                showMenu(view,expense)
            }
        }

        private fun showMenu(view: View,expense: ExpenseDataClass)
        {
            val popUpMenu = PopupMenu(context,view)
            val inflater: MenuInflater = popUpMenu.menuInflater
            inflater.inflate(R.menu.expense_card_menu,popUpMenu.menu)
            popUpMenu.setOnMenuItemClickListener { item ->
                when(item.itemId)
                {
                    R.id.delete_expense ->
                    {
                        moneyViewModel.removeExpense(expense)
                        notifyItemRemoved(adapterPosition)
                        true
                    } else -> false
                }
            }
            popUpMenu.show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        var binding = ExpenseHistoryCardBinding.inflate(LayoutInflater.from(context),parent,false)
        return ExpenseViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return datalist.size
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        /*holder.binding.expenseName.text = datalist.get(position).ExpenseName
        holder.binding.expenseDate.text = datalist.get(position).ExpenseDate
        holder.binding.expenseValue.text = datalist.get(position).ExpenseValue.toString()
        holder.itemView.setOnClickListener()
        {
            Toast.makeText(context,datalist.get(position).ExpenseName, Toast.LENGTH_SHORT).show()
        }*/

        val expense = datalist[position]
        holder.bind(expense)
        holder.itemView.setOnClickListener()
        {
            Toast.makeText(context,expense.ExpenseName,Toast.LENGTH_SHORT).show()
        }
    }

    fun addExpense(expense: ExpenseDataClass)
    {
        datalist.add(expense)
        notifyItemInserted(datalist.size - 1)
    }
}