package com.bytegeek.quickchat.ui.screens.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bytegeek.quickchat.R
import com.bytegeek.quickchat.providers.DataProvider

@Composable
@Preview(showBackground = true)
fun SingleChat() {
    val chatList = remember {
        DataProvider.chats
    }


    Scaffold(topBar = {
        TopAppBar(
            title = {
                Image(
                    painter = painterResource(id = R.drawable.ic_avatar), contentDescription = "",
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(32.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(Modifier.width(16.dp))
                Text(stringResource(id = R.string.txt_chat))
            },
            backgroundColor = colorResource(id = R.color.colorBottomNav),
            navigationIcon = {
                IconButton(
                    onClick = {

                    }) {
                    Icon(Icons.Filled.ArrowBackIos, "")
                }
            },
            actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Filled.MoreHoriz, "")
                }
            },
        )
    }) {

        LazyColumn() {
            itemsIndexed(items = chatList) { index, item ->
                ChatItem()
            }
        }
    }


}

@Composable()
fun ChatItem() {    
    


}