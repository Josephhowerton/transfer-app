package com.transfers.transfertracker.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.transfers.transfertracker.R
import com.transfers.transfertracker.enums.Screen
import com.transfers.transfertracker.view.main.viewmodel.DashboardViewModel
import com.transfers.transfertracker.view.theme.TransferTrackerTheme

@Preview(showBackground = true)
@Composable
fun PlayerComponentScreenPreview() {
    PlayerComponentScreen(Modifier)
}

@Composable
fun PlayerComponentScreen(modifier: Modifier) = TransferTrackerTheme {
    Card(shape = RoundedCornerShape(2),
        modifier = modifier
            .fillMaxWidth()
            .height(450.dp)) {
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

            Text(text = "Team Players",
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

            Text(text = "Select a player below to view data.",
                modifier = Modifier.constrainAs(descriptionText) {
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                    top.linkTo(selectedTeam.bottom, 5.dp)
                }
            )

            PlayerItem(
                playerName = "Player 1",
                playerImageUrl = "",
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(secondTeam) {
                        top.linkTo(descriptionText.bottom)
                        bottom.linkTo(thirdTeam.top)
                    }
            )

            PlayerItem(
                playerName = "Player 2",
                playerImageUrl = "",
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(thirdTeam) {
                        top.linkTo(secondTeam.bottom)
                        bottom.linkTo(fourthTeam.top)
                    }
            )

            PlayerItem(
                playerName = "Player 3",
                playerImageUrl = "",
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(fourthTeam) {
                        top.linkTo(thirdTeam.bottom)
                        bottom.linkTo(fifthTeam.top)
                    }
            )

            PlayerItem(
                playerName = "Player 4",
                playerImageUrl = "",
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .constrainAs(fifthTeam) {
                        top.linkTo(fourthTeam.bottom)
                        bottom.linkTo(addATeamText.top)
                    }
            )

            ClickableText(onClick = {}, text = AnnotatedString("All Players"),
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
fun PlayerComponent(viewModel: DashboardViewModel,
                    navController: NavController,
                    modifier: Modifier) = TransferTrackerTheme {
    Card(shape = RoundedCornerShape(2),
        modifier = modifier
            .fillMaxWidth()
            .height(450.dp)) {
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

            Text(text = "Team Players",
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

            Text(text = "Select a player below to view data.",
                modifier = Modifier.constrainAs(descriptionText) {
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                    top.linkTo(selectedTeam.bottom, 5.dp)
                }
            )

            PlayerItem(
                navController,
                playerName = "Player 1",
                playerImageUrl = "",
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(secondTeam) {
                        top.linkTo(descriptionText.bottom)
                        bottom.linkTo(thirdTeam.top)
                    }
            )

            PlayerItem(
                navController,
                playerName = "Player 2",
                playerImageUrl = "",
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(thirdTeam) {
                        top.linkTo(secondTeam.bottom)
                        bottom.linkTo(fourthTeam.top)
                    }
            )

            PlayerItem(
                navController,
                playerName = "Player 3",
                playerImageUrl = "",
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(fourthTeam) {
                        top.linkTo(thirdTeam.bottom)
                        bottom.linkTo(fifthTeam.top)
                    }
            )

            PlayerItem(
                navController,
                playerName = "Player 4",
                playerImageUrl = "",
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .constrainAs(fifthTeam) {
                        top.linkTo(fourthTeam.bottom)
                        bottom.linkTo(addATeamText.top)
                    }
            )

            ClickableText(onClick = { navController.navigate(Screen.PLAYER_LIST.name) }, text = AnnotatedString("All Players"),
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
fun PlayerItem(playerName: String,
               playerImageUrl: String,
               modifier: Modifier) = Card(elevation = 0.dp, modifier = modifier) {
        ConstraintLayout(modifier = Modifier
            .fillMaxWidth()) {
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

            Text(text = playerName,
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlayerItem(navController: NavController,
               playerName: String,
               playerImageUrl: String,
               modifier: Modifier) =
    Card(elevation = 0.dp, modifier = modifier, onClick = { navController.navigate(Screen.PLAYER_DETAILS.name) }) {
        ConstraintLayout(modifier = Modifier
            .fillMaxWidth()) {
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

            Text(text = playerName,
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