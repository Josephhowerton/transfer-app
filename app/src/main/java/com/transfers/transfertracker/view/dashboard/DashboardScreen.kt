package com.transfers.transfertracker.view.dashboard

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.transfers.transfertracker.view.components.*
import com.transfers.transfertracker.view.theme.HyperLinkBlue
import com.transfers.transfertracker.view.theme.TransferTrackerTheme

@Composable
fun Dashboard(viewModel: DashboardViewModel) = TransferTrackerTheme {
    Scaffold(bottomBar = {TransferBottomAppBar(viewModel)}) {
        ConstraintLayout(
            Modifier.verticalScroll(
                state = rememberScrollState(),
            )
        ) {
            val (teams, news, players, stats) = createRefs()

            TeamsComponent(
                viewModel,
                Modifier
                    .padding(bottom = 5.dp)
                    .padding(bottom = 50.dp)
                    .constrainAs(teams) {
                        top.linkTo(parent.top)
                    }
            )

            NewsComponentScreen(
                viewModel,
                Modifier
                    .padding(bottom = 5.dp)
                    .padding(bottom = 50.dp)
                    .constrainAs(news) {
                        top.linkTo(teams.bottom)
                    }
            )

            PlayerComponent(
                viewModel,
                Modifier
                    .padding(bottom = 5.dp)
                    .padding(bottom = 50.dp)
                    .constrainAs(players) {
                        top.linkTo(news.bottom)
                    }
            )

            StatisticsComponent(
                viewModel,
                Modifier
                    .padding(bottom = 5.dp)
                    .padding(bottom = 50.dp)
                    .constrainAs(stats) {
                        top.linkTo(players.bottom)
                    }
            )
        }
    }
}


@Composable
private fun TransferBottomAppBar(viewModel: DashboardViewModel) {
    var mDisplayMenu by remember { mutableStateOf(false) }
    BottomAppBar( backgroundColor = HyperLinkBlue ) {
        IconButton(onClick = { mDisplayMenu = !mDisplayMenu }) {
            Icon(Icons.Default.MoreVert, "")
        }

        DropdownMenu(
            expanded = mDisplayMenu,
            onDismissRequest = { mDisplayMenu = false }
        ) {

            DropdownMenuItem(onClick = { viewModel.signOut() }) {
                Text(text = "Logout")
            }
        }
    }
}
