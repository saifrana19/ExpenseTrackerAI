package com.example.expensetrackerai.presentation.screens.support

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FAQScreen(
    onNavigateBack: () -> Unit
) {
    val faqs = listOf(
        FAQItem("How to add an expense?", "You can add an expense by clicking the '+' button on the Home screen or by using voice input with 'Munshi AI'."),
        FAQItem("Is my data safe?", "Yes, all your data is stored locally on your device and is not shared with any third party."),
        FAQItem("Can I export my data?", "Yes, you can export your data as a PDF or CSV from the Export Reports section in Settings."),
        FAQItem("What is Munshi AI?", "Munshi AI is our intelligent assistant that helps you track expenses using natural language and voice commands."),
        FAQItem("How to set a budget?", "Go to Budgeting & Planning in Settings and set monthly limits for each category.")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("FAQs", fontWeight = FontWeight.Bold) },
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
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
                .padding(16.dp)
        ) {
            items(faqs) { faq ->
                FAQCard(faq)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun FAQCard(faq: FAQItem) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth().clickable { expanded = !expanded },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(faq.question, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                Icon(if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown, contentDescription = null)
            }
            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(faq.answer, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

data class FAQItem(val question: String, val answer: String)
