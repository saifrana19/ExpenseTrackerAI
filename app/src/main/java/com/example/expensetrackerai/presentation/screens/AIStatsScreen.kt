package com.example.expensetrackerai.presentation.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.expensetrackerai.domain.AIAdvisor
import com.example.expensetrackerai.presentation.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AIStatsScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val expenses by viewModel.expenses.collectAsState()
    val advisor = remember { AIAdvisor() }
    val insights = advisor.getAnalysis(expenses)

    // Group expenses by category
    val categoryTotals = expenses.groupingBy { it.category }.fold(0.0) { acc, curr -> acc + curr.amount }
    val totalSpent = expenses.sumOf { it.amount }

    val infiniteTransition = rememberInfiniteTransition()
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AI Insights", fontWeight = FontWeight.Bold) },
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
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
                .padding(16.dp)
        ) {
            
            // 🧠 AI Advice Card (Premium Glassmorphism Style)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp)),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.primaryContainer,
                                    MaterialTheme.colorScheme.secondaryContainer
                                )
                            )
                        )
                        .padding(24.dp)
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary)
                                    .scale(scale),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.AutoAwesome, contentDescription = "AI", tint = Color.White, modifier=Modifier.size(24.dp))
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Text("Gemini AI Advisor", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer)
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        if (expenses.isEmpty()) {
                            Text("Add some expenses to get personalized AI tips!", color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha=0.7f))
                        } else {
                            insights.forEach { advice ->
                                Row(modifier = Modifier.padding(bottom = 8.dp), verticalAlignment = Alignment.Top) {
                                    Icon(Icons.Default.Lightbulb, contentDescription = null, tint = Color(0xFFFFB300), modifier = Modifier.size(20.dp).padding(top=2.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(advice, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onPrimaryContainer)
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("Category Breakdown", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
            Spacer(modifier = Modifier.height(16.dp))

            if(categoryTotals.isEmpty()) {
                Box(modifier = Modifier.fillMaxWidth().height(100.dp), contentAlignment = Alignment.Center) {
                     Text("No data to display.", color = MaterialTheme.colorScheme.onBackground.copy(alpha=0.5f))
                }
            } else {
                LazyColumn {
                    items(categoryTotals.toList().size) { index ->
                        val (cat, amount) = categoryTotals.toList()[index]
                        val percentage = if(totalSpent > 0) (amount / totalSpent).toFloat() else 0f

                        CategoryProgressRow(category = cat, amount = amount, progress = percentage)
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryProgressRow(category: String, amount: Double, progress: Float) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(category, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
            Text("PKR ${amount.toInt()}", color = MaterialTheme.colorScheme.onBackground.copy(alpha=0.6f))
        }
        Spacer(modifier = Modifier.height(8.dp))
        
        // Custom animated progress bar simulation
        Box(modifier = Modifier.fillMaxWidth().height(12.dp).clip(RoundedCornerShape(6.dp)).background(MaterialTheme.colorScheme.surfaceVariant)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction = progress)
                    .height(12.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary)
                        )
                    )
            )
        }
    }
}