package com.example.expensetrackerai.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.expensetrackerai.presentation.components.PremiumExpenseCard
import com.example.expensetrackerai.presentation.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionHistoryScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val expenses by viewModel.expenses.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Transaction History", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            
            // Search / Filter could go here in future

            Text(
                "All Expenses",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            if (expenses.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No transactions found.", color = MaterialTheme.colorScheme.onBackground.copy(alpha=0.5f))
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    items(expenses.sortedByDescending { it.date }) { expense ->
                        PremiumExpenseCard(
                            expense = expense,
                            onDeleteClick = { viewModel.deleteExpense(it) },
                            onEditClick = { /* Can implement edit from history too or disable edit */ }
                        )
                    }
                }
            }
        }
    }
}
