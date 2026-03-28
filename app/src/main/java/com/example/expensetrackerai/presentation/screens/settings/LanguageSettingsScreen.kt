package com.example.expensetrackerai.presentation.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageSettingsScreen(
    onNavigateBack: () -> Unit
) {
    val languages = listOf("English", "Urdu (اردو)", "Punjabi (پنجابی)", "Sindhi (سنڌي)", "Pashto (پښتو)")
    var selectedLanguage by remember { mutableStateOf("English") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Language Settings", fontWeight = FontWeight.Bold) },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
        ) {
            Text(
                "Choose your preferred app language.",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            languages.forEach { language ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedLanguage = language }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(language, modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyLarge)
                    if (selectedLanguage == language) {
                        Icon(Icons.Default.Check, contentDescription = "Selected", tint = MaterialTheme.colorScheme.primary)
                    }
                }
                Divider(modifier = Modifier.padding(horizontal = 16.dp))
            }
        }
    }
}
