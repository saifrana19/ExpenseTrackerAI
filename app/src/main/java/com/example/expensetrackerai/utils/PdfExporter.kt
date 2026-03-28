package com.example.expensetrackerai.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.util.Log
import android.widget.Toast
import com.example.expensetrackerai.data.local.entities.Expense
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class PdfExporter(private val context: Context) {

    fun exportExpensesToPdf(expenses: List<Expense>, fileName: String = "Expense_Report") {
        val pdfDocument = PdfDocument()
        val paint = Paint()
        val titlePaint = Paint()
        
        // Page Info
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create() // A4 Size
        val page = pdfDocument.startPage(pageInfo)
        val canvas: Canvas = page.canvas

        val startX = 40f
        var startY = 50f

        // Header
        titlePaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        titlePaint.textSize = 24f
        titlePaint.color = Color.parseColor("#1A237E") // Dark Indigo
        canvas.drawText("ExpenseTracker AI Report", startX, startY, titlePaint)
        
        startY += 30f
        paint.textSize = 12f
        paint.color = Color.GRAY
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        canvas.drawText("Generated on: ${sdf.format(Date())}", startX, startY, paint)
        
        startY += 40f
        
        // Table Header Background
        paint.color = Color.parseColor("#E8EAF6")
        canvas.drawRect(startX - 5, startY - 20, 555f, startY + 10, paint)

        // Table Headers
        paint.color = Color.BLACK
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        paint.textSize = 14f
        canvas.drawText("Date", startX, startY, paint)
        canvas.drawText("Category", startX + 100, startY, paint)
        canvas.drawText("Description", startX + 220, startY, paint)
        canvas.drawText("Amount", startX + 450, startY, paint)

        startY += 30f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        paint.textSize = 12f

        var totalAmount = 0.0
        val dateSdf = SimpleDateFormat("dd/MM/yy", Locale.getDefault())

        for (expense in expenses) {
            // Check for page overflow
            if (startY > 800) {
                // In a real app, you'd handle multiple pages here.
                // For simplicity, we limit it for now or could add a new page loop.
                break 
            }

            canvas.drawText(dateSdf.format(Date(expense.date)), startX, startY, paint)
            canvas.drawText(expense.category, startX + 100, startY, paint)
            
            // Limit description length to avoid overlap
            val desc = if (expense.description.length > 25) expense.description.substring(0, 22) + "..." else expense.description
            canvas.drawText(desc, startX + 220, startY, paint)
            
            canvas.drawText("Rs. ${expense.amount}", startX + 450, startY, paint)
            
            totalAmount += expense.amount
            startY += 25f
        }

        startY += 20f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        paint.textSize = 16f
        paint.color = Color.parseColor("#D32F2F") // Red for total
        canvas.drawText("Total Expenses: Rs. $totalAmount", startX + 350, startY, paint)

        pdfDocument.finishPage(page)

        // Save PDF to Documents/Downloads
        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "${fileName}_${System.currentTimeMillis()}.pdf")

        try {
            pdfDocument.writeTo(FileOutputStream(file))
            Toast.makeText(context, "PDF Saved: ${file.name}", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(context, "Failed to save PDF", Toast.LENGTH_SHORT).show()
        } finally {
            pdfDocument.close()
        }
    }
}
