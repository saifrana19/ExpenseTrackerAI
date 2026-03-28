package com.example.expensetrackerai.di

import android.content.Context
import androidx.room.Room
import com.example.expensetrackerai.data.local.dao.ExpenseDao
import com.example.expensetrackerai.data.local.database.ExpenseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ExpenseDatabase {
        return Room.databaseBuilder(
            context,
            ExpenseDatabase::class.java,
            "expense_tracker.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideExpenseDao(database: ExpenseDatabase): ExpenseDao = database.expenseDao()
}