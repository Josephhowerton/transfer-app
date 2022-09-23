package com.transfers.transfertracker.view.player

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.skydoves.landscapist.glide.GlideImage
import com.transfers.transfertracker.R
import com.transfers.transfertracker.view.component.TransferTrackerAlertDialog
import com.transfers.transfertracker.view.component.TransferTopAppBar
import com.transfers.transfertracker.view.theme.*

@Preview(showBackground = true)
@Composable
private fun Player() = TransferTrackerTheme {
    Card(elevation = 1.dp,
        shape = RoundedCornerShape(2),
        modifier = Modifier
            .fillMaxWidth()
            .height(175.dp)) {

        ConstraintLayout(modifier = Modifier.fillMaxSize()) {

            val (playerImage,
                playerName,
                injuredIcon,
                playerTeam,
                playerCountryFlag,
                playerRating) = createRefs()

            Image(painter = painterResource(id = R.drawable.ic_baseline_person_24),
                contentDescription = "",
                modifier = Modifier
                    .size(128.dp)
                    .clip(CircleShape)
                    .padding(10.dp)
                    .background(CardBackgroundColor, CircleShape)
                    .border(2.dp, CardBackgroundColor, CircleShape)
                    .constrainAs(playerImage) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
            )

            Text(text = "8.0",
                color = PlayerRatingHigh,
                maxLines = 1,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(15.dp)
                    .wrapContentSize()
                    .constrainAs(playerRating) {
                        bottom.linkTo(parent.bottom)
                        end.linkTo(playerImage.end)
                        start.linkTo(playerImage.start)
                    }
            )

            InjuredIcon(isVisible = true, modifier = Modifier
                .constrainAs(injuredIcon) {
                    bottom.linkTo(playerImage.bottom)
                    end.linkTo(playerImage.end)
                }
            )

            Text(text = "Richarlison",
                maxLines = 1,
                fontSize = 24.sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(start = 2.dp)
                    .wrapContentSize()
                    .constrainAs(playerName) {
                        top.linkTo(playerImage.top, 25.dp)
                        start.linkTo(playerImage.end, 25.dp)
                    }
            )

            Image(painter = painterResource(id = R.drawable.ic_baseline_flag_24),
                contentDescription = "Brazil",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .constrainAs(playerCountryFlag) {
                        top.linkTo(playerName.bottom, 10.dp)
                        start.linkTo(playerName.start)
                    }
            )

            Image(painter = painterResource(id = R.drawable.ic_tottenham),
                contentDescription = "",
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .constrainAs(playerTeam) {
                        top.linkTo(playerCountryFlag.top)
                        start.linkTo(playerCountryFlag.end, 15.dp)
                    }
            )
        }
    }
}

@Composable
private fun DataItem(stat: String, value: String, modifier: Modifier) = TransferTrackerTheme {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
    ) {
        val (statText, valueText) = createRefs()

        Text(text = stat,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(end = 5.dp, start = 25.dp)
                .constrainAs(statText) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                }
        )

        Text(text = value,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(end = 50.dp)
                .constrainAs(valueText) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
        )
    }
}

@Composable
private fun InjuredIcon(isVisible: Boolean, modifier: Modifier) {
    if (isVisible) {
        Image(painter = painterResource(id = R.drawable.ic_injured_medical),
            contentDescription = "",
            modifier = modifier
                .size(48.dp)
                .padding(10.dp)
        )
    }
}

/*
Components
 */
@Composable
fun PlayerProfile(playerId: String?, teamId: String?, leagueId: String?, onErrorAction: () -> Unit) = TransferTrackerTheme {
    Scaffold(topBar = {

        TransferTopAppBar(stringResource(id = R.string.title_player_profile), false, onErrorAction)

    }) {
        if (playerId == null || teamId == null) {
            TransferTrackerAlertDialog(
                title = R.string.title_generic_error,
                message = R.string.message_generic_error,
                buttonTitle = R.string.title_dismiss_button,
                showErrorDialog = true,
                onClick = onErrorAction
            )
        } else {
            PlayerProfileScreen(playerId, teamId, leagueId, onErrorAction)
        }
    }
}

