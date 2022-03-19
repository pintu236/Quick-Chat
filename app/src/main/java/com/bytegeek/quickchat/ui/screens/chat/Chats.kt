package com.bytegeek.quickchat.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bytegeek.quickchat.R
import com.bytegeek.quickchat.Routes
import com.bytegeek.quickchat.data.models.ChatModel
import com.bytegeek.quickchat.providers.DataProvider
import com.bytegeek.quickchat.utils.Timing

@Composable
fun Chat(navController: NavController) {
    val chatList = remember {
        DataProvider.chats
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        itemsIndexed(items = chatList) { index, item ->
            ChatItem(item, navController)
        }
    }

}

@Composable
fun ChatItem(chatModel: ChatModel, navController: NavController) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .clickable(
                onClick = {
                    navController.navigate(Routes.SingleChat.route)
                }
            )

    ) {
        Image(
            painterResource(id = R.drawable.ic_avatar),
            contentDescription = "",
            modifier = Modifier
                .clip(CircleShape)
                .size(32.dp), contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.size(4.dp))
        Column {
            Row() {
                Text(chatModel.name, modifier = Modifier.weight(1f))
                Text(Timing.getTimeInString(chatModel.lastRead, Timing.TimeFormats.DD_MMMM_YYYY))
            }
            Spacer(modifier = Modifier.size(4.dp))

            Row() {
                Text(
                    chatModel.message, modifier = Modifier.weight(1f), fontSize = 13.sp
                )
                Text(
                    chatModel.unreadCount.toString(),
                    modifier =
                    Modifier
                        .background(
                            shape = CircleShape,
                            color = colorResource(id = R.color.colorPrimary)
                        )
                        .size(18.dp),
                    color = colorResource(id = R.color.colorSecondaryText),
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp

                )
            }
        }
    }
}