package com.example.expensetrackerai.presentation.screens.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.expensetrackerai.presentation.home.HomeViewModel

data class ChatMessage(
    val text: String,
    val isUser: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MunshiChatScreen(
    onNavigateBack: () -> Unit
) {
    var inputText by remember { mutableStateOf("") }
    val messages = remember { mutableStateListOf<ChatMessage>(
        ChatMessage("As-salam-o-alaikum! Main aapka Munshi hoon. Bataiye aaj kya kharcha hua?", false)
    )}

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Column {
                        Text("Munshi AI Chat", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
                        Text("Personal Expense Assistant", style = MaterialTheme.typography.labelSmall, color = Color.White.copy(alpha = 0.7f))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Chat Messages Area
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                reverseLayout = false,
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                items(messages) { message ->
                    ChatBubble(message)
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

            // Input Area
            Surface(
                tonalElevation = 4.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = inputText,
                        onValueChange = { inputText = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Record an expense...") },
                        shape = RoundedCornerShape(24.dp),
                        trailingIcon = {
                            IconButton(onClick = { /* Handle Voice Input */ }) {
                                Icon(Icons.Default.Mic, contentDescription = "Voice", tint = MaterialTheme.colorScheme.primary)
                            }
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = {
                            if (inputText.isNotBlank()) {
                                messages.add(ChatMessage(inputText, true))
                                // Simulate Munshi AI Response - This will later use Gemini
                                messages.add(ChatMessage("Ji bilkul, main ne note kar liya hai.", false))
                                inputText = ""
                            }
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = Color.White
                        ),
                        modifier = Modifier.size(52.dp)
                    ) {
                        Icon(Icons.Default.Send, contentDescription = "Send")
                    }
                }
            }
        }
    }
}

@Composable
fun ChatBubble(message: ChatMessage) {
    val alignment = if (message.isUser) Alignment.End else Alignment.Start
    val bgColor = if (message.isUser) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
    val textColor = if (message.isUser) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
    val shape = if (message.isUser) {
        RoundedCornerShape(16.dp, 16.dp, 0.dp, 16.dp)
    } else {
        RoundedCornerShape(16.dp, 16.dp, 16.dp, 0.dp)
    }

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = alignment) {
        Box(
            modifier = Modifier
                .clip(shape)
                .background(bgColor)
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            Text(text = message.text, color = textColor)
        }
    }
}
