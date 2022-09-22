package com.transfers.transfertracker.view.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import com.transfers.transfertracker.model.teams.Team
import com.transfers.transfertracker.view.dashboard.DashboardViewModel
import com.transfers.transfertracker.view.dashboard.GetStartedItem
import com.transfers.transfertracker.view.theme.CardBackgroundColor
import com.transfers.transfertracker.view.theme.HyperLinkBlue
import com.transfers.transfertracker.view.theme.TransferTrackerTheme

@Preview(showBackground = true)
@Composable
fun TeamsComponentScreenPreview() {
    TeamsComponentScreen(Modifier)
}

@Composable
fun TeamsComponentScreen(modifier: Modifier) = TransferTrackerTheme {
    Card(shape = RoundedCornerShape(2),
        modifier = modifier
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

            Text(text = "PlaceHolderText",
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

            ClickableText(onClick = {}, text = AnnotatedString("Add Team"),
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TeamsComponent(viewModel: DashboardViewModel, modifier: Modifier) = TransferTrackerTheme {
    Card(shape = RoundedCornerShape(2),
        modifier = modifier
            .fillMaxWidth()
            .height(450.dp)
    ) {

        val currentTeam = remember {
            viewModel.selectedTeam
        }

        val teams = remember {
            viewModel.usersTeams
        }

        val addTeamVisibility = remember {
            viewModel.shouldShowAddTeamButton
        }

        val currentTeamBitmap = currentTeam.value?.logo
        val logo = if(currentTeamBitmap.isNullOrEmpty())
            R.drawable.ic_baseline_sports_soccer_24
        else
            currentTeamBitmap

        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (
                descriptionText,
                addATeamText,
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

            val currentTeamName = currentTeam?.value?.name ?: "Not Provided"
            Text(text = currentTeamName,
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

            if(teams.isEmpty()){
                GetStartedItem("Select A Team\n\nTo Get Started", Modifier
                    .padding(top = 20.dp, bottom = 40.dp)
                    .constrainAs(teamsList) {
                        top.linkTo(descriptionText.bottom)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })
            }else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(15.dp),
                    modifier = Modifier
                        .padding(top = 20.dp, bottom = 20.dp)
                        .constrainAs(teamsList) {
                            height = Dimension.fillToConstraints
                            top.linkTo(descriptionText.bottom)
                            bottom.linkTo(parent.bottom)
                        }) {
                    items(teams) { team ->
                        val dismissState = rememberDismissState(
                            confirmStateChange = {
                                if (it == DismissValue.DismissedToEnd || it == DismissValue.DismissedToStart) {
                                    viewModel.removeSelectedTeam(team)
                                }
                                true
                            }
                        )

                        SwipeToDismiss(
                            state = dismissState,
                            directions = setOf(DismissDirection.EndToStart),
                            dismissThresholds = { FractionalThreshold(0.2f) },
                            background = {
                                val alignment = Alignment.CenterEnd
                                val color by animateColorAsState(targetValue = Color.Red)
                                val icon = Icons.Default.Delete
                                val scale by animateFloatAsState(
                                    targetValue =
                                    if (dismissState.targetValue == DismissValue.Default)
                                        0.8f
                                    else
                                        1.2f
                                )

                                Card(
                                    shape = RoundedCornerShape(5.dp),
                                    modifier = Modifier.fillMaxWidth()
                                        .fillParentMaxHeight()
                                ) {
                                    Box(
                                        contentAlignment = alignment,
                                        modifier = Modifier.fillMaxWidth()
                                            .background(color)
                                            .padding(start = 12.dp, end = 12.dp)
                                    ) {
                                        Icon(
                                            icon,
                                            contentDescription = "Icon",
                                            modifier = Modifier.scale(scale)
                                        )
                                    }
                                }
                            },
                            dismissContent = {
                                TeamItem(
                                    viewModel = viewModel,
                                    dismissState = dismissState,
                                    team = team,
                                    modifier = Modifier
                                        .wrapContentSize()
                                )
                            }
                        )
                    }
                }
            }

            AddMoreClickableText(viewModel, isVisible = addTeamVisibility.value,
                Modifier.constrainAs(addATeamText) {
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
            )
        }
    }
}

@Composable
fun AddMoreClickableText(viewModel: DashboardViewModel, isVisible: Boolean, modifier: Modifier) {
    if (isVisible) {
        ClickableText(
            onClick = { viewModel.navigateToAddTeam() }, text = AnnotatedString("Add Team"),
            style = TextStyle(
                color = HyperLinkBlue,
                fontSize = 20.sp
            ),
            modifier = modifier
                .padding(15.dp)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TeamItem(
    viewModel: DashboardViewModel,
    team: Team,
    dismissState: DismissState,
    modifier: Modifier
) =
    Card(backgroundColor = CardBackgroundColor,
        elevation = animateDpAsState(
        targetValue =
        if (dismissState.dismissDirection != null)
            5.dp
        else
            0.dp
    ).value,
        modifier = modifier,
        onClick = { viewModel.updateCurrentTeam(team) }) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val (crest, name) = createRefs()
            GlideImage(imageModel = team.logo,
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

            val teamName = team.name ?: "Not Provided"
            Text(text = teamName,
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TeamItem(teamName: String, crestUrl: String, modifier: Modifier) =
    Card(elevation = 0.dp, modifier = modifier, onClick = { }) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val (crest, name) = createRefs()
            Image(painter = painterResource(id = R.drawable.ic_baseline_sports_soccer_24),
                contentDescription = "",
                modifier = Modifier
                    .padding(end = 5.dp, start = 5.dp, top = 15.dp, bottom = 15.dp)
                    .constrainAs(crest) {
                        start.linkTo(parent.start)
                        top.linkTo(name.top)
                        bottom.linkTo(name.bottom)
                    }
            )

            Text(text = teamName,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(end = 5.dp, start = 5.dp, top = 15.dp, bottom = 15.dp)
                    .constrainAs(name) {
                        top.linkTo(parent.top)
                        start.linkTo(crest.end)
                    }
            )
        }
    }
