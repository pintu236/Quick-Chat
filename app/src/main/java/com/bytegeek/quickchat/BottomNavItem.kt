package com.bytegeek.quickchat

sealed class BottomNavItem(var title: String, var icon: Int, var route: String) {
    object Chats : BottomNavItem("Chats", R.drawable.ic_outline_chat_bubble_outline_24, "chat");
    object Calls : BottomNavItem("Calls", R.drawable.ic_outline_phone_24, "call");
    object Settings : BottomNavItem("Settings", R.drawable.ic_outline_settings_24, "setting");
}