/*
Components
 */
@Composable
private fun PlayerProfileScreen(playerId: String, teamId: String, leagueId: String?, onErrorAction: () -> Unit) = TransferTrackerTheme {
    val viewModel:PlayerProfileViewModel = hiltViewModel()

    DisposableEffect(key1 = viewModel) {
        onDispose { viewModel.onDestroy() }
    }

    if(!leagueId.isNullOrEmpty() && leagueId != "ignore"){
        viewModel.fetchPlayerProfile(playerId = playerId, teamId = teamId, leagueId = leagueId)
    }
    else{
        viewModel.fetchPlayerProfile(playerId = playerId, teamId = teamId)
    }
    ConstraintLayout(Modifier.verticalScroll(
        state = rememberScrollState(),
    )){

        val (profile,
            details,
            medical,
            attacking,
            defending,
            discipline,
        ) = createRefs()

        val showErrorDialog = remember { viewModel.shouldShowErrorDialog }
        val errorTitle = remember { viewModel.errorTitle }
        val errorMessage = remember { viewModel.errorMessage }

        TransferTrackerAlertDialog(
            title = errorTitle.value,
            message = errorMessage.value,
            buttonTitle = R.string.title_dismiss_button,
            showErrorDialog = showErrorDialog.value,
            onClick = onErrorAction
        )

        Player(viewModel, Modifier.constrainAs(profile) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })

        PlayerDetails(viewModel, Modifier.constrainAs(details) {
                top.linkTo(profile.bottom, 15.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })

        PlayerMedical(viewModel, Modifier.constrainAs(medical) {
            top.linkTo(details.bottom, 15.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })

        PlayerAttacking(viewModel, Modifier.constrainAs(attacking) {
            top.linkTo(medical.bottom, 15.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })

        PlayerDefense(viewModel, Modifier.constrainAs(defending) {
            top.linkTo(attacking.bottom, 15.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })

        PlayerDiscipline(viewModel,
            Modifier
                .padding(bottom = 10.dp)
                .constrainAs(discipline) {
                    top.linkTo(defending.bottom, 15.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })
    }
}

@Composable
private fun Player(viewModel: PlayerProfileViewModel, modifier: Modifier) = TransferTrackerTheme {
    Card(elevation = 4.dp,
        shape = RoundedCornerShape(2),
        modifier = modifier
            .fillMaxWidth()
            .height(175.dp)) {

        ConstraintLayout(modifier = Modifier.fillMaxSize()) {

            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .decoderFactory(SvgDecoder.Factory())
                    .data(viewModel.playerCountryFlag.value)
                    .build()
            )

            val (playerImage,
                playerName,
                injuredIcon,
                playerTeam,
                playerCountryFlag,
                playerRating) = createRefs()

            val photo = remember {
                viewModel.playerPhoto
            }

            val rating = remember {
                viewModel.playerRating
            }

            val ratingDouble = remember {
                viewModel.playerRatingDouble
            }

            val injured = remember {
                viewModel.playerInjured
            }

            val name = remember {
                viewModel.playerName
            }

            val flagDescription = remember {
                viewModel.playerCountryFlagDescription
            }

            val logo = remember {
                viewModel.playerLogo
            }

            GlideImage(imageModel = photo.value,
                contentDescription = "",
                modifier = Modifier
                    .size(128.dp)
                    .padding(10.dp)
                    .clip(CircleShape)
                    .background(LightGrey, CircleShape)
                    .border(1.dp, LightGrey, CircleShape)
                    .constrainAs(playerImage) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
            )

            Text(text = rating.value,
                color = if(ratingDouble.value <= 5.0) {
                    PlayerRatingLow
                }
                else if(ratingDouble.value > 5.0 && ratingDouble.value < 7.5) {
                    PlayerRatingAverage
                }
                else if(ratingDouble.value >= 7.5 && ratingDouble.value < 9.0) {
                    PlayerRatingHigh
                }
                else{
                    PlayerRatingElite
                },
                maxLines = 1,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(15.dp)
                    .constrainAs(playerRating) {
                        end.linkTo(playerImage.end)
                        start.linkTo(playerImage.start)
                        bottom.linkTo(parent.bottom)
                    }
            )

            InjuredIcon(isVisible = injured.value, modifier = Modifier
                .constrainAs(injuredIcon) {
                    bottom.linkTo(playerImage.bottom)
                    end.linkTo(playerImage.end)
                }
            )

            Text(text = name.value,
                maxLines = 1,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(start = 2.dp)
                    .constrainAs(playerName) {
                        top.linkTo(playerImage.top, 25.dp)
                        start.linkTo(playerImage.end, 25.dp)
                    }
            )

            Image(painter = painter,
                contentDescription = flagDescription.value,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .constrainAs(playerCountryFlag) {
                        top.linkTo(playerName.bottom, 10.dp)
                        start.linkTo(playerName.start)
                    }
            )

            GlideImage(imageModel = logo.value,
                contentDescription = "",
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .constrainAs(playerTeam) {
                        top.linkTo(playerName.bottom, 10.dp)
                        start.linkTo(playerCountryFlag.end, 15.dp)
                    }
            )
        }
    }
}

@Composable
private fun PlayerDetails(viewModel: PlayerProfileViewModel, modifier: Modifier) = TransferTrackerTheme {
    Card(elevation = 1.dp,
        shape = RoundedCornerShape(10),
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {

        ConstraintLayout(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
        ) {

            val (title,
                divider,
                playerPosition,
                playerAge,
                playerHeight,
                playerWeight) = createRefs()

            val position = remember {
                viewModel.playerPosition
            }

            val age = remember {
                viewModel.playerAge
            }

            val height = remember {
                viewModel.playerHeight
            }

            val weight = remember {
                viewModel.playerWeight
            }

            Text(text = "Information",
                maxLines = 1,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(start = 20.dp)
                    .constrainAs(title) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
            )

            Divider(color = Color.DarkGray,
                thickness = 1.dp,
                modifier = Modifier
                    .padding(10.dp)
                    .constrainAs(divider) {
                        top.linkTo(title.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            DataItem(stat = "Position",
                value = position.value,
                modifier = Modifier
                    .padding(15.dp)
                    .wrapContentSize()
                    .constrainAs(playerPosition) {
                        top.linkTo(divider.bottom)
                        start.linkTo(parent.start)
                    })

            DataItem(stat = "Age",
                value = age.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerAge) {
                        top.linkTo(playerPosition.bottom)
                        start.linkTo(parent.start)
                    })

            DataItem(stat = "Height",
                value = height.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerHeight) {
                        top.linkTo(playerAge.bottom)
                        start.linkTo(parent.start)
                    })

            DataItem(stat = "Weight",
                value = weight.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerWeight) {
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                        top.linkTo(playerHeight.bottom)
                    })
        }
    }
}

@Composable
private fun PlayerMedical(viewModel: PlayerProfileViewModel, modifier: Modifier) = TransferTrackerTheme {
    Card(elevation = 1.dp,
        shape =  RoundedCornerShape(10),
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()) {

        ConstraintLayout(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()) {

            val (title,
                divider,
                playerAppearances,
                playerStarting,
                playerMinutes,
                playerInjured) = createRefs()

            Text(text = "Medical",
                maxLines = 1,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(start = 20.dp)
                    .constrainAs(title) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
            )

            Divider(color = Color.DarkGray,
                thickness = 1.dp,
                modifier = Modifier
                    .padding(10.dp)
                    .constrainAs(divider) {
                        top.linkTo(title.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            val appearances = remember { viewModel.playerAppearances }
            DataItem(stat = "Appearances",
                value = appearances.value,
                modifier = Modifier
                    .padding(15.dp)
                    .wrapContentSize()
                    .constrainAs(playerAppearances) {
                        top.linkTo(divider.bottom)
                        start.linkTo(parent.start)
                    })

            val lineups = remember { viewModel.playerLineups }
            DataItem(stat = "Starting",
                value = lineups.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerStarting) {
                        start.linkTo(parent.start)
                        top.linkTo(playerAppearances.bottom)
                    })

            val minutes = remember { viewModel.playerMinutes }
            DataItem(stat = "Minutes",
                value = minutes.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerMinutes) {
                        start.linkTo(parent.start)
                        top.linkTo(playerStarting.bottom)
                    })

            val isInjured = remember { viewModel.playerInjured }
            DataItem(stat = "Injured",
                value = if(isInjured.value){ "Yes" } else { "No" },
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerInjured) {
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                        top.linkTo(playerMinutes.bottom)
                    })
        }
    }
}

@Composable
private fun PlayerAttacking(viewModel: PlayerProfileViewModel, modifier: Modifier) = TransferTrackerTheme {
    Card(elevation = 1.dp,
        shape = RoundedCornerShape(10),
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {

        ConstraintLayout(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
        ) {

            val (title,
                divider,
                playerShots,
                playerShotsOnTarget,
                playerGoals,
                playerAssists,
                playerDribbleAttempts,
                playerDribbleSuccess,
                playerKeyPasses,
                playerPassAccuracy
            ) = createRefs()

            Text(text = "Attacking",
                maxLines = 1,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(start = 20.dp)
                    .constrainAs(title) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
            )

            Divider(color = Color.DarkGray,
                thickness = 1.dp,
                modifier = Modifier
                    .padding(10.dp)
                    .constrainAs(divider) {
                        top.linkTo(title.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            val shots = remember { viewModel.playerShots }
            DataItem(stat = "Shots",
                value = shots.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerShots) {
                        top.linkTo(divider.bottom)
                        start.linkTo(parent.start)
                    })

            val shotsOnTarget = remember { viewModel.playerShotsOnTarget }
            DataItem(stat = "Shots On Target",
                value = shotsOnTarget.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerShotsOnTarget) {
                        top.linkTo(playerShots.bottom)
                        start.linkTo(parent.start)
                    })

            val goals = remember { viewModel.playerGoals }
            DataItem(stat = "Goals",
                value = goals.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerGoals) {
                        top.linkTo(playerShotsOnTarget.bottom)
                        start.linkTo(parent.start)
                    })

            val assists = remember { viewModel.playerAssists }
            DataItem(stat = "Assists",
                value = assists.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerAssists) {
                        top.linkTo(playerGoals.bottom)
                        start.linkTo(parent.start)
                    })

            val dribblesAttempted = remember { viewModel.playerDribbleAttempts }
            DataItem(stat = "Dribble Attempts",
                value = dribblesAttempted.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerDribbleAttempts) {
                        top.linkTo(playerAssists.bottom)
                        start.linkTo(parent.start)
                    })

            val dribblesSuccessful = remember { viewModel.playerDribbleSuccess }
            DataItem(stat = "Successful Dribbles",
                value = dribblesSuccessful.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerDribbleSuccess) {
                        top.linkTo(playerDribbleAttempts.bottom)
                        start.linkTo(parent.start)
                    })

            val keyPasses = remember { viewModel.playerKeyPasses }
            DataItem(stat = "Key Passes",
                value = keyPasses.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerKeyPasses) {
                        top.linkTo(playerDribbleSuccess.bottom)
                        start.linkTo(parent.start)
                    })

            val passAccuracy = remember { viewModel.playerPassAccuracy }
            DataItem(stat = "Pass Accuracy",
                value = passAccuracy.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerPassAccuracy) {
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                        top.linkTo(playerKeyPasses.bottom)
                    })
        }
    }
}

