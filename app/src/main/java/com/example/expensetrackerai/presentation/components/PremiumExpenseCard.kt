package com.example.expensetrackerai.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.LocalAtm
import androidx.compose.material.icons.filled.LocalGasStation
import androidx.compose.material.icons.filled.LocalGroceryStore
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.School
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.expensetrackerai.data.local.entities.Expense
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PremiumExpenseCard(
    expense: Expense,
    onDeleteClick: (Expense) -> Unit,
    onEditClick: (Expense) -> Unit
) {
    val dateFormat = SimpleDateFormat("dd MMM, yyyy", Locale.getDefault())
    val dateString = dateFormat.format(Date(expense.date))

    val (icon, bgColor, tintColor) = getCategoryIconAndColors(expense.category)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onEditClick(expense) }, // Whole card clickable for edit
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp) // Softer, more modern corners
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Category Icon with Circle Background
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(bgColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = expense.category,
                    tint = tintColor,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Details
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = expense.description.ifEmpty { expense.category },
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = dateString,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }

            // Amount and Actions
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "PKR ${expense.amount.toInt()}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.error // standard error red for expenses
                )
                Row {
                   IconButton(
                       onClick = { onEditClick(expense) },
                       modifier = Modifier.size(28.dp)
                   ) {
                       Icon(
                           imageVector = Icons.Default.Edit,
                           contentDescription = "Edit",
                           tint = MaterialTheme.colorScheme.primary,
                           modifier = Modifier.size(16.dp)
                       )
                   }
                   IconButton(
                       onClick = { onDeleteClick(expense) },
                       modifier = Modifier.size(28.dp)
                   ) {
                       Icon(
                           imageVector = Icons.Default.DeleteOutline,
                           contentDescription = "Delete",
                           tint = MaterialTheme.colorScheme.error,
                           modifier = Modifier.size(16.dp)
                       )
                   }
                }
            }
        }
    }
}

fun getCategoryIconAndColors(category: String): Triple<ImageVector, Color, Color> {
    return when (category.lowercase(Locale.ROOT)) {
        "food" -> Triple(Icons.Default.Fastfood, Color(0xFFFFF3E0), Color(0xFFFF9800))
        "transport" -> Triple(Icons.Default.LocalGasStation, Color(0xFFE3F2FD), Color(0xFF2196F3))
        "shopping", "grocery" -> Triple(Icons.Default.LocalGroceryStore, Color(0xFFE8F5E9), Color(0xFF4CAF50))
        "education", "stationary" -> Triple(Icons.Default.School, Color(0xFFF3E5F5), Color(0xFF9C27B0))
        "bills", "utilities" -> Triple(Icons.Default.Receipt, Color(0xFFFFEBEE), Color(0xFFF44336))
        else -> Triple(Icons.Default.LocalAtm, Color(0xFFECEFF1), Color(0xFF607D8B))
    }
}
