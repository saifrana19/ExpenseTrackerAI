package com.example.expensetrackerai.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.expensetrackerai.presentation.add_expense.AddExpenseScreen
import com.example.expensetrackerai.presentation.home.HomeScreen
import com.example.expensetrackerai.presentation.screens.AIStatsScreen
import com.example.expensetrackerai.presentation.screens.SettingsScreen
import com.example.expensetrackerai.presentation.screens.chat.MunshiChatScreen
import androidx.compose.material.icons.filled.Chat

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0; navController.navigate("home") },
                    icon = { Icon(Icons.Default.Home, "Home") },
                    label = { Text("Home") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1; navController.navigate("history") },
                    icon = { Icon(Icons.Default.History, "History") },
                    label = { Text("History") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2; navController.navigate("chat") },
                    icon = { Icon(Icons.Default.Chat, "Munshi") },
                    label = { Text("Munshi") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
                NavigationBarItem(
                    selected = selectedTab == 3,
                    onClick = { selectedTab = 3; navController.navigate("ai_stats") },
                    icon = { Icon(Icons.Default.Analytics, "Insights") },
                    label = { Text("Insights") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
                NavigationBarItem(
                    selected = selectedTab == 4,
                    onClick = { selectedTab = 4; navController.navigate("settings") },
                    icon = { Icon(Icons.Default.Settings, "Settings") },
                    label = { Text("Settings") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(padding)
        ) {
            composable("home") {
                HomeScreen(onNavigateToAdd = { navController.navigate("add_expense") })
            }
            composable("add_expense") {
                AddExpenseScreen(onNavigateBack = { navController.popBackStack() })
            }
            composable("history") {
                com.example.expensetrackerai.presentation.screens.TransactionHistoryScreen()
            }
            composable("chat") {
                MunshiChatScreen(onNavigateBack = { navController.popBackStack() })
            }
            composable("ai_stats") {
                AIStatsScreen()
            }
            composable("settings") {
                SettingsScreen(
                    onNavigateToCategories = { navController.navigate("categories") },
                    onNavigateToAbout = { navController.navigate("about_dev") },
                    onNavigateToProfile = { navController.navigate("profile") },
                    onNavigateToBudget = { navController.navigate("budget") },
                    onNavigateToHelp = { navController.navigate("help") },
                    onNavigateToCurrency = { navController.navigate("currency") },
                    onNavigateToLanguage = { navController.navigate("language") },
                    onNavigateToNotifications = { navController.navigate("notifications") }
                )
            }
            composable("profile") {
                com.example.expensetrackerai.presentation.screens.profile.ProfileScreen(
                    onNavigateToEditProfile = { navController.navigate("edit_profile") },
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable("edit_profile") {
                com.example.expensetrackerai.presentation.screens.profile.EditProfileScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable("categories") {
                com.example.expensetrackerai.presentation.screens.CategoryManagerScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable("about_dev") {
                com.example.expensetrackerai.presentation.screens.AboutDeveloperScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            // New Screens
            composable("budget") { com.example.expensetrackerai.presentation.screens.budget.BudgetPlanningScreen(onNavigateBack = { navController.popBackStack() }) }
            composable("help") { com.example.expensetrackerai.presentation.screens.support.HelpSupportScreen(onNavigateBack = { navController.popBackStack() }) }
            composable("currency") { com.example.expensetrackerai.presentation.screens.settings.CurrencySettingsScreen(onNavigateBack = { navController.popBackStack() }) }
            composable("language") { com.example.expensetrackerai.presentation.screens.settings.LanguageSettingsScreen(onNavigateBack = { navController.popBackStack() }) }
            composable("notifications") { com.example.expensetrackerai.presentation.screens.settings.NotificationSettingsScreen(onNavigateBack = { navController.popBackStack() }) }
            composable("export") { com.example.expensetrackerai.presentation.screens.export.ExportSelectionScreen(onNavigateBack = { navController.popBackStack() }) }
            composable("faq") { com.example.expensetrackerai.presentation.screens.support.FAQScreen(onNavigateBack = { navController.popBackStack() }) }
            composable("privacy") { com.example.expensetrackerai.presentation.screens.support.PrivacyPolicyScreen(onNavigateBack = { navController.popBackStack() }) }
            composable("feedback") { com.example.expensetrackerai.presentation.screens.support.FeedbackScreen(onNavigateBack = { navController.popBackStack() }) }
        }
    }
}
