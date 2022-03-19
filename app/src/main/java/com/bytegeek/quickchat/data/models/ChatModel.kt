package com.bytegeek.quickchat.data.models

data class ChatModel(
    val id: Int, val name: String, val message: String, val lastRead: Long,
    val unreadCount: Int
)
