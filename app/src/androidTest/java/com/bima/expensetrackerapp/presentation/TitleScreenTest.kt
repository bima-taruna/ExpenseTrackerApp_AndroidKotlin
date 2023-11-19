package com.bima.expensetrackerapp.presentation

import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.bima.expensetrackerapp.MainActivity
import com.bima.expensetrackerapp.di.SupabaseModule
import com.bima.expensetrackerapp.presentation.navigation.AuthScreen
import com.bima.expensetrackerapp.presentation.navigation.Graph
import com.bima.expensetrackerapp.presentation.navigation.NavigationTest
import com.bima.expensetrackerapp.presentation.navigation.Screen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(SupabaseModule::class)
class TitleScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.activity.setContent {
            navController = TestNavHostController(LocalContext.current)
//            val navControllerNew = rememberNavController()
//            ExpenseTrackerAppTheme {
//                NavHost(navController = navControllerNew, startDestination = Screen.Title.route) {
//                    composable(
//                        route = Screen.Title.route
//                    ) {
//                        TitleScreen(navController = navControllerNew)
//                    }
//                    composable(
//                        route = Screen.Login.route
//                    ) {
//                        SignInScreen(navController = navControllerNew)
//                    }
//                }
//            }
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            NavigationTest(navController = navController)
        }
    }


    fun NavController.assertCurrentRouteName(expectedRouteName: String) {
        assertEquals(expectedRouteName, currentBackStackEntry?.destination?.route)
    }

    @Test
    fun moodTrackerNavHost_verifyStartDestination() {
        composeRule.onNodeWithText("SignUp").assertDoesNotExist()
        navController.assertCurrentRouteName(Graph.AUTH)
    }
    @Test
    fun loginButtonIsNavigateTo_login_screen() {
        composeRule.onNodeWithText("Login").performClick()
        navController.assertCurrentRouteName(AuthScreen.Login.route)
    }
}