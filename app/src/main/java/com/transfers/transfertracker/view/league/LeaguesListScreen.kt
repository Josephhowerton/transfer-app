package com.transfers.transfertracker.view.league

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.skydoves.landscapist.glide.GlideImage
import com.transfers.transfertracker.R
import com.transfers.transfertracker.view.component.TransferTrackerAlertDialog
import com.transfers.transfertracker.view.component.TransferTopAppBar
import com.transfers.transfertracker.view.theme.TransferTrackerTheme

@Preview
@Composable
fun LeaguesListPreview() = TransferTrackerTheme {
    LeaguesListScreen("England"){}
}

@Composable
fun LeaguesList(country: String?, onErrorAction: () -> Unit) = TransferTrackerTheme {
    if(country.isNullOrEmpty()){
        TransferTrackerAlertDialog(
            title = R.string.title_generic_error,
            message = R.string.message_generic_error,
            buttonTitle = R.string.title_dismiss_button,
            showErrorDialog = true,
            onErrorAction
        )
    }else{
        LeaguesListScreen(country, onErrorAction)
    }
}

@Composable
private fun LeaguesListScreen(country: String, onErrorAction: () -> Unit) = TransferTrackerTheme {
    val viewModel: LeaguesListViewModel = hiltViewModel()
    viewModel.fetchLeaguesByCountry(country)

    DisposableEffect(key1 = viewModel) {
        onDispose { viewModel.onDestroy() }
    }

    val leagues by remember { mutableStateOf(viewModel.leaguesList) }
    val showErrorDialog by remember { viewModel.shouldShowErrorDialog }
    val errorTitle by remember { viewModel.errorTitle }
    val errorMessage by remember { viewModel.errorMessage }

    Scaffold(topBar = {

        TransferTopAppBar(stringResource(id = R.string.title_league_list), false, onErrorAction)

    }) {

        TransferTrackerAlertDialog(
            title = errorTitle,
            message = errorMessage,
            buttonTitle = R.string.title_dismiss_button,
            showErrorDialog = showErrorDialog,
            onErrorAction
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(leagues.size) { index ->
                val id = leagues[index].id
                val logo = leagues[index].logo
                val name = leagues[index].name
                if (id != null && name != null) {
                    LeaguesListItem(viewModel, id, logo, name)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LeaguesListItem(viewModel: LeaguesListViewModel, id: Int, logo: String?, name: String) = Card(elevation = 2.dp,
    shape = RoundedCornerShape(10.dp), onClick = { viewModel.navigateToTeamList(id)}) {
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (leagueImage, leagueName) = createRefs()
        GlideImage(imageModel = logo,
            contentDescription = "",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(42.dp)
                .padding(start = 5.dp)
                .constrainAs(leagueImage) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )

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