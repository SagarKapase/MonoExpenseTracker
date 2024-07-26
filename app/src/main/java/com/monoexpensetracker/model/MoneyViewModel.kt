package com.monoexpensetracker.model

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.NumberFormat
import java.util.Locale

class MoneyViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private const val PREFS_NAME = "com.monoexpensetracker.prefs"
        private const val KEY_MONEY_AMOUNT = "money_amount"
    }

    private val prefs = application.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE)

    private val _moneyAmount = MutableLiveData<String>()
    val moneyAmount: LiveData<String> get() = _moneyAmount
    //private var totalMoney = 0.0

    init {
        loadMoneyAmount()
    }

    fun loadMoneyAmount()
    {
        val amount = prefs.getFloat(KEY_MONEY_AMOUNT,0.0f)
        _moneyAmount.value = NumberFormat.getInstance(Locale.getDefault()).format(amount.toDouble())
    }
    fun setMoneyAmount(amount: Double) {

        val currentAmount = prefs.getFloat(KEY_MONEY_AMOUNT,0.0f)
        val newAmount = currentAmount + amount.toFloat()
        prefs.edit().putFloat(KEY_MONEY_AMOUNT,newAmount).apply()
        _moneyAmount.value = NumberFormat.getInstance(Locale.getDefault()).format(newAmount.toDouble())
    }
}