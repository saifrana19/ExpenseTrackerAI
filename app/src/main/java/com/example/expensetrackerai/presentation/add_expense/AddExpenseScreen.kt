package com.example.expensetrackerai.presentation.add_expense

import android.Manifest
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.expensetrackerai.utils.VoiceRecognizer
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(
    onNavigateBack: () -> Unit,
    viewModel: AddExpenseViewModel = hiltViewModel()
) {
    val amount by viewModel.amount.collectAsState()
    val description by viewModel.description.collectAsState()
    val category by viewModel.category.collectAsState()

    val context = LocalContext.current
    val voicePermissionState = rememberPermissionState(Manifest.permission.RECORD_AUDIO)

    // Voice Recognizer Instance
    val voiceRecognizer = remember { VoiceRecognizer(context) }

    // State to handle listening status
    var isListening by remember { mutableStateOf(false) }

    LaunchedEffect(isListening) {
        if (isListening) {
            voiceRecognizer.startListening().collect { state ->
                when(state) {
                    is VoiceRecognizer.VoiceState.Result -> {
                        viewModel.processVoiceInput(state.text)
                        isListening = false
                    }
                    is VoiceRecognizer.VoiceState.Error -> {
                        Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                        isListening = false
                    }
                    else -> {}
                }
            }
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Add Expense") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Voice Button
            FilledTonalButton(
                onClick = {
                    if (voicePermissionState.status.isGranted) {
                        isListening = true
                    } else {
                        voicePermissionState.launchPermissionRequest()
                    }
                },
                modifier = Modifier.size(80.dp),
                shape = MaterialTheme.shapes.extraLarge,
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = if (isListening) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Icon(
                    Icons.Default.Mic,
                    contentDescription = "Speak",
                    modifier = Modifier.size(32.dp)
                )
            }
            Text(
                if (isListening) "Listening..." else "Tap & Say (e.g., '100 rs for burger')",
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Fields
            OutlinedTextField(
                value = amount,
                onValueChange = viewModel::onAmountChange,
                label = { Text("Amount") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = description,
                onValueChange = viewModel::onDescriptionChange,
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Simplified Category Box (Could be a Dropdown in full version)
            OutlinedTextField(
                value = category,
                onValueChange = viewModel::onCategoryChange,
                label = { Text("Category (Auto-detected)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { viewModel.saveExpense(onNavigateBack) },
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("Save Expense")
            }
        }
    }
}