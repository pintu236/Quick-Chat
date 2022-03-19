package com.bytegeek.quickchat.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bytegeek.quickchat.R
import com.bytegeek.quickchat.Routes


@Composable
fun Main(navController: NavHostController) {

    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
    ) {


        Image(
            painter = painterResource(id = R.drawable.ic_undraw_connected_world_wuay),
            contentDescription = null,
            modifier = Modifier.size(296.dp)
        )
        Spacer(modifier = Modifier.size(20.dp))
        Text(
            text = stringResource(id = R.string.txt_connect_together),
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            color = colorResource(id = R.color.colorPrimaryText)
        )
        Text(
            text = stringResource(id = R.string.txt_connect_together_hint),
            color = colorResource(id = R.color.colorGrey)
        )
        Spacer(modifier = Modifier.size(20.dp))
        TextButton(
            onClick = {
                navController.navigate(Routes.SignUp.route)
            },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.colorPrimary)
            ),
            modifier = Modifier
                .padding(all = 16.dp)
                .fillMaxWidth()
                .height(40.dp)
        ) {
            Text(
                text = stringResource(id = R.string.get_started), color = Color.White
            )
        }
        Text(
            text = stringResource(id = R.string.txt_login),
            color = colorResource(id = R.color.colorPrimary),
            modifier = Modifier.clickable(onClick =
            {
                navController.navigate(Routes.LogIn.route)
            })
        )
    }
}
