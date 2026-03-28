package com.example.expensetrackerai.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.expensetrackerai.presentation.add_expense.AddExpenseScreen
import com.example.expensetrackerai.presentation.home.HomeScreen
import com.example.expensetrackerai.presentation.screens.WelcomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") {
            WelcomeScreen(
                onNavigateToHome = {
                    navController.navigate("login") {
                        popUpTo("welcome") { inclusive = true }
                    }
                }
            )
        }
        composable("login") {
            com.example.expensetrackerai.presentation.screens.auth.LoginScreen(
                onNavigateToRegister = { navController.navigate("register") },
                onLoginSuccess = {
                    navController.navigate("main") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
        composable("register") {
            com.example.expensetrackerai.presentation.screens.auth.RegisterScreen(
                onNavigateToLogin = { navController.navigate("login") { popUpTo("register") { inclusive=true } } },
                onRegisterSuccess = {
                    navController.navigate("otp")
                }
            )
        }
        composable("otp") {
            com.example.expensetrackerai.presentation.screens.auth.OTPScreen(
                email = "user@example.com", // Placeholder
                onOtpVerified = {
                    navController.navigate("main") {
                        popUpTo("register") { inclusive = true }
                    }
                },
                onResendOtp = { /* Logic */ }
            )
        }
        composable("main") {
            MainScreen()
        }
    }
}