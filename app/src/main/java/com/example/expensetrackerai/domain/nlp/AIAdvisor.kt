package com.example.expensetrackerai.domain

import com.example.expensetrackerai.data.local.entities.Expense

class AIAdvisor {

    fun getAnalysis(expenses: List<Expense>): List<String> {
        val adviceList = mutableListOf<String>()
        val totalSpent = expenses.sumOf { it.amount }

        if (expenses.isEmpty()) {
            return listOf("Abhi koi kharcha nahi hua. Shuru karne ke liye '+' dabayein!")
        }

        // 1. Food Check
        val foodSpent = expenses.filter { it.category == "Food" }.sumOf { it.amount }
        if (foodSpent > (totalSpent * 0.4)) {
            adviceList.add("⚠️ Aapne khane pe bohot kharch kar diya (${foodSpent.toInt()} Rs). Thora ghar ka khana khayein!")
        }

        // 2. Shopping Check
        val shoppingSpent = expenses.filter { it.category == "Shopping" }.sumOf { it.amount }
        if (shoppingSpent > 5000) {
            adviceList.add("🛑 Shopping control karein! Is mahine ${shoppingSpent.toInt()} Rs uda diye hain.")
        }

        // 3. Savings Tip
        if (totalSpent < 10000) {
            adviceList.add("✅ Good Job! Aapka kharcha control mein hai.")
        } else {
            adviceList.add("💡 Tip: Agle hafte thodi bachat karein.")
        }

        // 4. Most Frequent Category
        val categories = expenses.groupingBy { it.category }.eachCount()
        val topCategory = categories.maxByOrNull { it.value }?.key
        if (topCategory != null) {
            adviceList.add("📊 Aap sabse zyada transaction '$topCategory' mein karte hain.")
        }

        return adviceList
    }
}