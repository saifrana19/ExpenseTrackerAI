package com.example.expensetrackerai.presentation.screens.budget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetPlanningScreen(
    onNavigateBack: () -> Unit
) {
    val budgets = listOf(
        BudgetItem("Food", 15000.0, 8500.0),
        BudgetItem("Transport", 5000.0, 4200.0),
        BudgetItem("Shopping", 10000.0, 12000.0),
        BudgetItem("Rent", 25000.0, 25000.0)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Budget Planning", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* Add Budget Dialog */ }, containerColor = MaterialTheme.colorScheme.primary) {
                Icon(Icons.Default.Add, contentDescription = "Add Budget", tint = Color.White)
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
                .padding(16.dp)
        ) {
            item {
                Text("Monthly Budget Summary", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            items(budgets) { budget ->
                BudgetCard(budget)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun BudgetCard(budget: BudgetItem) {
    val progress = (budget.spent / budget.limit).toFloat().coerceIn(0f, 1f)
    val color = if (progress > 0.9f) Color.Red else if (progress > 0.7f) Color.Yellow else MaterialTheme.colorScheme.primary

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(budget.category, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                Text("Rs. ${budget.spent} / ${budget.limit}", style = MaterialTheme.typography.bodySmall)
            }
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier.fillMaxWidth().height(8.dp),
                color = color,
                trackColor = MaterialTheme.colorScheme.outlineVariant
            )
        }
    }
}

data class BudgetItem(val category: String, val limit: Double, val spent: Double)
