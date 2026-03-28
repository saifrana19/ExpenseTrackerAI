package com.example.expensetrackerai.presentation.add_expense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetrackerai.data.local.entities.Expense
import com.example.expensetrackerai.data.repository.ExpenseRepository
import com.example.expensetrackerai.domain.nlp.ExpenseParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val repository: ExpenseRepository,
    private val parser: ExpenseParser
) : ViewModel() {

    private val _amount = MutableStateFlow("")
    val amount = _amount.asStateFlow()

    private val _description = MutableStateFlow("")
    val description = _description.asStateFlow()

    private val _category = MutableStateFlow("Other")
    val category = _category.asStateFlow()

    fun onAmountChange(v: String) { _amount.value = v }
    fun onDescriptionChange(v: String) { _description.value = v }
    fun onCategoryChange(v: String) { _category.value = v }

    fun processVoiceInput(text: String) {
        val result = parser.parseVoiceInput(text)
        if (result.success) {
            result.amount?.let { _amount.value = it.toString() }
            _category.value = result.category
            _description.value = result.description
        }
    }

    fun saveExpense(onSuccess: () -> Unit) {
        val amt = _amount.value.toDoubleOrNull()
        if (amt != null) {
            viewModelScope.launch {
                repository.insertExpense(
                    Expense(
                        amount = amt,
                        description = _description.value,
                        category = _category.value
                    )
                )
                onSuccess()
            }
        }
    }
}