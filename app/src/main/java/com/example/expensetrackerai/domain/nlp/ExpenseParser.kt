package com.example.expensetrackerai.domain.nlp

import java.util.regex.Pattern
import javax.inject.Inject

class ExpenseParser @Inject constructor() {

    data class ParsedResult(
        val amount: Double?,
        val category: String,
        val description: String,
        val success: Boolean
    )

    fun parseVoiceInput(text: String): ParsedResult {
        val lowerText = text.lowercase()
        val amount = extractAmount(lowerText)
        val category = detectCategory(lowerText)

        return ParsedResult(
            amount = amount,
            category = category,
            // Agar voice samajh na aaye toh category hi description ban jaye
            description = text.replaceFirstChar { it.uppercase() },
            success = amount != null
        )
    }

    private fun extractAmount(text: String): Double? {
        // Urdu numbers (Roman) handle karna mushkil hai offline bina server ke,
        // isliye hum digits (500) dhundenge.
        // Google Speech Recognition usually "paanch sau" ko "500" hi likh ke deta hai.
        val matcher = Pattern.compile("(\\d+(\\.\\d+)?)").matcher(text)
        if (matcher.find()) {
            return matcher.group(1)?.toDoubleOrNull()
        }
        return null
    }

    private fun detectCategory(text: String): String {
        return when {
            // 🍔 Food / Khana
            text.containsAny("khana", "roti", "chai", "biryani", "burger", "pizza", "hotel", "lunch", "dinner", "nashta") -> "Food"

            // 🚗 Transport / Karaya
            text.containsAny("kiraya", "petrol", "rikshaw", "uber", "careem", "bike", "gadi", "bus", "ticket", "safari") -> "Transport"

            // 🛍️ Shopping / Khareedari
            text.containsAny("shopping", "kapray", "jootay", "shoes", "market", "bazaar", "saman", "grocery", "sabzi") -> "Shopping"

            // 📱 Bills / Easyload
            text.containsAny("bill", "bijli", "gas", "internet", "wifi", "package", "easyload", "load", "card", "balance") -> "Bills"

            // 💊 Health / Dawai
            text.containsAny("doctor", "dawai", "medicine", "hospital", "checkup") -> "Health"

            else -> "Other"
        }
    }

    // Helper function taaki code saaf rahe
    private fun String.containsAny(vararg keywords: String): Boolean {
        return keywords.any { this.contains(it) }
    }
}