@Composable
private fun PlayerDefense(viewModel: PlayerProfileViewModel, modifier: Modifier) = TransferTrackerTheme {
    Card(elevation = 1.dp,
        shape = RoundedCornerShape(10),
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {

        ConstraintLayout(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
        ) {

            val (title,
                divider,
                playerTackles,
                playerBlocks,
                playerInterceptions,
                playerDuels,
                playerDuelsWon) = createRefs()

            Text(text = "Defense",
                maxLines = 1,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(start = 20.dp)
                    .constrainAs(title) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
            )

            Divider(color = Color.DarkGray,
                thickness = 1.dp,
                modifier = Modifier
                    .padding(10.dp)
                    .constrainAs(divider) {
                        top.linkTo(title.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            val tackles = remember { viewModel.playerTackles }
            DataItem(stat = "Tackles",
                value = tackles.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerTackles) {
                        top.linkTo(divider.bottom)
                        start.linkTo(parent.start)
                    })

            val blocks = remember { viewModel.playerBlocks }
            DataItem(stat = "Blocks",
                value = blocks.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerBlocks) {
                        top.linkTo(playerTackles.bottom)
                        start.linkTo(parent.start)
                    })

            val interceptions = remember { viewModel.playerInterceptions }
            DataItem(stat = "Interceptions",
                value = interceptions.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerInterceptions) {
                        top.linkTo(playerBlocks.bottom)
                        start.linkTo(parent.start)
                    })

            val duelsTotal = remember { viewModel.playerDuels }
            DataItem(stat = "Duels",
                value = duelsTotal.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerDuels) {
                        top.linkTo(playerInterceptions.bottom)
                        start.linkTo(parent.start)
                    })

            val duelsWon = remember { viewModel.playerDuelsWon }
            DataItem(stat = "Duels Won",
                value = duelsWon.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerDuelsWon) {
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                        top.linkTo(playerDuels.bottom)
                    })
        }
    }
}

