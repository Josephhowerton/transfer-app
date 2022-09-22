package com.transfers.transfertracker.view.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.transfers.transfertracker.view.theme.HyperLinkBlue

@Composable
fun TransferTopAppBar(title: String, displaySignOut: Boolean, action: () -> Unit) {
    TopAppBar(elevation = 5.dp, backgroundColor = HyperLinkBlue) {
        if(displaySignOut){
            SignOutConfiguration(action)
        }
        else{
            NavigateBackConfiguration(title, action)
        }
    }

}


@Composable
private fun SignOutConfiguration(signOutClick: () -> Unit) {
    var mDisplayMenu by remember { mutableStateOf(false) }

    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(onClick = { mDisplayMenu = !mDisplayMenu }) {
            Icon(Icons.Default.MoreVert, "")
        }

        DropdownMenu(expanded = mDisplayMenu,
            onDismissRequest = { mDisplayMenu = false }) {

            DropdownMenuItem( onClick = signOutClick ) {
                Text(text = "Logout")
            }

        }
    }
}

@Composable
private fun NavigateBackConfiguration(title: String, onBackClick: () -> Unit) {
    IconButton(onClick = onBackClick) {
        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
    }

    Text(text = title)
}