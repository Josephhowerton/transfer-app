package com.transfers.transfertracker.view.team

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.skydoves.landscapist.glide.GlideImage
import com.transfers.transfertracker.R
import com.transfers.transfertracker.model.teams.Team
import com.transfers.transfertracker.view.component.TransferTrackerAlertDialog
import com.transfers.transfertracker.view.component.TransferTopAppBar
import com.transfers.transfertracker.view.theme.TransferTrackerTheme

@Preview
@Composable
fun TeamsListPreview() = TransferTrackerTheme {
    LazyColumn(Modifier.fillMaxSize()) {
        items(20){
            TeamsListScreen("1") {}
        }
    }
}

@Composable
fun TeamsList(leagueId: String?, onErrorAction: () -> Unit) = TransferTrackerTheme {
    Scaffold(topBar = {

        TransferTopAppBar("Dashboard", false, onErrorAction)

    }) {
        if (leagueId != null) {
            TeamsListScreen(leagueId = leagueId, onErrorAction = onErrorAction)
        } else {
            TransferTrackerAlertDialog(
                title = R.string.title_generic_error,
                message = R.string.message_generic_error,
                buttonTitle = R.string.title_dismiss_button,
                showErrorDialog = true,
                onErrorAction
            )
        }
    }
}

@Composable
private fun TeamsListScreen(leagueId: String, onErrorAction: () -> Unit) = TransferTrackerTheme {

    val viewModel: TeamsListViewModel = hiltViewModel()
    viewModel.fetchTeamsByLeague(leagueId)

    DisposableEffect(key1 = viewModel) {
        onDispose { viewModel.onDestroy() }
    }

    val teams by remember { mutableStateOf(viewModel.teamsList) }
    val showErrorDialog by remember { viewModel.shouldShowErrorDialog }
    val errorTitle by remember { viewModel.errorTitle }
    val errorMessage by remember { viewModel.errorMessage }

    TransferTrackerAlertDialog(
        title = errorTitle,
        message = errorMessage,
        buttonTitle = R.string.title_dismiss_button,
        showErrorDialog = showErrorDialog,
        onErrorAction
    )

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