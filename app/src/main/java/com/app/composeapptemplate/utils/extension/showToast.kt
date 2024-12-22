package com.app.composeapptemplate.utils.extension

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

@Composable
fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    val context = LocalContext.current
    context.showToast(message, duration)
}