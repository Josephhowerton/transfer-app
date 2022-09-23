package com.transfers.transfertracker.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.skydoves.landscapist.glide.GlideImage
import com.transfers.transfertracker.R
import com.transfers.transfertracker.view.dashboard.DashboardViewModel
import com.transfers.transfertracker.view.dashboard.PlayerItem
import com.transfers.transfertracker.view.theme.HyperLinkBlue
import com.transfers.transfertracker.view.theme.TransferTrackerTheme

@Preview(showBackground = true)
@Composable
fun SignAPlayerPreview() = TransferTrackerTheme {
    Card(
        shape = RoundedCornerShape(2),
        modifier = Modifier
            .fillMaxWidth()
            .height(450.dp)
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (descriptionText,
                addATeamText,
                selectedTeam,
                selectedTeamImage,
                secondTeam,
                thirdTeam,
                fourthTeam,
                fifthTeam
            ) = createRefs()

            Image(painter = painterResource(id = R.drawable.ic_baseline_sports_soccer_24),
                contentDescription = "",
                modifier = Modifier
                    .size(36.dp)
                    .padding(end = 2.dp)
                    .constrainAs(selectedTeamImage) {
                        start.linkTo(parent.start)
                        top.linkTo(selectedTeam.top)
                        bottom.linkTo(selectedTeam.bottom)
                        end.linkTo(selectedTeam.start)
                    }
            )

            Text(text = "Sign a Player",
                fontSize = 32.sp,
                modifier = Modifier
                    .padding(start = 2.dp)
                    .constrainAs(selectedTeam) {
                        top.linkTo(parent.top, 5.dp)
                        end.linkTo(parent.end)
                        start.linkTo(selectedTeamImage.end)
                    }
            )

            createHorizontalChain(selectedTeamImage, selectedTeam, chainStyle = ChainStyle.Packed)

            Text(text = "Select a team below to change data.",
                modifier = Modifier.constrainAs(descriptionText) {
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                    top.linkTo(selectedTeam.bottom, 5.dp)
                }
            )

            TeamItem(
                teamName = "Team 1",
                crestUrl = "",
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(secondTeam) {
                        top.linkTo(descriptionText.bottom)
                        bottom.linkTo(thirdTeam.top)
                    }
            )

            TeamItem(
                teamName = "Team 2",
                crestUrl = "",
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(thirdTeam) {
                        top.linkTo(secondTeam.bottom)
                        bottom.linkTo(fourthTeam.top)
                    }
            )

            TeamItem(
                teamName = "Team 3",
                crestUrl = "",
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(fourthTeam) {
                        top.linkTo(thirdTeam.bottom)
                        bottom.linkTo(fifthTeam.top)
                    }
            )

            TeamItem(
                teamName = "Team 4",
                crestUrl = "",
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .constrainAs(fifthTeam) {
                        top.linkTo(fourthTeam.bottom)
                        bottom.linkTo(addATeamText.top)
                    }
            )

            ClickableText(onClick = {}, text = AnnotatedString("Sign a Player"),
                style = TextStyle(
                    color = Color.Blue,
                    fontSize = 20.sp
                ),
                modifier = Modifier
                    .padding(15.dp)
                    .constrainAs(addATeamText) {
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
            )
        }
    }
}

@Composable
fun SignAPlayer(viewModel: DashboardViewModel, modifier: Modifier) = TransferTrackerTheme {
    Card(
        shape = RoundedCornerShape(2),
        elevation = 5.dp,
        modifier = modifier
            .fillMaxWidth()
            .height(450.dp)
    ) {
        val players = remember {
            viewModel.playersList
        }

        val currentTeam = remember {
            viewModel.selectedTeam
        }

        val currentTeamBitmap = currentTeam?.value?.logo
        val logo = if(currentTeamBitmap.isNullOrEmpty())
            R.drawable.ic_baseline_sports_soccer_24
        else
            currentTeamBitmap

        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (
                descriptionText,
                morePlayers,
                selectedTeam,
                selectedTeamImage,
                teamsList,
            ) = createRefs()

            GlideImage(imageModel = logo,
                contentDescription = "",
                modifier = Modifier
                    .size(36.dp)
                    .padding(end = 2.dp)
                    .constrainAs(selectedTeamImage) {
                        start.linkTo(parent.start)
                        top.linkTo(selectedTeam.top)
                        bottom.linkTo(selectedTeam.bottom)
                        end.linkTo(selectedTeam.start)
                    }
            )

            Text(text = "Sign A Player",
                fontSize = 32.sp,
                modifier = Modifier
                    .padding(start = 2.dp)
                    .constrainAs(selectedTeam) {
                        top.linkTo(parent.top, 5.dp)
                        end.linkTo(parent.end)
                        start.linkTo(selectedTeamImage.end)
                    }
            )

            createHorizontalChain(selectedTeamImage, selectedTeam, chainStyle = ChainStyle.Packed)

            Text(text = "Select a player below to view profile.",
                modifier = Modifier.constrainAs(descriptionText) {
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                    top.linkTo(selectedTeam.bottom, 5.dp)
                }
            )

            LazyColumn(verticalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier
                    .padding(top = 20.dp, bottom = 20.dp)
                    .constrainAs(teamsList) {
                        height = Dimension.fillToConstraints
                        top.linkTo(descriptionText.bottom)
                        bottom.linkTo(morePlayers.top)
                    }) {
                items(players) { player ->
                    val playerName = player.name
                    val playerPhoto = player.photo
                    val team = currentTeam?.value
                    val teamId = player.teamId
                    val playerId = player.id
                    if(playerName != null && playerPhoto != null  &&
                        teamId != null && playerId != null && team != null){
                        val leagueId = team.leagueId
                        if(leagueId != null){
                            PlayerItem(viewModel = viewModel,
                                playerName = playerName,
                                playerPhoto = playerPhoto,
                                leagueId = leagueId,
                                playerId = playerId,
                                teamId = teamId)
                        }
                    }
                }
            }

            ClickableText(onClick = {  },
                text = AnnotatedString("Sign a Player"),
                style = TextStyle(
                    color = HyperLinkBlue,
                    fontSize = 20.sp
                ),
                modifier = Modifier
                    .padding(15.dp)
                    .constrainAs(morePlayers) {
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
            )
        }
    }
}