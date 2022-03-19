package com.bytegeek.quickchat.ui.screens

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bytegeek.quickchat.BottomNavItem
import com.bytegeek.quickchat.R

@Composable
fun Home(mainNavController: NavController) {

    var navController = rememberNavController()
    var title = remember {
        mutableStateOf("")
    }
    var bottomNavItems = listOf(
        BottomNavItem.Chats,
        BottomNavItem.Calls,
        BottomNavItem.Settings,
    )

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(title.value)
        },
            backgroundColor = colorResource(id = R.color.colorAppbar),
            contentColor = colorResource(id = R.color.colorPrimaryText), actions = {
                IconButton(onClick = { /*TODO*/ }, content = {
                    Icon(Icons.Filled.Search, "search")
                })

            }
        )
    }, bottomBar = {
        BottomNavigation(
            backgroundColor = colorResource(id = R.color.colorBottomNav)
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            bottomNavItems.forEach { item ->

                BottomNavigationItem(
                    selectedContentColor = colorResource(id = R.color.colorPrimary),
                    unselectedContentColor = colorResource(id = R.color.colorGrey),
                    selected =
                    currentRoute == item.route, onClick = {
                        navController.navigate(item.route) {
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }

                    }, icon = {
                        Icon(
                            painterResource(id = item.icon),
                            contentDescription = item.title
                        )
                    }, label = {
                        Text(item.title, fontWeight = FontWeight.SemiBold)
                    }
                )

            }
        }
    }) {
        NavHost(
            navController,
            startDestination = BottomNavItem.Chats.route
        ) {
            composable(BottomNavItem.Chats.route) {
                Chat(navController = mainNavController)
            }
            composable(BottomNavItem.Calls.route) {
                Call()
            }
            composable(BottomNavItem.Settings.route) {
                Setting()
            }

        }

    }
    navController.addOnDestinationChangedListener { controller, destination, args ->
        when (controller.currentDestination?.route) {
            BottomNavItem.Chats.route -> {
                title.value = BottomNavItem.Chats.title
            }
            BottomNavItem.Calls.route -> {
                title.value = BottomNavItem.Calls.title
            }
            BottomNavItem.Settings.route -> {
                title.value = BottomNavItem.Settings.title
            }
        }
    }
}