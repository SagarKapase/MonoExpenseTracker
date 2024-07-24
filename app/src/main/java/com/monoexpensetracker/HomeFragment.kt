package com.monoexpensetracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.monoexpensetracker.adapter.ExpenseAdapter
import com.monoexpensetracker.databinding.FragmentHomeBinding
import com.monoexpensetracker.dataclass.Constant


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var expenseAdapter: ExpenseAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        expenseAdapter = ExpenseAdapter(Constant.getData(),requireContext())
        binding.expenseRecyclerView.layoutManager= LinearLayoutManager(requireContext())
        binding.expenseRecyclerView.adapter = expenseAdapter
        return binding.root
    }
}