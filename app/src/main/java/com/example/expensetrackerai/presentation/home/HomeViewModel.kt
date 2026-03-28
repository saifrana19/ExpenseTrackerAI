package com.example.expensetrackerai.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetrackerai.data.local.entities.Expense
import com.example.expensetrackerai.data.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ExpenseRepository
) : ViewModel() {

    private val _expenses = MutableStateFlow<List<Expense>>(emptyList())
    val expenses: StateFlow<List<Expense>> = _expenses.asStateFlow()

    private val _totalSpent = MutableStateFlow(0.0)
    val totalSpent: StateFlow<Double> = _totalSpent.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            launch { repository.getAllExpenses().collectLatest { _expenses.value = it } }
            launch { repository.getTotalSpent().collectLatest { _totalSpent.value = it } }
        }
    }

    // ✅ DELETE FUNCTION
    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            repository.deleteExpense(expense)
        }
    }

    // ✅ UPDATE FUNCTION (Edit ke liye)
    fun updateExpense(expense: Expense) {
        viewModelScope.launch {
            repository.insertExpense(expense) // Room mein Insert hi Replace (Update) karta hai
        }
    }
}