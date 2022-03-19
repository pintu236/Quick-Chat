package com.bytegeek.quickchat.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.bytegeek.quickchat.R
import com.bytegeek.quickchat.Routes
import com.bytegeek.quickchat.data.local.DataManager
import com.bytegeek.quickchat.data.network.Status
import com.bytegeek.quickchat.ui.screens.signup.showMessage


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EmailVerification(
    navController: NavController,
    email: String?,
    viewModel: EmailViewModel = viewModel()
) {
    val context = LocalContext.current;
    val focusManager = LocalFocusManager.current
    val otpCode = remember { mutableStateOf("") }
    val newOTPCode = remember { mutableStateOf("") }

    val verifyState = viewModel.verifyLiveData.observeAsState()

    LaunchedEffect(verifyState.value?.status) {
        when (verifyState.value?.status) {
            Status.ERROR -> {
                showMessage(context = context, verifyState.value?.message)
            }
            Status.SUCCESS -> {
                showMessage(context, verifyState.value?.message)
                navController.navigate(Routes.Home.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        inclusive = true
                    }
                }
                DataManager.getInstance(context).saveUserDetail(verifyState.value?.response)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .clickable(onClick = {
                focusManager.clearFocus()
            }, indication = null, interactionSource = remember {
                MutableInteractionSource()
            }),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painterResource(id = R.drawable.ic_email_verification),
            contentDescription = "",
            modifier = Modifier
                .size(96.dp)
                .background(
                    color =
                    colorResource(id = R.color.colorPrimary).copy(alpha = 0.5f),
                    shape = RoundedCornerShape(48.dp)
                )
                .padding(10.dp)
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = stringResource(id = R.string.email_verification),
            color = colorResource(id = R.color.colorPrimaryText),
            fontWeight = FontWeight.Bold, fontSize = 32.sp

        )
        Text(
            text = stringResource(id = R.string.email_verification_hint),
            color = colorResource(id = R.color.colorGrey),
        )
        Spacer(modifier = Modifier.size(32.dp))
        PinInput(onValueChanged = {
            newOTPCode.value = newOTPCode.value.plus(it)
        }, value = otpCode.value)
        Spacer(modifier = Modifier.size(32.dp))
        TextButton(
            onClick = {

                viewModel.verifyEmail(
                    email ?: "", newOTPCode.value
                )
            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.colorPrimary)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp)
        ) {
            if (verifyState.value?.status == Status.LOADING
            ) {
                focusManager.clearFocus()
                CircularProgressIndicator(
                    color = colorResource(id = R.color.white), modifier = Modifier.size(32.dp)
                )
            } else {
                Text(
                    text = stringResource(id = R.string.txt_verify_email), color = Color.White
                )
            }

        }


    }
}

@ExperimentalComposeUiApi
@Composable
fun PinInput(
    modifier: Modifier = Modifier,
    length: Int = 5,
    value: String = "",
    onValueChanged: (String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current

    TextField(
        value = value,
        onValueChange = {

//            if (it.length <= length) {
//                if (it.all { c -> c in '0'..'9' }) {
//                    onValueChanged(it)
//                }
//                if (it.length >= length) {
//                    keyboard?.hide()
//                }
//            }
        },
        // Hide the text field
        modifier = Modifier
            .size(0.dp)
            .focusRequester(focusRequester),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        )
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(length) {
            CommonOtpTextField(onValueChanged = {
                onValueChanged(it)
            })
            Spacer(modifier = Modifier.size(8.dp))
        }
    }
}


@Composable
fun CommonOtpTextField(onValueChanged: (String) -> Unit) {
    val max = 1
    val otp = remember { mutableStateOf(TextFieldValue("")) }
    val focusManager = LocalFocusManager.current;

    OutlinedTextField(
        value = otp.value,
        singleLine = true,
        onValueChange = {
            if (it.text.length <= max) {
                otp.value = it
                Log.d("Entering Values:", otp.value.text)
                onValueChanged(otp.value.text)
//                if (it.text.isNotEmpty()) {
//                    focusManager.moveFocus(FocusDirection.Next)
//                }
            }


        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Phone,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(onNext = {
            focusManager.moveFocus(FocusDirection.Next)
        }),

        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .width(60.dp)
            .height(60.dp),
        maxLines = 1,
        textStyle = LocalTextStyle.current.copy(
            textAlign = TextAlign.Center
        )

    )
}