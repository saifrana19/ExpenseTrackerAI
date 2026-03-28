package com.example.expensetrackerai.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.expensetrackerai.data.local.entities.Expense
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ExpenseCard(
    expense: Expense,
    onEditClick: (Expense) -> Unit,   // ✅ Edit Action
    onDeleteClick: (Expense) -> Unit  // ✅ Delete Action
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Category Icon
            IconBox(category = expense.category)

            Spacer(modifier = Modifier.width(16.dp))

            // Text Details
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = expense.category,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF004D40) // Dark Green Text
                )
                if (expense.description.isNotEmpty()) {
                    Text(
                        text = expense.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        maxLines = 1
                    )
                }
                Text(
                    text = formatDate(expense.date),
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.LightGray
                )
            }

            // Amount & Actions
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "Rs ${expense.amount.toInt()}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFD32F2F) // Red Amount
                )

                Spacer(modifier = Modifier.height(8.dp))

                // ✅ Action Buttons (Edit & Delete)
                Row {
                    IconButton(
                        onClick = { onEditClick(expense) },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = Color(0xFF00695C),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    IconButton(
                        onClick = { onDeleteClick(expense) },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color(0xFFD32F2F),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun IconBox(category: String) {
    val icon = when (category) {
        "Food" -> Icons.Default.Fastfood
        "Transport" -> Icons.Default.DirectionsCar
        "Shopping" -> Icons.Default.ShoppingBag
        "Bills" -> Icons.Default.Receipt
        "Health" -> Icons.Default.MedicalServices
        else -> Icons.Default.Category
    }

    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFFE0F2F1)), // Light Green Background
        contentAlignment = Alignment.Center
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = Color(0xFF00695C))
    }
}

fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault())
    return sdf.format(Date(timestamp))
}