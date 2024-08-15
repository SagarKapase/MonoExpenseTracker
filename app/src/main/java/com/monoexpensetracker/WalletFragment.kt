package com.monoexpensetracker

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.monoexpensetracker.databinding.FragmentWalletBinding
import com.monoexpensetracker.model.MoneyViewModel
import java.text.NumberFormat
import java.util.Locale

class WalletFragment : Fragment() {

    private lateinit var binding: FragmentWalletBinding
    private val moneyViewModel : MoneyViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWalletBinding.inflate(layoutInflater,container,false)
        binding.addMoneyIcon.setOnClickListener()
        {
            showInputDialogBox()
        }
        moneyViewModel.moneyAmount.observe(viewLifecycleOwner, { amount ->
            binding.walletSetMoney.text = amount
        })

        binding.backimgicon.setOnClickListener()
        {
            requireActivity().supportFragmentManager.popBackStack()
        }

        return binding.root
    }
    private fun showInputDialogBox()
    {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Add Money")

        val inputText = EditText(requireContext())
        inputText.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL

        builder.setView(inputText)

        builder.setPositiveButton("OK"){
            dialog,which ->
            val enteredString = inputText.text.toString().replace(",","")
            val enteredNumber = enteredString.toDoubleOrNull()?:0.0

            moneyViewModel.setMoneyAmount(enteredNumber)
            Toast.makeText(requireContext(),"You added : $enteredNumber money in the wallet",Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("Cancel")
        {
            dialog,which ->
            dialog.cancel()
        }
        builder.show()

    }

}