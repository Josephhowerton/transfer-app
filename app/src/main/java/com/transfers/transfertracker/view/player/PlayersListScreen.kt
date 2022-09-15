package com.transfers.transfertracker.view.player

import android.graphics.drawable.Drawable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.skydoves.landscapist.glide.GlideImage
import com.transfers.transfertracker.R
import com.transfers.transfertracker.model.squad.SquadPlayer
import com.transfers.transfertracker.view.dashboard.DashboardViewModel
import com.transfers.transfertracker.view.theme.TransferTrackerTheme

@Composable
fun PlayersList(viewModel: PlayersListViewModel) = TransferTrackerTheme {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(5.dp), modifier = Modifier.fillMaxSize()) {
        items(viewModel.playersList){ player ->
            player.name?.let {
                if(player.photo.isNullOrEmpty()){
                    PlayersListItem(viewModel, it, R.drawable.ic_baseline_person_24)
                }else{
                    PlayersListItem(viewModel, it, player.photo)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlayersListItem(viewModel: PlayersListViewModel, name: String, photoUrl: String) = Card(elevation = 2.dp, onClick = {  }) {
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (leagueImage, leagueName) = createRefs()
        GlideImage(imageModel = photoUrl,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(36.dp)
                .padding(end = 5.dp, start = 5.dp, top = 15.dp, bottom = 15.dp)
                .constrainAs(leagueImage) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )

        Text(text = name,
            fontSize = 24.sp,
            modifier = Modifier
                .padding(end = 5.dp, start = 5.dp)
                .constrainAs(leagueName) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(leagueImage.end)
                }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlayersListItem(viewModel: PlayersListViewModel, name: String, photoUrl: Int) = Card(elevation = 2.dp, onClick = {  }) {
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (leagueImage, leagueName) = createRefs()
        GlideImage(imageModel = photoUrl,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(36.dp)
                .padding(end = 5.dp, start = 5.dp, top = 15.dp, bottom = 15.dp)
                .constrainAs(leagueImage) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )

        Text(text = name,
            fontSize = 24.sp,
            modifier = Modifier
                .padding(end = 5.dp, start = 5.dp)
                .constrainAs(leagueName) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(leagueImage.end)
                }
        )
    }
}