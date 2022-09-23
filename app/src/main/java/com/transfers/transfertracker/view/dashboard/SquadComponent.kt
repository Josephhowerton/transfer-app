package com.transfers.transfertracker.view.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
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
import com.transfers.transfertracker.view.components.TeamsComponentScreen
import com.transfers.transfertracker.view.theme.CardBackgroundColor
import com.transfers.transfertracker.view.theme.HyperLinkBlue
import com.transfers.transfertracker.view.theme.TransferTrackerTheme

@Preview(showBackground = true)
@Composable
fun SquadComponentPreview() {
    TeamsComponentScreen(Modifier)
}


@Composable
fun SquadComponent(viewModel: DashboardViewModel, modifier: Modifier) = TransferTrackerTheme {
    Card(
        shape = RoundedCornerShape(2),
        modifier = modifier
            .fillMaxWidth()
            .height(450.dp)
    ) {
        val currentTeam = remember {
            viewModel.selectedTeam
        }

        val players = remember {
            viewModel.playersList
        }

        val logo = if (currentTeam.value?.logo != null)
            currentTeam.value?.logo
        else
            R.drawable.ic_baseline_sports_soccer_24

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

            Text(text = "Squad",
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

            if (players.isEmpty()) {
                val message = if (currentTeam.value == null) {
                    "Select A Team\n\nTo Get Started"
                } else {
                    "We are having trouble\n\nfinding your team"
                }
                GetStartedItem(message, Modifier
                    .padding(top = 20.dp, bottom = 40.dp)
                    .constrainAs(teamsList) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(descriptionText.bottom)
                        bottom.linkTo(parent.bottom)
                    })
            } else {
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
                        val team = currentTeam.value
                        val teamId = player.teamId
                        val playerId = player.id
                        if (playerName != null && playerPhoto != null && teamId != null && playerId != null && team != null) {
                            val leagueId = team.leagueId
                            if (leagueId != null) {
                                PlayerItem(
                                    viewModel = viewModel,
                                    playerName = playerName,
                                    playerPhoto = playerPhoto,
                                    leagueId = leagueId,
                                    playerId = playerId,
                                    teamId = teamId,
                                )
                            }
                        }
                    }
                }
            }

            if (players.isNotEmpty()) {
                ClickableText(onClick = { viewModel.navigateToSquadList() },
                    text = AnnotatedString("More"),
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
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlayerItem(
    viewModel: DashboardViewModel,
    playerName: String,
    playerPhoto: String,
    leagueId: Int,
    teamId: String,
    playerId: Int,
) =
    Card(backgroundColor = CardBackgroundColor,
        elevation = 2.dp,
        onClick = {
            viewModel.navigateToPlayerProfile(
                playerId.toString(),
                teamId,
                leagueId.toString()
            )
        }) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, bottom = 5.dp)
        ) {
            val (crest, name) = createRefs()
            GlideImage(imageModel = playerPhoto,
                contentScale = ContentScale.Fit,
                contentDescription = "",
                modifier = Modifier
                    .padding(start = 5.dp)
                    .size(36.dp)
                    .clip(CircleShape)
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