package com.transfers.transfertracker.view.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
private fun Preview(){
    TransferTrackerAlertDialog(1,1,1,showErrorDialog = true) {}
}

@Composable
fun TransferTrackerAlertDialog(title: Int, message: Int, buttonTitle: Int, showErrorDialog: Boolean, onClick: () -> Unit){
    if(showErrorDialog){
        AlertDialog(
            onDismissRequest = onClick,
            title = { Text(text = stringResource(id = title)) },
            text = { Text(text = stringResource(id = message)) },
            buttons = {
                Row(
                    modifier = Modifier.padding(8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = onClick
                    ) {
                        Text(text = stringResource(id = buttonTitle))
                    }
                }
            }
        )
    }
}