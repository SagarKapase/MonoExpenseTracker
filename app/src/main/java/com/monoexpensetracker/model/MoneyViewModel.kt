package com.monoexpensetracker.model

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.monoexpensetracker.dataclass.ExpenseDataClass
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.exp

class MoneyViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private const val PREFS_NAME = "com.monoexpensetracker.prefs"
        private const val KEY_MONEY_AMOUNT = "money_amount"
        private const val KEY_EXPENSE_LIST = "expense_list"
        private const val KEY_TOTAL_EXPENSE_AMOUNT = "total_expense"
    }

    private val prefs = application.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE)
    private val gson = Gson()

    private val _moneyAmount = MutableLiveData<String>()
    val moneyAmount: LiveData<String> get() = _moneyAmount
    //private var totalMoney = 0.0

    //this is for the to observe the total expense amount
    private val _totalExpenseAmount = MutableLiveData<String>()
    val totalExpenseAmount : LiveData<String> get() = _totalExpenseAmount

    private val _expenseList = MutableLiveData<ArrayList<ExpenseDataClass>>()
    val expenseList: LiveData<ArrayList<ExpenseDataClass>> get() = _expenseList

    init {
        loadMoneyAmount()
        loadExpenseList()
        loadTotalExpenseAmount()
    }

    fun loadMoneyAmount()
    {
        val amount = prefs.getFloat(KEY_MONEY_AMOUNT,0.0f).toDouble()
        _moneyAmount.value = formatAmount(amount)
    }
    private fun loadExpenseList()
    {
        val json = prefs.getString(KEY_EXPENSE_LIST,null)
        if (json != null)
        {
            val type = object : TypeToken<ArrayList<ExpenseDataClass>>(){}.type
            _expenseList.value = gson.fromJson(json,type)
        }else
        {
            _expenseList.value = ArrayList()
        }
    }

    fun loadTotalExpenseAmount()
    {
        val amount = prefs.getFloat(KEY_TOTAL_EXPENSE_AMOUNT,0.0f).toDouble()
        _totalExpenseAmount.value = formatAmount(amount)
    }

    fun addExpense(expense: ExpenseDataClass) {
        val list = _expenseList.value ?: ArrayList()
        list.add(expense)
        _expenseList.value = list
        saveExpenseList(list)

        val currentTotalExpense = prefs.getFloat(KEY_TOTAL_EXPENSE_AMOUNT,0.0f)
        val newTotalExpense = currentTotalExpense + expense.ExpenseValue.toFloat()
        prefs.edit().putFloat(KEY_TOTAL_EXPENSE_AMOUNT,newTotalExpense).apply()
        _totalExpenseAmount.value = formatAmount(newTotalExpense.toDouble())
    }
    private fun saveExpenseList(list: ArrayList<ExpenseDataClass>)
    {
        val json = gson.toJson(list)
        prefs.edit().putString(KEY_EXPENSE_LIST,json).apply()
    }
    fun setMoneyAmount(amount: Double) {

        val currentAmount = prefs.getFloat(KEY_MONEY_AMOUNT,0.0f)
        val newAmount = currentAmount + amount.toFloat()
        prefs.edit().putFloat(KEY_MONEY_AMOUNT,newAmount).apply()
        _moneyAmount.value = formatAmount(newAmount.toDouble())
    }

    fun subTractMoneyAmount(amount: Double)
    {
        val currentAmmount = prefs.getFloat(KEY_MONEY_AMOUNT,0.0f).toDouble()
        val newAmount = currentAmmount -amount
        prefs.edit().putFloat(KEY_MONEY_AMOUNT,newAmount.toFloat()).apply()
        _moneyAmount.value = formatAmount(newAmount)
    }

    private fun formatAmount(amount: Double):String
    {
        return NumberFormat.getInstance(Locale.getDefault()).format(amount)
    }

    //this is method to remove the expense when delete button is clicked
    fun removeExpense(expense: ExpenseDataClass)
    {
        val list = _expenseList.value ?: return
        list.remove(expense)
        _expenseList.value = list
        saveExpenseList(list)

        val currentTotalExpense = prefs.getFloat(KEY_TOTAL_EXPENSE_AMOUNT,0.0f).toDouble()
        val newTotalExpense = currentTotalExpense - expense.ExpenseValue
        prefs.edit().putFloat(KEY_TOTAL_EXPENSE_AMOUNT,newTotalExpense.toFloat()).apply()
        _totalExpenseAmount.value = formatAmount(newTotalExpense)

        //adding delete amount to the totalExpenseAmount
        val currentTotalAmount = prefs.getFloat(KEY_MONEY_AMOUNT,0.0f).toDouble()
        val NewTotalAmount = currentTotalAmount + expense.ExpenseValue
        prefs.edit().putFloat(KEY_MONEY_AMOUNT,NewTotalAmount.toFloat()).apply()
        _moneyAmount.value = formatAmount(NewTotalAmount)

    }
}