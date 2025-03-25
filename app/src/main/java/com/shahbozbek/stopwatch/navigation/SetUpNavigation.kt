package com.shahbozbek.stopwatch.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.shahbozbek.stopwatch.R
import com.shahbozbek.stopwatch.ui.theme.Purple40
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetUpNavigation() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val selectedItemIndex = rememberSaveable {
        mutableIntStateOf(0)
    }
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column {
                    Box(
                        modifier = Modifier
                            .height(150.dp)
                            .fillMaxWidth(0.8f)
                            .background(color = Purple40),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Rounded app icon
                            Image(
                                painter = painterResource(id = R.drawable.ic_app_icon), // Replace with your app icon resource
                                contentDescription = null,
                                modifier = Modifier
                                    .size(70.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.secondary)
                                    .padding(8.dp)
                            )
                            // Spacing between icon and app name
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "Super App",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Normal,
                                    color = Color.White
                                ),
                                fontSize = 26.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    listOfNavItems.forEachIndexed { index, navigationItem ->
                        NavigationDrawerItem(
                            label = { Text(text = navigationItem.title) },
                            selected = index == selectedItemIndex.intValue,
                            onClick = {
                                selectedItemIndex.intValue = index
                                scope.launch {
                                    drawerState.close()
                                }
                                navController.navigate(navigationItem.route) {
                                    popUpTo(navController.graph.id) {
                                        inclusive = true
                                    }
                                }
                            },
                            icon = {
                                Image(
                                    painter = if (index == selectedItemIndex.intValue) {
                                        painterResource(id = navigationItem.selectedIcon)
                                    } else {
                                        painterResource(navigationItem.unselectedIcon)
                                    }, contentDescription = navigationItem.title,
                                    modifier = Modifier.size(45.dp)
                                )
                            },
                            modifier = Modifier
                                .padding(NavigationDrawerItemDefaults.ItemPadding)
                                .fillMaxWidth(0.78f)
                        )
                    }
                }
            }
        }
    ) {

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Super App",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Normal
                            ),
                            fontSize = 26.sp
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        ) {
                            Image(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu"
                            )
                        }
                    }
                )
            },
            content = { paddingValues ->
                AppNavigation(navHostController = navController, paddingValues)
            }
        )

    }
}