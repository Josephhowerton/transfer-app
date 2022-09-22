package com.transfers.transfertracker.view.dashboard

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.transfers.transfertracker.R
import com.transfers.transfertracker.view.component.TransferTopAppBar
import com.transfers.transfertracker.view.components.*
import com.transfers.transfertracker.view.theme.TransferTrackerTheme



@Composable
fun Dashboard() = TransferTrackerTheme {
    val viewModel: DashboardViewModel = hiltViewModel()
    Scaffold(topBar = {

        TransferTopAppBar(stringResource(id = R.string.title_dashboard), true) { viewModel.signOut() }

    }) {
        ConstraintLayout(
            Modifier.verticalScroll(
                state = rememberScrollState(),
            )
        ) {
            val (teams, news, players, stats, transfers) = createRefs()

            DisposableEffect(key1 = viewModel) {
                onDispose { viewModel.onDestroy() }
            }

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

            SquadComponent(
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

            TransferComponent(viewModel,
                Modifier
                    .padding(bottom = 5.dp)
                    .padding(bottom = 50.dp)
                    .constrainAs(transfers) {
                        top.linkTo(stats.bottom)
                    }
            )
        }
    }
}