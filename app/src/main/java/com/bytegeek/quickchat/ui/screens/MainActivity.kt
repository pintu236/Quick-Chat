package com.bytegeek.quickchat.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bytegeek.quickchat.Routes
import com.bytegeek.quickchat.data.network.Status
import com.bytegeek.quickchat.ui.screens.chat.SingleChat
import com.bytegeek.quickchat.ui.screens.login.Login
import com.bytegeek.quickchat.ui.screens.signup.SignUp
import com.bytegeek.quickchat.ui.theme.QuickChatTheme
import com.bytegeek.quickchat.utils.K_USER_INFO

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuickChatTheme {
                mainScreen()
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun mainScreen() {
    val viewModel: MainViewModel = viewModel()
    val navController = rememberNavController();
    val context = LocalContext.current;

    val userState = viewModel.getUserDetail(context).observeAsState()

    when (userState.value?.status) {
        Status.SUCCESS -> {
            val user = userState.value?.response;

            NavHost(
                navController = navController,
                startDestination = if (user == null) Routes.Main.route else Routes.Home.route
            ) {
                composable(Routes.Main.route) {
                    Main(navController = navController)
                }
                composable(Routes.SignUp.route) {
                    SignUp(navController)
                }
                composable(Routes.LogIn.route) {
                    Login(navController)
                }
                composable(Routes.EmailVerification.route.plus("/{$K_USER_INFO}")) {

                    EmailVerification(
                        navController, it.arguments?.getString(K_USER_INFO)
                    )
                }
                composable(Routes.Home.route) {
                    Home(navController)
                }
                composable(Routes.SingleChat.route) {
                    SingleChat()
                }
            }
        }
    }
}