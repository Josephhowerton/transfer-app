package com.transfers.transfertracker.view.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.transfers.transfertracker.BuildConfig
import com.transfers.transfertracker.R
import com.transfers.transfertracker.enums.Screen
import com.transfers.transfertracker.model.league.League
import com.transfers.transfertracker.view.main.viewmodel.DashboardViewModel
import com.transfers.transfertracker.view.theme.TransferTrackerTheme

@Preview
@Composable
fun LeaguesListPreview() = TransferTrackerTheme {
    LazyColumn(Modifier.fillMaxSize()) {
        items(20){
            LeaguesListItemPreview()
        }
    }
}

@Preview
@Composable
fun LeaguesListItemPreview() = Card(elevation = 0.dp) {
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (leagueImage, leagueName) = createRefs()
        Image(painter = painterResource(id = R.drawable.ic_baseline_sports_soccer_24),
            contentDescription = "",
            modifier = Modifier
                .padding(end = 5.dp, start = 5.dp, top = 15.dp, bottom = 15.dp)
                .constrainAs(leagueImage) {
                    start.linkTo(parent.start)
                    top.linkTo(leagueName.top)
                    bottom.linkTo(leagueName.bottom)
                }
        )

        Text(text = "League",
            fontSize = 24.sp,
            modifier = Modifier
                .padding(end = 5.dp, start = 5.dp, top = 15.dp, bottom = 15.dp)
                .constrainAs(leagueName) {
                    top.linkTo(parent.top)
                    start.linkTo(leagueImage.end)
                }
        )
    }
}

@Composable
fun LeaguesList(dashboardViewModel: DashboardViewModel) = TransferTrackerTheme {
    val leagues by remember { mutableStateOf(dashboardViewModel.leaguesList) }
    LazyColumn(Modifier.fillMaxSize()) {
        items(leagues.size){ index ->
            LeaguesListItem(dashboardViewModel, leagues[index])
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LeaguesListItem(dashboardViewModel: DashboardViewModel, league: League) = Card(elevation = 0.dp, onClick = {
    dashboardViewModel.fetchTeamsByLeague(league)
}) {
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (leagueImage, leagueName) = createRefs()
        Image(painter = painterResource(id = R.drawable.ic_baseline_sports_soccer_24),
            contentDescription = "",
            modifier = Modifier
                .padding(end = 5.dp, start = 5.dp, top = 15.dp, bottom = 15.dp)
                .constrainAs(leagueImage) {
                    start.linkTo(parent.start)
                    top.linkTo(leagueName.top)
                    bottom.linkTo(leagueName.bottom)
                }
        )

        Text(text = league.name,
            fontSize = 24.sp,
            modifier = Modifier
                .padding(end = 5.dp, start = 5.dp, top = 15.dp, bottom = 15.dp)
                .constrainAs(leagueName) {
                    top.linkTo(parent.top)
                    start.linkTo(leagueImage.end)
                }
        )
    }
}