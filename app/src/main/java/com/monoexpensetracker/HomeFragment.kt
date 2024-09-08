package com.monoexpensetracker

import android.app.DatePickerDialog
import android.content.Intent
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
import java.util.Calendar
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


        //setting the Greetings on the screen
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)

        //determing the greetings based on the time
         val greeting = when(hour) {
             in 0..12 -> "Good Morning,"
             in 12..17 -> "Good Afternoon,"
             else -> "Good Evening,"
         }

        binding.greetingsText.text = greeting

        val username = arguments?.getString("username")
        binding.usernameHome.text = username.toString()

        moneyViewModel.moneyAmount.observe(viewLifecycleOwner,{amount ->
            val updatedAmount = amount.replace(",", "").toDoubleOrNull() ?: 0.0
            binding.totalBalanceSetText.text = updatedAmount.toString()
            money = updatedAmount.toDouble()
        })

        moneyViewModel.expenseList.observe(viewLifecycleOwner,{expenses ->
            expenseAdapter = ExpenseAdapter(expenses,requireContext(),moneyViewModel)
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

        expenseDateInput.setOnClickListener()
        {
            showDatePickerDialog(expenseDateInput)
        }
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
    private fun showDatePickerDialog(expenseDateInput: EditText)
    {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDilog = DatePickerDialog(
            requireContext(),
            {
                view,year,monthOfYear,dayOfMonth ->
                val selectedDate = "$dayOfMonth/${monthOfYear+1}/$year"
                expenseDateInput.setText(selectedDate)
            },
            year,month,day
        )
        datePickerDilog.show()
    }
}