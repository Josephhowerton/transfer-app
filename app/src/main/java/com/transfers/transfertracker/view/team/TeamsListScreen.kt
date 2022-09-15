package com.transfers.transfertracker.view.league

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.skydoves.landscapist.glide.GlideImage
import com.transfers.transfertracker.R
import com.transfers.transfertracker.model.teams.Team
import com.transfers.transfertracker.view.dashboard.DashboardViewModel
import com.transfers.transfertracker.view.team.TeamsListViewModel
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
fun TeamsList(viewModel: TeamsListViewModel) = TransferTrackerTheme {
    val teams by remember { mutableStateOf(viewModel.teamsList) }
    LazyColumn(verticalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier.fillMaxSize()) {
        items(teams.size){ index ->
            TeamsListItem(viewModel, teams[index])
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TeamsListItem(viewModel: TeamsListViewModel, team: Team) = Card(elevation = 2.dp,
    shape = RoundedCornerShape(10.dp),
    onClick = { viewModel.saveSelectedTeam(team) }
) {
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (leagueImage, leagueName) = createRefs()
        GlideImage(imageModel = team.logo,
            contentDescription = "",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(start = 5.dp)
                .size(36.dp)
                .constrainAs(leagueImage) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )

        val name = team.name ?: "Not Provided"
        Text(text = name,
            fontSize = 24.sp,
            modifier = Modifier
                .padding(end = 5.dp, start = 10.dp, top = 15.dp, bottom = 15.dp)
                .constrainAs(leagueName) {
                    top.linkTo(parent.top)
                    start.linkTo(leagueImage.end)
                }
        )
    }
}