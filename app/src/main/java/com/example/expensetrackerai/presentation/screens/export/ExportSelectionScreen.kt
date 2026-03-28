package com.example.expensetrackerai.presentation.screens.export

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.TableChart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.expensetrackerai.utils.PdfExporter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExportSelectionScreen(
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val pdfExporter = PdfExporter(context)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Export Reports", fontWeight = FontWeight.Bold) },
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
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Select Export Format", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(32.dp))
            
            ExportCard(
                icon = Icons.Default.PictureAsPdf,
                title = "Export as PDF",
                description = "Beautiful table format suited for printing and sharing.",
                onClick = { 
                    // In a real app, you'd pass the actual expenses from a ViewModel
                    pdfExporter.exportExpensesToPdf(emptyList()) 
                }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            ExportCard(
                icon = Icons.Default.TableChart,
                title = "Export as CSV/Excel",
                description = "Raw data format for advanced analysis in Excel.",
                onClick = { /* CSV Export Logic */ }
            )
        }
    }
}

@Composable
fun ExportCard(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, description: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(40.dp), tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                Text(description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
