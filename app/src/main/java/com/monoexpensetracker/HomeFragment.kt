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
    private var money : Double = 0.0
    private var total_expense: Double = 0.0
    private val moneyViewModel : MoneyViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        moneyViewModel.moneyAmount.observe(viewLifecycleOwner,{amount ->
            val updatedAmount = amount.replace(",", "").toDoubleOrNull() ?: 0.0
            binding.totalBalanceSetText.text = updatedAmount.toString()
            money = updatedAmount.toDouble()
        })

        moneyViewModel.expenseList.observe(viewLifecycleOwner,{expenses ->
            expenseAdapter = ExpenseAdapter(expenses,requireContext())
            binding.expenseRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.expenseRecyclerView.adapter = expenseAdapter
        })

        moneyViewModel.totalExpenseAmount.observe(viewLifecycleOwner,{totalExpense ->
            binding.expenseBalSet.text = totalExpense
        })

        binding.addExpenseCard.setOnClickListener()
        {
            showAddExpenseCardDialog()
        }

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

                if (expenseValue.toDouble() <= money)
                {
                    val expense = ExpenseDataClass(expenseName, expenseDate, expenseValue)
                    moneyViewModel.addExpense(expense)
                    money -= expenseValue
                    //total_expense += expenseValue
                    moneyViewModel.subTractMoneyAmount(expenseValue)
                    binding.totalBalanceSetText.text = money.toString()
                    Toast.makeText(requireContext(), "Expense added", Toast.LENGTH_SHORT).show()
                }else
                {
                    Toast.makeText(requireContext(), "Insufficient Balance", Toast.LENGTH_SHORT).show()
                }

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