@Composable
private fun PlayerDiscipline(viewModel: PlayerProfileViewModel, modifier: Modifier) = TransferTrackerTheme {
    Card(elevation = 1.dp,
        shape = RoundedCornerShape(10),
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {

        ConstraintLayout(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
        ) {

            val (title,
                divider,
                playerFoulsCommitted,
                playerFoulsDrawn,
                playerPenaltiesCommitted,
                playerPenaltiesDrawn,
                playerReds,
                playerYellows,
                playerYellowRed,
            ) = createRefs()

            Text(text = "Discipline",
                maxLines = 1,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(start = 20.dp)
                    .constrainAs(title) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
            )

            Divider(color = Color.DarkGray,
                thickness = 1.dp,
                modifier = Modifier
                    .padding(10.dp)
                    .constrainAs(divider) {
                        top.linkTo(title.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            val foulsCommitted = remember { viewModel.playerFoulsCommitted }
            DataItem(stat = "Fouls Committed",
                value = foulsCommitted.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerFoulsCommitted) {
                        top.linkTo(divider.bottom)
                        start.linkTo(parent.start)
                    })

            val foulsDrawn = remember { viewModel.playerFoulsDrawn }
            DataItem(stat = "Fouls Drawn",
                value = foulsDrawn.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerFoulsDrawn) {
                        top.linkTo(playerFoulsCommitted.bottom)
                        start.linkTo(parent.start)
                    })

            val penaltiesCommitted = remember { viewModel.playerPenaltiesCommitted }
            DataItem(stat = "Penalties Committed",
                value = penaltiesCommitted.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerPenaltiesCommitted) {
                        top.linkTo(playerFoulsDrawn.bottom)
                        start.linkTo(parent.start)
                    })

            val penaltiesWon = remember { viewModel.playerPenaltiesDrawn }
            DataItem(stat = "Penalties Won",
                value = penaltiesWon.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerPenaltiesDrawn) {
                        top.linkTo(playerPenaltiesCommitted.bottom)
                        start.linkTo(parent.start)
                    })

            val red = remember { viewModel.playerReds }
            DataItem(stat = "Reds",
                value = red.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerReds) {
                        top.linkTo(playerPenaltiesDrawn.bottom)
                        start.linkTo(parent.start)
                    })

            val yellow = remember { viewModel.playerYellows }
            DataItem(stat = "Yellows",
                value = yellow.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerYellows) {
                        top.linkTo(playerReds.bottom)
                        start.linkTo(parent.start)
                    })

            val yellowRed = remember { viewModel.playerYellowRed }
            DataItem(stat = "Yellow to Reds",
                value = yellowRed.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerYellowRed) {
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                        top.linkTo(playerYellows.bottom)
                    })
        }
    }
}