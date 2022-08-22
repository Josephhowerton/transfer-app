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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.transfers.transfertracker.R
import com.transfers.transfertracker.enums.Screen
import com.transfers.transfertracker.model.teams.Team
import com.transfers.transfertracker.view.main.viewmodel.DashboardViewModel
import com.transfers.transfertracker.view.theme.TransferTrackerTheme

@Preview
@Composable
fun TeamsListPreview() = TransferTrackerTheme {
    LazyColumn(Modifier.fillMaxSize()) {
        items(20){
            TeamsListItemPreview()
        }
    }
}

@Preview
@Composable
fun TeamsListItemPreview() = Card(elevation = 0.dp) {
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

        Text(text = "Team",
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
fun TeamsList(navController: NavController, dashboardViewModel: DashboardViewModel) = TransferTrackerTheme {
    val teams by remember { mutableStateOf(dashboardViewModel.teamsList) }
    LazyColumn(Modifier.fillMaxSize()) {
        items(teams.size){ index ->
            TeamsListItem(navController, dashboardViewModel, teams[index])
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TeamsListItem(navController: NavController, dashboardViewModel: DashboardViewModel, team: Team) = Card(elevation = 0.dp,
    onClick = { dashboardViewModel.saveSelectedTeam(team) }
) {
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

        Text(text = team.name,
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