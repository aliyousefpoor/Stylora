package com.stylora.style.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.stylora.style.presentation.ui.FeedbackHistoryScreen
import com.stylora.style.presentation.ui.ImagePickerScreen
import com.stylora.style.ui.theme.StyloraTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StyloraTheme {
                StyloraApp()
            }
        }
    }
}

@Composable
fun StyloraApp() {
    val navController: NavHostController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "/give_feedback"
    ) {
        composable("/give_feedback") {
            ImagePickerScreen(modifier = Modifier, navigateToHistory = {
                navController.navigate("/feedback_history")
            })
        }

        composable("/feedback_history") {
            FeedbackHistoryScreen()
        }
    }
}
