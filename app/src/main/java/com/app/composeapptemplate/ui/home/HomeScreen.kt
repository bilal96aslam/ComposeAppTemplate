package com.app.composeapptemplate.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.app.composeapptemplate.network.response.UserData
import com.app.composeapptemplate.ui.components.ErrorState
import com.app.composeapptemplate.ui.components.Loader
import com.app.composeapptemplate.ui.components.VerticalSpacer
import com.app.composeapptemplate.utils.extension.showToast

@Composable
fun HomeScreen(
    modifier: Modifier,
    homeScreenUiState: HomeScreenUiState,
    loadData: () -> Unit,
) {

    LaunchedEffect(Unit) {
        loadData()
    }

    when (homeScreenUiState) {
        is HomeScreenUiState.Error -> {
            showToast(homeScreenUiState.msg)
            ErrorState(
                message = "Something went wrong please try again!",
                modifier = Modifier.fillMaxSize()
            )
        }

        HomeScreenUiState.Initial -> Unit
        HomeScreenUiState.Loading -> Loader(modifier = Modifier.fillMaxSize())
        is HomeScreenUiState.Success -> HomeScreenContent(
            modifier.fillMaxSize(),
            homeScreenUiState.obj
        )
    }
}

@Composable
fun HomeScreenContent(
    modifier: Modifier,
    response: UserData
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = response.avatar,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(150.dp)
                .aspectRatio(1f)
                .clip(CircleShape)
                .background(Color.Gray)
        )
        VerticalSpacer(15)
        Text(
            text = response.firstName + response.lastName,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        VerticalSpacer(12)
        Text(text = response.email, style = MaterialTheme.typography.bodyMedium)
    }
}

