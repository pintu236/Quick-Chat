package com.bytegeek.quickchat

sealed class Routes(val route: String) {
    object Main : Routes("main");
    object SignUp : Routes("signup")
    object LogIn : Routes("login")
    object EmailVerification : Routes("email_verification")
    object Home : Routes("home")
    object SingleChat : Routes("single_chat")
}