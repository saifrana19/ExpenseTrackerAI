package com.example.expensetrackerai.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.expensetrackerai.data.local.dao.ExpenseDao
import com.example.expensetrackerai.data.local.entities.Expense

@Database(entities = [Expense::class], version = 1, exportSchema = false)
abstract class ExpenseDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
}