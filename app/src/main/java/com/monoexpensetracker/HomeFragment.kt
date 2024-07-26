package com.monoexpensetracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.monoexpensetracker.adapter.ExpenseAdapter
import com.monoexpensetracker.databinding.FragmentHomeBinding
import com.monoexpensetracker.dataclass.ExpenseDataClass
import com.monoexpensetracker.model.MoneyViewModel
import java.text.NumberFormat
import java.util.Locale


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var expenseAdapter: ExpenseAdapter
    private val expenseList = ArrayList<ExpenseDataClass>()
    private val moneyViewModel : MoneyViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        moneyViewModel.moneyAmount.observe(viewLifecycleOwner,{amount ->
            binding.totalBalanceSetText.text = amount
        })

        binding.addExpenseCard.setOnClickListener()
        {
            showAddExpenseCardDialog()
        }

        expenseAdapter = ExpenseAdapter(expenseList,requireContext())

        binding.expenseRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.expenseRecyclerView.adapter = expenseAdapter
        return binding.root
    }

    private fun showAddExpenseCardDialog()
    {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Add Expense")

        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_add_expense,null)

        val expenseNameInput = dialogView.findViewById<EditText>(R.id.expenseNameInput)
        val expenseDateInput = dialogView.findViewById<EditText>(R.id.expenseDateInput)
        val expenseValueInput = dialogView.findViewById<EditText>(R.id.expenseValueInput)

        builder.setView(dialogView)

        builder.setPositiveButton("OK") { dialog, which ->
            val expenseName = expenseNameInput.text.toString()
            val expenseDate = expenseDateInput.text.toString()
            val expenseValue = expenseValueInput.text.toString().toDoubleOrNull() ?: 0.0

            if (expenseName.isNotEmpty() && expenseDate.isNotEmpty() && expenseValue > 0) {
                val expense = ExpenseDataClass(expenseName, expenseDate, expenseValue)
                expenseAdapter.addExpense(expense)
                Toast.makeText(requireContext(), "Expense added", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("Cancel") { dialog, which ->
            dialog.cancel()
        }
        builder.show()
    }
}