package com.app.composeapptemplate.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


@Composable
fun VerticalSpacer(size: Int) = Spacer(modifier = Modifier.height(size.dp))

@Composable
fun ErrorState(
    message: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.error
) {
    Box(modifier = modifier) {
        Text(text = message, modifier = Modifier.align(Alignment.Center), color = color)
    }
}

@Composable
fun Loader(
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
    color: Color = MaterialTheme.colorScheme.primary,
    strokeWidth: Dp = 4.dp
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(size)
                .align(Alignment.Center),
            color = color,
            strokeWidth = strokeWidth
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolBar(
    navController: NavHostController,
    title: String,
    modifier: Modifier = Modifier,
    isBackButtonVisible: Boolean = false
) {
    TopAppBar(
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        title = { Text(text = title) },
        navigationIcon = {
            if (isBackButtonVisible) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "")
                }
            }
        }
    )
}
