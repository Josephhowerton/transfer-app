package com.transfers.transfertracker.view.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
private fun Preview() {
    GetStartedItem("", Modifier)
}

@Composable
fun GetStartedItem(message: String, modifier: Modifier) {
    Column(modifier = modifier) {
        Text(text = message,
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
    }
}