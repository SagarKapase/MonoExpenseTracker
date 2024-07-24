package com.monoexpensetracker.dataclass

object Constant {
    private lateinit var dataList:ArrayList<ExpenseDataClass>

    fun getData():ArrayList<ExpenseDataClass>
    {
        dataList = ArrayList<ExpenseDataClass>();
        dataList.add(ExpenseDataClass("Lunch","23-10-2000","250"))
        dataList.add(ExpenseDataClass("Dinner","23-10-2024","300"))
        dataList.add(ExpenseDataClass("Breakfast","23-10-2023","50"))
        dataList.add(ExpenseDataClass("Lunch","23-10-2000","250"))
        dataList.add(ExpenseDataClass("Dinner","23-10-2024","300"))
        dataList.add(ExpenseDataClass("Breakfast","23-10-2023","50"))
        dataList.add(ExpenseDataClass("Lunch","23-10-2000","250"))
        dataList.add(ExpenseDataClass("Dinner","23-10-2024","300"))
        dataList.add(ExpenseDataClass("Breakfast","23-10-2023","50"))
        return dataList
    }
}