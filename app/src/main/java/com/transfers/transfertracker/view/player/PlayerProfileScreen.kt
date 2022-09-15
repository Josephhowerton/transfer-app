package com.transfers.transfertracker.view.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.skydoves.landscapist.glide.GlideImage
import com.transfers.transfertracker.R
import com.transfers.transfertracker.model.player.Player
import com.transfers.transfertracker.model.player.PlayerStatistic
import com.transfers.transfertracker.view.player.PlayerProfileViewModel
import com.transfers.transfertracker.view.theme.*

@Preview(showBackground = true)
@Composable
private fun PlayerProfileScreen() = TransferTrackerTheme {

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
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

            PlayerPreview(Modifier.constrainAs(profile) {
                top.linkTo(parent.top, 10.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })

            PlayerDetailsPreview(Modifier
                .constrainAs(details) {
                    top.linkTo(profile.bottom, 15.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })

            PlayerMedicalPreview(Modifier.constrainAs(medical) {
                top.linkTo(details.bottom, 15.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })

            PlayerAttackingPreview(Modifier.constrainAs(attacking) {
                top.linkTo(medical.bottom, 15.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })

            PlayerDefensePreview(Modifier.constrainAs(defending) {
                top.linkTo(attacking.bottom, 15.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })

            PlayerDisciplinePreview(
                Modifier
                    .padding(bottom = 10.dp)
                    .constrainAs(
                        discipline
                    ) {
                        top.linkTo(defending.bottom, 15.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })
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
fun PlayerProfile(viewModel: PlayerProfileViewModel) = TransferTrackerTheme {
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

        val player = remember {
            viewModel.playerInfo?.value
        }

        val playerStats = remember {
            viewModel.playerStatistic?.value
        }

        if(player != null && playerStats != null){
            Player(viewModel, player, playerStats, Modifier.constrainAs(profile) {
                top.linkTo(parent.top, 10.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })

            PlayerDetails(player, playerStats, Modifier
                .constrainAs(details) {
                    top.linkTo(profile.bottom, 15.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })

            PlayerMedical(player, playerStats, Modifier.constrainAs(medical) {
                top.linkTo(details.bottom, 15.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })

            PlayerAttacking(playerStats, Modifier.constrainAs(attacking) {
                top.linkTo(medical.bottom, 15.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })

            PlayerDefense(playerStats, Modifier.constrainAs(defending) {
                top.linkTo(attacking.bottom, 15.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })

            PlayerDiscipline(playerStats,
                Modifier
                    .padding(bottom = 10.dp)
                    .constrainAs(discipline) {
                        top.linkTo(defending.bottom, 15.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })
        }
    }
}

@Composable
private fun Player(viewModel: PlayerProfileViewModel, player: Player, playerStatistic: PlayerStatistic, modifier: Modifier) = TransferTrackerTheme {
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
                playerRating,
                comparePlayersText) = createRefs()

            GlideImage(imageModel = player.photo,
                contentDescription = "",
                modifier = Modifier
                    .size(128.dp)
                    .clip(CircleShape)
                    .padding(10.dp)
                    .background(Color.DarkGray, CircleShape)
                    .border(2.dp, Color.White, CircleShape)
                    .constrainAs(playerImage) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
            )

            val rating: String = playerStatistic.games?.rating ?: "No Information"
            val ratingDouble = playerStatistic.games?.rating?.toDouble() ?: 0.0
            Text(text = rating,
                color = if(ratingDouble <= 5.0) {
                    PlayerRatingLow
                }
                else if(ratingDouble > 5.0 && ratingDouble <= 7.5) {
                    PlayerRatingAverage
                }
                else if(ratingDouble > 7.5 && ratingDouble < 9.0) {
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
                        bottom.linkTo(parent.bottom)
                        start.linkTo(playerImage.start)
                    }
            )

            val injured = player.injured ?: false

            InjuredIcon(isVisible = injured, modifier = Modifier.constrainAs(injuredIcon) {
                bottom.linkTo(playerImage.bottom)
                end.linkTo(playerImage.end)
            })

            val name = player.name ?: "Not Provided"
            Text(text = name,
                maxLines = 1,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(start = 2.dp)
                    .constrainAs(playerName) {
                        top.linkTo(playerImage.top, 25.dp)
                        start.linkTo(playerImage.end, 25.dp)
                    }
            )

            val flagDescription = if(player.nationality != null){
                "${player.nationality} flag"
            }
            else{
                "Not Provided"
            }
            Image(painter = painter,
                contentDescription = flagDescription,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .constrainAs(playerCountryFlag) {
                        top.linkTo(playerName.bottom, 10.dp)
                        start.linkTo(playerName.start)
                    }
            )

            val logo = playerStatistic.team?.logo ?: R.drawable.ic_baseline_sports_soccer_24
            GlideImage(imageModel = logo,
                contentDescription = "",
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .constrainAs(playerTeam) {
                        top.linkTo(playerCountryFlag.top)
                        start.linkTo(playerCountryFlag.end, 15.dp)
                    }
            )

            Text(text = name,
                maxLines = 1,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(start = 2.dp)
                    .constrainAs(playerRating) {
                        top.linkTo(playerTeam.top)
                        start.linkTo(playerTeam.end, 15.dp)
                    }
            )

            ClickableText(onClick = { }, text = AnnotatedString("Compare"),
                style = TextStyle(
                    color = HyperLinkBlue,
                    fontSize = 20.sp
                ),
                modifier = Modifier
                    .padding(15.dp)
                    .constrainAs(comparePlayersText) {
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
            )
        }
    }
}

@Composable
private fun PlayerDetails(player: Player, statistic: PlayerStatistic, modifier: Modifier) = TransferTrackerTheme {
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

            Text(text = "Information",
                maxLines = 1,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(start = 20.dp)
                    .constrainAs(title) {
                        top.linkTo(parent.top, 15.dp)
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

            val position = statistic.games?.position ?: "Not Provided"
            DataItem(stat = "Position",
                value = position,
                modifier = Modifier
                    .padding(15.dp)
                    .wrapContentSize()
                    .constrainAs(playerPosition) {
                        top.linkTo(divider.bottom)
                        start.linkTo(parent.start)
                    })

            val age = player.age?.toString() ?: "Not Provided"
            DataItem(stat = "Age",
                value = age,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerAge) {
                        top.linkTo(playerPosition.bottom)
                        start.linkTo(parent.start)
                    })

            val height = player.height ?: "Not Provided"
            DataItem(stat = "Height",
                value = height,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerHeight) {
                        top.linkTo(playerAge.bottom)
                        start.linkTo(parent.start)
                    })

            val weight = player.weight ?: "Not Provided"
            DataItem(stat = "Weight",
                value = weight,
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
private fun PlayerMedical(player: Player, statistic: PlayerStatistic, modifier: Modifier) = TransferTrackerTheme {
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
                        top.linkTo(parent.top, 15.dp)
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

            val appearances = statistic.games?.appearences?.toString() ?: "Not Provided"
            DataItem(stat = "Appearances",
                value = appearances,
                modifier = Modifier
                    .padding(15.dp)
                    .wrapContentSize()
                    .constrainAs(playerAppearances) {
                        top.linkTo(divider.bottom)
                        start.linkTo(parent.start)
                    })

            val lineups = statistic.games?.lineups?.toString() ?: "Not Provided"
            DataItem(stat = "Starting",
                value = lineups,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerStarting) {
                        start.linkTo(parent.start)
                        top.linkTo(playerAppearances.bottom)
                    })

            val minutes = statistic.games?.minutes?.toString() ?: "Not Provided"
            DataItem(stat = "Minutes",
                value = minutes,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerMinutes) {
                        start.linkTo(parent.start)
                        top.linkTo(playerStarting.bottom)
                    })

            val isInjured = player.injured
            val injury = if(isInjured != null){
                if(isInjured){
                    "Yes"
                }
                else{
                    "No"
                }
            }
            else{
                "Not Provided"
            }
            DataItem(stat = "Injured",
                value = injury,
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
private fun PlayerAttacking(statistic: PlayerStatistic, modifier: Modifier) = TransferTrackerTheme {
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
                        top.linkTo(parent.top, 15.dp)
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

            val shots = statistic.shots?.total?.toString() ?: "Not Provided"
            DataItem(stat = "Shots",
                value = shots,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerShots) {
                        top.linkTo(divider.bottom)
                        start.linkTo(parent.start)
                    })

            val shotsOnTarget = statistic.shots?.on?.toString() ?: "Not Provided"
            DataItem(stat = "Shots On Target",
                value = shotsOnTarget,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerShotsOnTarget) {
                        top.linkTo(playerShots.bottom)
                        start.linkTo(parent.start)
                    })

            val goals = statistic.goals?.total?.toString() ?: "Not Provided"
            DataItem(stat = "Goals",
                value = goals,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerGoals) {
                        top.linkTo(playerShotsOnTarget.bottom)
                        start.linkTo(parent.start)
                    })

            val assists = statistic.goals?.assists?.toString() ?: "Not Provided"
            DataItem(stat = "Assists",
                value = assists,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerAssists) {
                        top.linkTo(playerGoals.bottom)
                        start.linkTo(parent.start)
                    })

            val dribblesAttempted = statistic.dribbles?.attempts?.toString() ?: "Not Provided"
            DataItem(stat = "Dribble Attempts",
                value = dribblesAttempted,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerDribbleAttempts) {
                        top.linkTo(playerAssists.bottom)
                        start.linkTo(parent.start)
                    })

            val dribblesSuccessful = statistic.dribbles?.success?.toString() ?: "Not Provided"
            DataItem(stat = "Successful Dribbles",
                value = dribblesSuccessful,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerDribbleSuccess) {
                        top.linkTo(playerDribbleAttempts.bottom)
                        start.linkTo(parent.start)
                    })

            val keyPasses = statistic.passes?.key?.toString() ?: "Not Provided"
            DataItem(stat = "Key Passes",
                value = keyPasses,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerKeyPasses) {
                        top.linkTo(playerDribbleSuccess.bottom)
                        start.linkTo(parent.start)
                    })

            val passAccuracy = statistic.passes?.accuracy?.toString() ?: "Not Provided"
            DataItem(stat = "Pass Accuracy",
                value = passAccuracy,
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
private fun PlayerDefense(statistic: PlayerStatistic, modifier: Modifier) = TransferTrackerTheme {
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
                        top.linkTo(parent.top, 15.dp)
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

            val tackles = statistic.tackles?.total?.toString() ?: "Not Provided"
            DataItem(stat = "Tackles",
                value = tackles,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerTackles) {
                        top.linkTo(divider.bottom)
                        start.linkTo(parent.start)
                    })

            val blocks = statistic.tackles?.blocks?.toString() ?: "Not Provided"
            DataItem(stat = "Blocks",
                value = blocks,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerBlocks) {
                        top.linkTo(playerTackles.bottom)
                        start.linkTo(parent.start)
                    })

            val interceptions = statistic.tackles?.interceptions?.toString() ?: "Not Provided"
            DataItem(stat = "Interceptions",
                value = interceptions,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerInterceptions) {
                        top.linkTo(playerBlocks.bottom)
                        start.linkTo(parent.start)
                    })

            val duelsTotal = statistic.duels?.total?.toString() ?: "Not Provided"
            DataItem(stat = "Duels",
                value = duelsTotal,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerDuels) {
                        top.linkTo(playerInterceptions.bottom)
                        start.linkTo(parent.start)
                    })

            val duelsWon = statistic.duels?.won?.toString() ?: "Not Provided"
            DataItem(stat = "Duels Won",
                value = duelsWon,
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
private fun PlayerDiscipline(statistic: PlayerStatistic, modifier: Modifier) = TransferTrackerTheme {
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
                        top.linkTo(parent.top, 15.dp)
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

            val foulsCommitted = statistic.fouls?.committed?.toString() ?: "Not Provided"
            DataItem(stat = "Fouls Committed",
                value = foulsCommitted,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerFoulsCommitted) {
                        top.linkTo(divider.bottom)
                        start.linkTo(parent.start)
                    })

            val foulsDrawn = statistic.fouls?.drawn?.toString() ?: "Not Provided"
            DataItem(stat = "Fouls Drawn",
                value = foulsDrawn,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerFoulsDrawn) {
                        top.linkTo(playerFoulsCommitted.bottom)
                        start.linkTo(parent.start)
                    })

            val penaltiesCommitted = statistic.penalty?.commited?.toString() ?: "Not Provided"
            DataItem(stat = "Penalties Committed",
                value = penaltiesCommitted,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerPenaltiesCommitted) {
                        top.linkTo(playerFoulsDrawn.bottom)
                        start.linkTo(parent.start)
                    })

            val penaltiesWon = statistic.penalty?.won?.toString() ?: "Not Provided"
            DataItem(stat = "Penalties Won",
                value = penaltiesWon,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerPenaltiesDrawn) {
                        top.linkTo(playerPenaltiesCommitted.bottom)
                        start.linkTo(parent.start)
                    })

            val red = statistic.cards?.red?.toString() ?: "Not Provided"
            DataItem(stat = "Reds",
                value = red,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerReds) {
                        top.linkTo(playerPenaltiesDrawn.bottom)
                        start.linkTo(parent.start)
                    })

            val yellow = statistic.cards?.yellow?.toString() ?: "Not Provided"
            DataItem(stat = "Yellows",
                value = yellow,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerYellows) {
                        top.linkTo(playerReds.bottom)
                        start.linkTo(parent.start)
                    })

            val yellowRed = statistic.cards?.yellowred?.toString() ?: "Not Provided"
            DataItem(stat = "Yellow to Reds",
                value = yellowRed,
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

/*
Preview
 */

@Composable
private fun PlayerPreview(modifier: Modifier) = TransferTrackerTheme {
    Card(elevation = 4.dp,
        shape = RoundedCornerShape(2),
        modifier = modifier
            .fillMaxWidth()
            .height(175.dp)) {

        ConstraintLayout(modifier = Modifier.fillMaxSize()) {

            val (playerImage,
                playerName,
                injuredIcon,
                playerTeam,
                playerRating,
                playerCountryFlag,
                comparePlayersText) = createRefs()

            Image(painter = painterResource(id = R.drawable.ic_baseline_person_24),
                contentDescription = "",
                modifier = Modifier
                    .size(128.dp)
                    .clip(CircleShape)
                    .padding(10.dp)
                    .background(Color.DarkGray, CircleShape)
                    .border(2.dp, Color.White, CircleShape)
                    .constrainAs(playerImage) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
            )

            Image(painter = painterResource(id = R.drawable.ic_injured_medical),
                contentDescription = "",
                modifier = Modifier
                    .size(48.dp)
                    .padding(10.dp)
                    .constrainAs(injuredIcon) {
                        bottom.linkTo(playerImage.bottom)
                        end.linkTo(playerImage.end)
                    }
            )

            Text(text = "6.789",
                color = PlayerRatingAverage,
                maxLines = 1,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(15.dp)
                    .constrainAs(playerRating) {
                        end.linkTo(playerImage.end)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(playerImage.start)
                    }
            )

            Text(text = "Rodrigo Bentacur",
                maxLines = 1,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(start = 2.dp)
                    .constrainAs(playerName) {
                        top.linkTo(playerImage.top, 25.dp)
                        start.linkTo(playerImage.end, 25.dp)
                    }
            )

            Image(painter = painterResource(id = R.drawable.ic_croatia),
                contentDescription = "",
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


            ClickableText(onClick = {}, text = AnnotatedString("Compare"),
                style = TextStyle(
                    color = HyperLinkBlue,
                    fontSize = 20.sp
                ),
                modifier = Modifier
                    .padding(15.dp)
                    .constrainAs(comparePlayersText) {
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
            )
        }
    }
}

@Composable
private fun PlayerDetailsPreview(modifier: Modifier) = TransferTrackerTheme {
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

            Text(text = "Information",
                maxLines = 1,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(start = 20.dp)
                    .constrainAs(title) {
                        top.linkTo(parent.top, 15.dp)
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
                value = "Midfielder",
                modifier = Modifier
                    .padding(15.dp)
                    .wrapContentSize()
                    .constrainAs(playerPosition) {
                        top.linkTo(divider.bottom)
                        start.linkTo(parent.start)
                    })

            DataItem(stat = "Age",
                value = "26",
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerAge) {
                        top.linkTo(playerPosition.bottom)
                        start.linkTo(parent.start)
                    })

            DataItem(stat = "Height",
                value = "187 cm",
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerHeight) {
                        top.linkTo(playerAge.bottom)
                        start.linkTo(parent.start)
                    })

            DataItem(stat = "Weight",
                value = "80 kg",
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
private fun PlayerMedicalPreview(modifier: Modifier) = TransferTrackerTheme {
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
                        top.linkTo(parent.top, 15.dp)
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

            DataItem(stat = "Appearances",
                value = "28",
                modifier = Modifier
                    .padding(15.dp)
                    .wrapContentSize()
                    .constrainAs(playerAppearances) {
                        top.linkTo(divider.bottom)
                        start.linkTo(parent.start)
                    })

            DataItem(stat = "Starting",
                value = "26",
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerStarting) {
                        start.linkTo(parent.start)
                        top.linkTo(playerAppearances.bottom)
                    })

            DataItem(stat = "Minutes",
                value = "2355",
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerMinutes) {
                        start.linkTo(parent.start)
                        top.linkTo(playerStarting.bottom)
                    })

            DataItem(stat = "Injured",
                value = "No",
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
private fun PlayerAttackingPreview(modifier: Modifier) = TransferTrackerTheme {
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
                        top.linkTo(parent.top, 15.dp)
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

            DataItem(stat = "Shots",
                value = "2",
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerShots) {
                        top.linkTo(divider.bottom)
                        start.linkTo(parent.start)
                    })

            DataItem(stat = "Shots On Target",
                value = "2",
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerShotsOnTarget) {
                        top.linkTo(playerShots.bottom)
                        start.linkTo(parent.start)
                    })

            DataItem(stat = "Goals",
                value = "0",
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerGoals) {
                        top.linkTo(playerShotsOnTarget.bottom)
                        start.linkTo(parent.start)
                    })

            DataItem(stat = "Assists",
                value = "2",
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerAssists) {
                        top.linkTo(playerGoals.bottom)
                        start.linkTo(parent.start)
                    })

            DataItem(stat = "Dribble Attempts",
                value = "4",
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerDribbleAttempts) {
                        top.linkTo(playerAssists.bottom)
                        start.linkTo(parent.start)
                    })


            DataItem(stat = "Successful Dribbles",
                value = "4",
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerDribbleSuccess) {
                        top.linkTo(playerDribbleAttempts.bottom)
                        start.linkTo(parent.start)
                    })

            DataItem(stat = "Key Passes",
                value = "4",
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerKeyPasses) {
                        top.linkTo(playerDribbleSuccess.bottom)
                        start.linkTo(parent.start)
                    })

            DataItem(stat = "Pass Accuracy",
                value = "43",
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
private fun PlayerDefensePreview(modifier: Modifier) = TransferTrackerTheme {
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
                        top.linkTo(parent.top, 15.dp)
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

            DataItem(stat = "Tackles",
                value = "16",
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerTackles) {
                        top.linkTo(divider.bottom)
                        start.linkTo(parent.start)
                    })

            DataItem(stat = "Blocks",
                value = "17",
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerBlocks) {
                        top.linkTo(playerTackles.bottom)
                        start.linkTo(parent.start)
                    })

            DataItem(stat = "Interceptions",
                value = "25",
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerInterceptions) {
                        top.linkTo(playerBlocks.bottom)
                        start.linkTo(parent.start)
                    })

            DataItem(stat = "Duels",
                value = "134",
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerDuels) {
                        top.linkTo(playerInterceptions.bottom)
                        start.linkTo(parent.start)
                    })

            DataItem(stat = "Duels Won",
                value = "77",
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
private fun PlayerDisciplinePreview(modifier: Modifier) = TransferTrackerTheme {
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
                        top.linkTo(parent.top, 15.dp)
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

            DataItem(stat = "Fouls Committed",
                value = "19",
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerFoulsCommitted) {
                        top.linkTo(divider.bottom)
                        start.linkTo(parent.start)
                    })

            DataItem(stat = "Fouls Drawn",
                value = "6",
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerFoulsDrawn) {
                        top.linkTo(playerFoulsCommitted.bottom)
                        start.linkTo(parent.start)
                    })

            DataItem(stat = "Penalties Committed",
                value = "0",
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerPenaltiesCommitted) {
                        top.linkTo(playerFoulsDrawn.bottom)
                        start.linkTo(parent.start)
                    })

            DataItem(stat = "Penalties Drawn",
                value = "0",
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerPenaltiesDrawn) {
                        top.linkTo(playerPenaltiesCommitted.bottom)
                        start.linkTo(parent.start)
                    })

            DataItem(stat = "Reds",
                value = "0",
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerReds) {
                        top.linkTo(playerPenaltiesDrawn.bottom)
                        start.linkTo(parent.start)
                    })

            DataItem(stat = "Yellows",
                value = "4",
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(playerYellows) {
                        top.linkTo(playerReds.bottom)
                        start.linkTo(parent.start)
                    })

            DataItem(stat = "Yellow to Reds",
                value = "0",
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