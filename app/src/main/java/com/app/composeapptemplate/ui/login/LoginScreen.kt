package com.app.composeapptemplate.ui.login

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.app.composeapptemplate.R

@Composable
@Preview
fun loginScreen() {
    Column {
        Text(text = stringResource(id = R.string.login))
    }

}