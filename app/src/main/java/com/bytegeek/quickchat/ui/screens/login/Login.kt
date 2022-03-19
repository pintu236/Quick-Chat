package com.bytegeek.quickchat.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bytegeek.quickchat.R
import com.bytegeek.quickchat.Routes
import com.bytegeek.quickchat.data.network.NetworkResponse
import com.bytegeek.quickchat.data.network.Status
import com.bytegeek.quickchat.ui.screens.signup.showMessage

@Composable
fun Login(navController: NavController, loginViewModel: LoginViewModel = viewModel()) {
    val context = LocalContext.current;
    val focusManager = LocalFocusManager.current;
    val coroutineScope = rememberCoroutineScope()

    var email = remember {
        mutableStateOf(TextFieldValue(""))
    };


    val loginState = loginViewModel.loginLiveData.observeAsState(
        initial = NetworkResponse.idle()
    )
    val signUpState = loginViewModel.signUpLiveData.observeAsState(NetworkResponse.idle())

    LaunchedEffect(signUpState.value.status) {
        when (signUpState.value.status) {
            Status.ERROR -> {
                showMessage(context, signUpState.value.message)
            }
            Status.SUCCESS -> {
                showMessage(context, signUpState.value.message)
                navController.navigate(
                    Routes.EmailVerification.route.plus("/")
                        .plus(email.value.text)
                ) {
                    popUpTo(Routes.EmailVerification.route) {
                        inclusive = true
                    }
                }
            }
        }
    }
    LaunchedEffect(loginState.value.status) {
        when (loginState.value.status) {
            Status.ERROR -> {
                if (loginState.value.statusCode.equals(
                        "UserNotConfirmedException",
                        ignoreCase = true
                    )
                ) {
                    loginViewModel.confirmSignup(email.value.text.trim())
                } else {
                    showMessage(context, loginState.value.message)
                }

            }
            Status.SUCCESS -> {
                showMessage(context, loginState.value.message)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                32.dp
            )
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = {
                    focusManager.clearFocus()
                })
    ) {
        Image(
            painterResource(id = R.drawable.ic_baseline_arrow_back_24),
            contentDescription = "", modifier = Modifier
                .background(
                    color =
                    colorResource(id = R.color.colorPrimary).copy(alpha = 0.5f),
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(10.dp)
                .clickable(
                    onClick = {
                        navController.popBackStack()
                    }
                ))
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = stringResource(id = R.string.txt_login_to_your_account),
            fontSize = 32.sp,
            style = MaterialTheme.typography.h1,
            color = colorResource(id = R.color.colorPrimaryText)
        )
        Spacer(modifier = Modifier.size(48.dp))
        TextField(value = email.value,
            onValueChange = {
            email.value = it
        },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                textColor = colorResource(id = R.color.colorPrimaryText)
            ),
            shape = RoundedCornerShape(10.dp), modifier = Modifier
                .fillMaxWidth(),
            placeholder = {
                Text(stringResource(id = R.string.txt_email))
            }

        )
        Spacer(modifier = Modifier.size(32.dp))
        TextButton(
            onClick = {
                if (email.value.text.trim().isEmpty()) {
                    showMessage(context, "Please enter email")
                } else {
                    loginViewModel.login(email.value.text.trim())
                }
            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.colorPrimary)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp)
        ) {
            if (loginState.value.status == Status.LOADING ||
                signUpState.value.status == Status.LOADING
            ) {
                focusManager.clearFocus()
                CircularProgressIndicator(
                    color = colorResource(id = R.color.white), modifier = Modifier.size(32.dp)
                )
            } else {
                Text(
                    text = stringResource(id = R.string.txt_login), color = Color.White
                )
            }

        }
        Spacer(modifier = Modifier.size(18.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.txt_dont_have_account), color =
                colorResource(
                    id = R.color.colorGrey
                )
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = stringResource(id = R.string.txt_sign_up),
                color =
                colorResource(
                    id = R.color.colorPrimary
                ), fontWeight = FontWeight.Bold, modifier = Modifier.clickable(onClick = {
                    navController.navigate(Routes.SignUp.route)
                })

            )
        }


    }
}