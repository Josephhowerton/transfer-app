package com.transfers.transfertracker.view.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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
import com.transfers.transfertracker.enums.ETransfer
import com.transfers.transfertracker.view.components.TeamsComponentScreen
import com.transfers.transfertracker.view.theme.CardBackgroundColor
import com.transfers.transfertracker.view.theme.HyperLinkBlue
import com.transfers.transfertracker.view.theme.TransferTrackerTheme

@Preview(showBackground = true)
@Composable
fun PlayersComponentPreview() {
    TeamsComponentScreen(Modifier)
}

@Composable
fun TransferComponent(viewModel: DashboardViewModel, modifier: Modifier) = TransferTrackerTheme {
    Card(
        shape = RoundedCornerShape(2),
        modifier = modifier
            .fillMaxWidth()
            .height(450.dp)
    ) {
        val currentTeam = remember {
            viewModel.selectedTeam
        }

        val transferData = remember {
            viewModel.transferList
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

            Text(text = "Transfers",
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

            if (transferData.isEmpty()) {
                val message = if (currentTeam.value == null) {
                    "Select A Team\n\nTo Get Started"
                } else {
                    "We are having trouble\n\nfinding transfers"
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
                    items(transferData) { transfer ->
                        val team = currentTeam.value
                        val playerId = transfer.id
                        val teamId = transfer.teamId
                        val playerName = transfer.name
                        val playerPhoto = transfer.photo
                        val playerTransfer = transfer.transfer
                        if (playerName != null && playerPhoto != null && teamId != null && playerId != null && team != null) {
                            val transferImage: Int = when (playerTransfer) {
                                ETransfer.IN -> R.drawable.ic_baseline_transfer_in
                                ETransfer.OUT -> R.drawable.ic_baseline_transfer_out
                                else -> R.drawable.ic_baseline_circle_24
                            }

                            TransferListItem(
                                viewModel = viewModel,
                                playerName = playerName,
                                playerPhoto = playerPhoto,
                                playerId = playerId,
                                teamId = teamId,
                                type = transfer.type,
                                transferImage = transferImage
                            )
                        }
                    }
                }
            }

            if (transferData.isNotEmpty()) {
                ClickableText(onClick = { viewModel.navigateToTransferList() },
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
private fun TransferListItem(
    viewModel: DashboardViewModel,
    playerName: String,
    playerId: Int,
    type: String?,
    teamId: String,
    transferImage: Int,
    playerPhoto: Any
) =
    Card(backgroundColor = CardBackgroundColor,
        elevation = 2.dp,
        onClick = {
            viewModel.navigateToPlayerProfile(playerId.toString(), teamId, "ignore")
        }
    ) {
        ConstraintLayout(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp, bottom = 5.dp)) {
            val (leagueImage, leagueName, direction, transferType) = createRefs()

            GlideImage(imageModel = playerPhoto,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(start = 5.dp)
                    .size(36.dp)
                    .constrainAs(leagueImage) {
                        start.linkTo(parent.start)
                        top.linkTo(leagueName.top)
                        bottom.linkTo(leagueName.bottom)
                    }
            )

            Text(text = playerName,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(end = 5.dp, start = 5.dp, top = 15.dp, bottom = 15.dp)
                    .constrainAs(leagueName) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(leagueImage.end)
                    }
            )

            if (type != null) {
                Text(text = type,
                    fontSize = 18.sp,
                    color = HyperLinkBlue,
                    modifier = Modifier
                        .padding(end = 5.dp, start = 5.dp)
                        .constrainAs(transferType) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            end.linkTo(direction.start)
                        }
                )
            }

            GlideImage(imageModel = transferImage,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(start = 5.dp)
                    .size(24.dp)
                    .constrainAs(direction) {
                        end.linkTo(parent.end)
                        top.linkTo(leagueName.top)
                        bottom.linkTo(leagueName.bottom)
                    }
            )
        }
    }