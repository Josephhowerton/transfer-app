package com.transfers.transfertracker.view.main.screens

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.transfers.transfertracker.view.components.*
import com.transfers.transfertracker.view.components.news.NewsComponent
import com.transfers.transfertracker.view.main.viewmodel.DashboardViewModel
import com.transfers.transfertracker.view.theme.TransferTrackerTheme


@Preview
@Composable
fun DashboardScreenPreview() {
    DashboardScreen()
}

@Composable
fun DashboardScreen() = TransferTrackerTheme {
    ConstraintLayout(
        Modifier.scrollable(
            state = rememberScrollState(),
            orientation = Orientation.Vertical
        )
    ) {
        val (teams, players, stats, news) = createRefs()

        TeamsComponentScreen(
            Modifier
                .padding(bottom = 5.dp)
                .padding(bottom = 50.dp)
                .constrainAs(teams) {
                    top.linkTo(parent.top)
                }
        )

        PlayerComponentScreen(
            Modifier
                .padding(bottom = 5.dp)
                .padding(bottom = 50.dp)
                .constrainAs(players) {
                    top.linkTo(teams.bottom)
                }
        )

        StatisticsComponentScreen(
            Modifier
                .padding(bottom = 5.dp)
                .padding(bottom = 50.dp)
                .constrainAs(stats) {
                    top.linkTo(players.bottom)
                }
        )
    }
}

@Composable
fun Dashboard(dashboardViewModel: DashboardViewModel,
              navController: NavController) = TransferTrackerTheme {
    ConstraintLayout(
        Modifier.verticalScroll(
            state = rememberScrollState(),
        )
    ) {
        val (teams, news, players, stats) = createRefs()

        TeamsComponent(
            dashboardViewModel,
            navController,
            Modifier
                .padding(bottom = 5.dp)
                .padding(bottom = 50.dp)
                .constrainAs(teams) {
                    top.linkTo(parent.top)
                }
        )

        NewsComponent(
            dashboardViewModel,
            navController,
            Modifier
                .padding(bottom = 5.dp)
                .padding(bottom = 50.dp)
                .constrainAs(news) {
                    top.linkTo(teams.bottom)
                }
        )

        PlayerComponent(
            dashboardViewModel,
            navController,
            Modifier.padding(bottom = 5.dp)
                .padding(bottom = 50.dp)
                .constrainAs(players) {
                    top.linkTo(news.bottom)
                }
        )

        StatisticsComponent(
            dashboardViewModel,
            navController,
            Modifier
                .padding(bottom = 5.dp)
                .padding(bottom = 50.dp)
                .constrainAs(stats) {
                    top.linkTo(players.bottom)
                }
        )
    }
}
