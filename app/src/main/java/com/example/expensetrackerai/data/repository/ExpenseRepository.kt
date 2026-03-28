package com.example.expensetrackerai.data.repository

import com.example.expensetrackerai.data.local.dao.ExpenseDao
import com.example.expensetrackerai.data.local.entities.Expense
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExpenseRepository @Inject constructor(
    private val dao: ExpenseDao
) {
    fun getAllExpenses(): Flow<List<Expense>> = dao.getAllExpenses()

    suspend fun insertExpense(expense: Expense) = dao.insertExpense(expense)

    suspend fun deleteExpense(expense: Expense) = dao.deleteExpense(expense)

    fun getTotalSpent(): Flow<Double> = dao.getTotalSpent().map { it ?: 0.0 }
}