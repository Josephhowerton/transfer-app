package com.transfers.transfertracker.view.player

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.skydoves.landscapist.glide.GlideImage
import com.transfers.transfertracker.R
import com.transfers.transfertracker.view.component.TransferTopAppBar
import com.transfers.transfertracker.view.component.TransferTrackerAlertDialog
import com.transfers.transfertracker.view.theme.CardBackgroundColor
import com.transfers.transfertracker.view.theme.TransferTrackerTheme

@Composable
fun SquadList(onErrorAction: () -> Unit) = TransferTrackerTheme {
    val viewModel: PlayersListViewModel = hiltViewModel()
    viewModel.fetchSquad()
    Scaffold(topBar = {

        TransferTopAppBar(stringResource(id = R.string.title_player_profile), false, onErrorAction)

    }) {
        DisposableEffect(key1 = viewModel) {
            onDispose { viewModel.onDestroy() }
        }

        val showErrorDialog by remember { viewModel.shouldShowErrorDialog }
        val errorTitle by remember { viewModel.errorTitle }
        val errorMessage by remember { viewModel.errorMessage }

        TransferTrackerAlertDialog(
            title = errorTitle,
            message = errorMessage,
            buttonTitle = R.string.title_dismiss_button,
            showErrorDialog = showErrorDialog,
            onClick = onErrorAction
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(viewModel.playersList) { player ->

                val name = player.name
                val id = player.id

                if (name != null && id != null) {
                    if (player.photo.isNullOrEmpty()) {
                        PlayersListItem(viewModel, name, id, R.drawable.ic_baseline_person_24)
                    } else {
                        PlayersListItem(viewModel, name, id, player.photo)
                    }
                } else {
                    viewModel.shouldShowErrorDialog.value = true
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlayersListItem(
    viewModel: PlayersListViewModel,
    playerName: String,
    id: Int,
    photoUrl: Any
) = Card(
    backgroundColor = CardBackgroundColor,
    elevation = 2.dp,
    onClick = { viewModel.navigateToPlayerProfile(id.toString()) }) {
    ConstraintLayout(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 5.dp, bottom = 5.dp)) {
        val (crest, name) = createRefs()
        GlideImage(imageModel = photoUrl,
            contentScale = ContentScale.Fit,
            contentDescription = "",
            modifier = Modifier
                .padding(start = 5.dp)
                .size(36.dp)
                .constrainAs(crest) {
                    start.linkTo(parent.start)
                    top.linkTo(name.top)
                    bottom.linkTo(name.bottom)
                }
        )

        Text(text = playerName,
            fontSize = 24.sp,
            modifier = Modifier
                .padding(end = 5.dp, start = 7.dp, top = 15.dp, bottom = 15.dp)
                .constrainAs(name) {
                    start.linkTo(crest.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )
    }
}