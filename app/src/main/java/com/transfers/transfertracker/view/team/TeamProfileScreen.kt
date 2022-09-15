//package com.transfers.transfertracker.view.team
//
//import androidx.compose.foundation.*
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.constraintlayout.compose.ConstraintLayout
//import androidx.constraintlayout.compose.Dimension
//import coil.compose.rememberAsyncImagePainter
//import coil.decode.SvgDecoder
//import coil.request.ImageRequest
//import com.skydoves.landscapist.glide.GlideImage
//import com.transfers.transfertracker.R
//import com.transfers.transfertracker.model.stats.TeamStatistics
//import com.transfers.transfertracker.view.theme.*
//
//@Preview(showBackground = true)
//@Composable
//private fun TeamProfilePreview() = TransferTrackerTheme {
//
//    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
//        ConstraintLayout(
//            Modifier.verticalScroll(
//                state = rememberScrollState(),
//            )){
//
//            val (profile,
//                lineups,
//                form,
//                attacking,
//                defending,
//                discipline,
//            ) = createRefs()
//
//            TeamInfoPreview(Modifier.constrainAs(profile) {
//                top.linkTo(parent.top, 10.dp)
//                start.linkTo(parent.start)
//                end.linkTo(parent.end)
//            })
//
//            TeamLineupsPreview(Modifier.constrainAs(lineups) {
//                top.linkTo(profile.bottom, 15.dp)
//                start.linkTo(parent.start)
//                end.linkTo(parent.end)
//            })
//
//            TeamFormPreview(Modifier.constrainAs(form) {
//                top.linkTo(lineups.bottom, 15.dp)
//                start.linkTo(parent.start)
//                end.linkTo(parent.end)
//            })
//
//            TeamAttackingPreview(Modifier.constrainAs(attacking) {
//                top.linkTo(form.bottom, 15.dp)
//                start.linkTo(parent.start)
//                end.linkTo(parent.end)
//            })
//
//            TeamDefensePreview(Modifier.constrainAs(defending) {
//                top.linkTo(attacking.bottom, 15.dp)
//                start.linkTo(parent.start)
//                end.linkTo(parent.end)
//            })
//
//            TeamDisciplinePreview(
//                Modifier
//                    .padding(bottom = 10.dp)
//                    .constrainAs(
//                        discipline
//                    ) {
//                        top.linkTo(defending.bottom, 15.dp)
//                        start.linkTo(parent.start)
//                        end.linkTo(parent.end)
//                    })
//        }
//    }
//}
//
//@Composable
//fun TeamProfile(viewModel: TeamProfileViewModel) = TransferTrackerTheme {
//    ConstraintLayout(
//        Modifier.verticalScroll(
//            state = rememberScrollState(),
//        )){
//
//        val (profile,
//            lineups,
//            form,
//            attacking,
//            defending,
//            discipline,
//        ) = createRefs()
//
//        val teamStatistics = remember {
//            viewModel.statistics
//        }
//
//        teamStatistics?.run {
//            TeamInfo(teamStatistics.value, Modifier.constrainAs(profile) {
//                top.linkTo(parent.top, 10.dp)
//                start.linkTo(parent.start)
//                end.linkTo(parent.end)
//            })
//
//            TeamLineups(teamStatistics.value, Modifier.constrainAs(lineups) {
//                top.linkTo(profile.bottom, 15.dp)
//                start.linkTo(parent.start)
//                end.linkTo(parent.end)
//            })
//
//            TeamForm(teamStatistics.value, Modifier.constrainAs(form) {
//                top.linkTo(lineups.bottom, 15.dp)
//                start.linkTo(parent.start)
//                end.linkTo(parent.end)
//            })
//
//            TeamAttacking(teamStatistics.value, Modifier.constrainAs(attacking) {
//                top.linkTo(form.bottom, 15.dp)
//                start.linkTo(parent.start)
//                end.linkTo(parent.end)
//            })
//
//            TeamDefense(teamStatistics.value, Modifier.constrainAs(defending) {
//                top.linkTo(attacking.bottom, 15.dp)
//                start.linkTo(parent.start)
//                end.linkTo(parent.end)
//            })
//
//            TeamDiscipline(teamStatistics.value,
//                Modifier
//                    .padding(bottom = 10.dp)
//                    .constrainAs(discipline) {
//                        top.linkTo(defending.bottom, 15.dp)
//                        start.linkTo(parent.start)
//                        end.linkTo(parent.end)
//                    })
//        }
//    }
//}
//
//@Composable
//private fun DataItem(stat: String, value: String, modifier: Modifier) = TransferTrackerTheme {
//    ConstraintLayout(
//        modifier = modifier
//            .fillMaxWidth()
//    ) {
//        val (statText, valueText) = createRefs()
//
//        Text(text = stat,
//            fontSize = 20.sp,
//            modifier = Modifier
//                .padding(end = 5.dp, start = 25.dp)
//                .constrainAs(statText) {
//                    top.linkTo(parent.top)
//                    start.linkTo(parent.start)
//                    bottom.linkTo(parent.bottom)
//                }
//        )
//
//        Text(text = value,
//            fontSize = 20.sp,
//            modifier = Modifier
//                .padding(end = 50.dp)
//                .constrainAs(valueText) {
//                    top.linkTo(parent.top)
//                    end.linkTo(parent.end)
//                    bottom.linkTo(parent.bottom)
//                }
//        )
//    }
//}
//
//@Composable
//private fun DataItemWithExtra(stat: String, value1: String, value2: String, modifier: Modifier) = TransferTrackerTheme {
//    ConstraintLayout(
//        modifier = modifier
//            .fillMaxWidth()
//    ) {
//        val (statText, valueText, valueAvgText) = createRefs()
//
//        Text(text = stat,
//            fontSize = 20.sp,
//            modifier = Modifier
//                .padding(end = 5.dp, start = 25.dp, bottom = 5.dp)
//                .constrainAs(statText) {
//                    top.linkTo(parent.top)
//                    start.linkTo(parent.start)
//                    bottom.linkTo(valueAvgText.top)
//                }
//        )
//
//        Text(text = value1,
//            fontSize = 20.sp,
//            modifier = Modifier
//                .padding(end = 50.dp, bottom = 5.dp)
//                .constrainAs(valueText) {
//                    top.linkTo(parent.top)
//                    end.linkTo(parent.end)
//                    bottom.linkTo(valueAvgText.top)
//                }
//        )
//
//        Text(text = value2,
//            fontSize = 20.sp,
//            modifier = Modifier
//                .padding(end = 50.dp)
//                .constrainAs(valueAvgText) {
//                    end.linkTo(parent.end)
//                    bottom.linkTo(parent.bottom)
//                }
//        )
//    }
//}
//
//@Composable
//private fun DataItemWithAvg(stat: String, value: String, modifier: Modifier) = TransferTrackerTheme {
//    ConstraintLayout(
//        modifier = modifier
//            .fillMaxWidth()
//    ) {
//        val (statText, valueText) = createRefs()
//
//        Text(text = stat,
//            fontSize = 20.sp,
//            modifier = Modifier
//                .padding(end = 5.dp, start = 25.dp)
//                .constrainAs(statText) {
//                    top.linkTo(parent.top)
//                    start.linkTo(parent.start)
//                    bottom.linkTo(parent.bottom)
//                }
//        )
//
//        Text(text = value,
//            fontSize = 20.sp,
//            modifier = Modifier
//                .padding(end = 50.dp)
//                .constrainAs(valueText) {
//                    top.linkTo(parent.top)
//                    end.linkTo(parent.end)
//                    bottom.linkTo(parent.bottom)
//                }
//        )
//    }
//}
//
//@Composable
//private fun DataItemWithPercentage(stat: String, value: String, modifier: Modifier) = TransferTrackerTheme {
//    ConstraintLayout(
//        modifier = modifier
//            .fillMaxWidth()
//    ) {
//        val (statText, valueText) = createRefs()
//
//        Text(text = stat,
//            fontSize = 20.sp,
//            modifier = Modifier
//                .padding(end = 5.dp, start = 25.dp)
//                .constrainAs(statText) {
//                    top.linkTo(parent.top)
//                    start.linkTo(parent.start)
//                    bottom.linkTo(parent.bottom)
//                }
//        )
//
//        Text(text = value,
//            fontSize = 20.sp,
//            modifier = Modifier
//                .padding(end = 50.dp)
//                .constrainAs(valueText) {
//                    top.linkTo(parent.top)
//                    end.linkTo(parent.end)
//                    bottom.linkTo(parent.bottom)
//                }
//        )
//    }
//}
//
///*
//Components
// */
//@Composable
//private fun TeamInfo(teamStats: TeamStatistics, modifier: Modifier) = TransferTrackerTheme {
//    Card(elevation = 4.dp,
//        shape = RoundedCornerShape(2),
//        modifier = modifier
//            .fillMaxWidth()
//            .height(175.dp)) {
//
//        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
//
//            val league = teamStats.league
//            val team = teamStats.team
//
//            val painter = rememberAsyncImagePainter(
//                model = if(league?.country != null){
//                    ImageRequest.Builder(LocalContext.current)
//                        .decoderFactory(SvgDecoder.Factory())
//                        .data(league.country)
//                        .build()
//                } else{
//                    R.drawable.ic_baseline_flag_24
//                }
//            )
//
//            val currentTeamBitmap = team?.logo
//            val logo = if(currentTeamBitmap.isNullOrEmpty())
//                R.drawable.ic_baseline_sports_soccer_24
//            else
//                currentTeamBitmap
//
//            val (teamImage,
//                leagueName,
//                countryImage,
//                leagueImage,
//                teamName) = createRefs()
//
//            val tName = team?.name ?: "Not Provided"
//            GlideImage(imageModel = logo,
//                contentDescription = "${tName} logo",
//                contentScale = ContentScale.Inside,
//                modifier = Modifier
//                    .size(128.dp)
//                    .clip(CircleShape)
//                    .padding(10.dp)
//                    .border(2.dp, Color.White, CircleShape)
//                    .constrainAs(teamImage) {
//                        top.linkTo(parent.top)
//                        start.linkTo(parent.start)
//                    }
//            )
//
//            val lName = league?.name ?: "Not Provided"
//            Text(text = lName,
//                maxLines = 1,
//                fontSize = 20.sp,
//                modifier = Modifier
//                    .constrainAs(leagueName) {
//                        top.linkTo(teamImage.top, 25.dp)
//                        start.linkTo(teamImage.end, 25.dp)
//                    }
//            )
//
//            val country = if(league?.country != null) "${league.country} flag" else "flag"
//            Image(painter = painter,
//                contentDescription = "country",
//                modifier = Modifier
//                    .size(36.dp)
//                    .clip(CircleShape)
//                    .constrainAs(countryImage) {
//                        top.linkTo(leagueName.bottom, 10.dp)
//                        start.linkTo(leagueName.start)
//                    }
//            )
//
//            GlideImage(imageModel = logo,
//                contentDescription = "$lName logo",
//                modifier = Modifier
//                    .size(36.dp)
//                    .clip(CircleShape)
//                    .constrainAs(leagueImage) {
//                        top.linkTo(countryImage.top)
//                        start.linkTo(countryImage.end, 15.dp)
//                    }
//            )
//
//
//            Text(text = tName,
//                maxLines = 1,
//                fontSize = 24.sp,
//                modifier = Modifier
//                    .padding(top = 15.dp, bottom = 15.dp)
//                    .constrainAs(teamName) {
//                        end.linkTo(parent.end)
//                        start.linkTo(parent.start)
//                        bottom.linkTo(parent.bottom)
//                        top.linkTo(teamImage.bottom)
//                    }
//            )
//        }
//    }
//}
//
//@Composable
//fun TeamLineups(teamStats: TeamStatistics, modifier: Modifier) = TransferTrackerTheme {
//    Card(elevation = 1.dp,
//        shape =  RoundedCornerShape(10),
//        modifier = modifier
//            .fillMaxWidth()
//            .height(300.dp)) {
//
//        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
//
//            val (title,
//                divider,
//                formationList,
//                notProvided
//            ) = createRefs()
//
//            Text(text = "Lineups",
//                maxLines = 1,
//                fontSize = 24.sp,
//                modifier = Modifier
//                    .padding(start = 20.dp)
//                    .constrainAs(title) {
//                        top.linkTo(parent.top, 15.dp)
//                        start.linkTo(parent.start)
//                    }
//            )
//
//            Divider(color = Color.DarkGray,
//                thickness = 1.dp,
//                modifier = Modifier
//                    .padding(10.dp)
//                    .constrainAs(divider) {
//                        top.linkTo(title.bottom)
//                        start.linkTo(parent.start)
//                        end.linkTo(parent.end)
//                    }
//            )
//
//            val lineups = teamStats.lineups
//
//            if(lineups.isNullOrEmpty()){
//                Text(text = "Not Provided",
//                    modifier = Modifier
//                        .padding(top = 20.dp, bottom = 20.dp)
//                        .fillMaxWidth()
//                        .constrainAs(formationList) {
//                            height = Dimension.fillToConstraints
//                            top.linkTo(divider.bottom)
//                            bottom.linkTo(parent.bottom)
//                        }
//                )
//            }
//            else{
//                LazyColumn(modifier = Modifier
//                    .padding(top = 20.dp, bottom = 20.dp)
//                    .fillMaxWidth()
//                    .constrainAs(formationList) {
//                        height = Dimension.fillToConstraints
//                        top.linkTo(divider.bottom)
//                        bottom.linkTo(parent.bottom)
//                    }) {
//                    items(lineups) { data ->
//                        val lineup = data.formation ?: "Not Provided"
//                        val timesPlayed = data.played?.toString() ?: "Not Provided"
//                        DataItemWithExtra(stat = "Formation",
//                            value1 = "4-2-3-1",
//                            value2 = "32",
//                            modifier = Modifier
//                                .padding(top = 15.dp, start = 10.dp, end = 10.dp, bottom = 10.dp)
//                                .fillMaxWidth()
//                                .wrapContentHeight())
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//private fun TeamForm(teamStats: TeamStatistics, modifier: Modifier) = TransferTrackerTheme {
//    Card(elevation = 1.dp,
//        shape =  RoundedCornerShape(10),
//        modifier = modifier
//            .fillMaxWidth()
//            .wrapContentHeight()) {
//
//        ConstraintLayout(modifier = Modifier
//            .fillMaxWidth()
//            .wrapContentHeight()) {
//
//            val (title,
//                divider,
//                playerAppearances,
//                playerStarting,
//                playerMinutes,
//                playerInjured) = createRefs()
//
//            Text(text = "Medical",
//                maxLines = 1,
//                fontSize = 24.sp,
//                modifier = Modifier
//                    .padding(start = 20.dp)
//                    .constrainAs(title) {
//                        top.linkTo(parent.top, 15.dp)
//                        start.linkTo(parent.start)
//                    }
//            )
//
//            Divider(color = Color.DarkGray,
//                thickness = 1.dp,
//                modifier = Modifier
//                    .padding(10.dp)
//                    .constrainAs(divider) {
//                        top.linkTo(title.bottom)
//                        start.linkTo(parent.start)
//                        end.linkTo(parent.end)
//                    }
//            )
//
//            DataItem(stat = "Appearances",
//                value = statistic.games.appearences.toString(),
//                modifier = Modifier
//                    .padding(15.dp)
//                    .wrapContentSize()
//                    .constrainAs(playerAppearances) {
//                        top.linkTo(divider.bottom)
//                        start.linkTo(parent.start)
//                    })
//
//            DataItem(stat = "Starting",
//                value = statistic.games.lineups.toString(),
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(playerStarting) {
//                        start.linkTo(parent.start)
//                        top.linkTo(playerAppearances.bottom)
//                    })
//
//            DataItem(stat = "Minutes",
//                value = statistic.games.minutes.toString(),
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(playerMinutes) {
//                        start.linkTo(parent.start)
//                        top.linkTo(playerStarting.bottom)
//                    })
//
//            DataItem(stat = "Injured",
//                value = if(playerProfile.player.injured) "Yes" else "No",
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(playerInjured) {
//                        start.linkTo(parent.start)
//                        bottom.linkTo(parent.bottom)
//                        top.linkTo(playerMinutes.bottom)
//                    })
//        }
//    }
//}
//
//@Composable
//private fun TeamAttacking(teamStats: TeamStatistics, modifier: Modifier) = TransferTrackerTheme {
//    Card(elevation = 1.dp,
//        shape = RoundedCornerShape(10),
//        modifier = modifier
//            .fillMaxWidth()
//            .wrapContentHeight()
//    ) {
//
//        ConstraintLayout(modifier = Modifier
//            .fillMaxWidth()
//            .wrapContentHeight()
//        ) {
//
//            val (title,
//                divider,
//                playerShots,
//                playerShotsOnTarget,
//                playerGoals,
//                playerAssists,
//                playerDribbleAttempts,
//                playerDribbleSuccess,
//                playerKeyPasses,
//                playerPassAccuracy
//            ) = createRefs()
//
//                    .wrapContentSize()
//                    .constrainAs(playerShotsOnTarget) {
//                        top.linkTo(playerShots.bottom)
//                        start.linkTo(parent.start)
//                    })
//
//            DataItem(stat = "Goals",
//                value = statistic.goals.total.toString(),
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(playerGoals) {
//                        top.linkTo(playerShotsOnTarget.bottom)
//                        start.linkTo(parent.start)
//                    })
//
//            DataItem(stat = "Assists",
//                value = statistic.goals.assists.toString(),
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(playerAssists) {
//                        top.linkTo(playerGoals.bottom)
//                        start.linkTo(parent.start)
//                    })
//
//            DataItem(stat = "Dribble Attempts",
//                value = statistic.dribbles.attempts.toString(),
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(playerDribbleAttempts) {
//                        top.linkTo(playerAssists.bottom)
//                        start.linkTo(parent.start)
//                    })
//
//
//            DataItem(stat = "Successful Dribbles",
//                value = statistic.dribbles.success.toString(),
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(playerDribbleSuccess) {
//                        top.linkTo(playerDribbleAttempts.bottom)
//                        start.linkTo(parent.start)
//                    })
//
//            DataItem(stat = "Key Passes",
//                value = statistic.passes.key.toString(),
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(playerKeyPasses) {
//                        top.linkTo(playerDribbleSuccess.bottom)
//                        start.linkTo(parent.start)
//                    })
//
//            DataItem(stat = "Pass Accuracy",
//                value = statistic.passes.accuracy.toString(),
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(playerPassAccuracy) {
//                        start.linkTo(parent.start)
//                        bottom.linkTo(parent.bottom)
//                        top.linkTo(playerKeyPasses.bottom)
//                    })
//        }
//    }
//}
//
//@Composable
//private fun TeamDefense(teamStats: TeamStatistics, modifier: Modifier) = TransferTrackerTheme {
//    Card(elevation = 1.dp,
//        shape = RoundedCornerShape(10),
//        modifier = modifier
//            .fillMaxWidth()
//            .wrapContentHeight()
//    ) {
//
//        ConstraintLayout(modifier = Modifier
//            .fillMaxWidth()
//            .wrapContentHeight()
//        ) {
//
//            val (title,
//                divider,
//                playerTackles,
//                playerBlocks,
//                playerInterceptions,
//                playerDuels,
//                playerDuelsWon) = createRefs()
//
//            Text(text = "Defense",
//                maxLines = 1,
//                fontSize = 24.sp,
//                modifier = Modifier
//                    .padding(start = 20.dp)
//                    .constrainAs(title) {
//                        top.linkTo(parent.top, 15.dp)
//                        start.linkTo(parent.start)
//                    }
//            )
//
//            Divider(color = Color.DarkGray,
//                thickness = 1.dp,
//                modifier = Modifier
//                    .padding(10.dp)
//                    .constrainAs(divider) {
//                        top.linkTo(title.bottom)
//                        start.linkTo(parent.start)
//                        end.linkTo(parent.end)
//                    }
//            )
//
//            DataItem(stat = "Tackles",
//                value = statistic.tackles.total.toString(),
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(playerTackles) {
//                        top.linkTo(divider.bottom)
//                        start.linkTo(parent.start)
//                    })
//
//            DataItem(stat = "Blocks",
//                value = statistic.tackles.blocks.toString(),
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(playerBlocks) {
//                        top.linkTo(playerTackles.bottom)
//                        start.linkTo(parent.start)
//                    })
//
//            DataItem(stat = "Interceptions",
//                value = statistic.tackles.interceptions.toString(),
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(playerInterceptions) {
//                        top.linkTo(playerBlocks.bottom)
//                        start.linkTo(parent.start)
//                    })
//
//            DataItem(stat = "Duels",
//                value = statistic.duels.total.toString(),
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(playerDuels) {
//                        top.linkTo(playerInterceptions.bottom)
//                        start.linkTo(parent.start)
//                    })
//
//            DataItem(stat = "Duels Won",
//                value = statistic.duels.won.toString(),
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(playerDuelsWon) {
//                        start.linkTo(parent.start)
//                        bottom.linkTo(parent.bottom)
//                        top.linkTo(playerDuels.bottom)
//                    })
//        }
//    }
//}
//
//@Composable
//private fun TeamDiscipline(teamStats: TeamStatistics, modifier: Modifier) = TransferTrackerTheme {
//    Card(elevation = 1.dp,
//        shape = RoundedCornerShape(10),
//        modifier = modifier
//            .fillMaxWidth()
//            .wrapContentHeight()
//    ) {
//
//        ConstraintLayout(modifier = Modifier
//            .fillMaxWidth()
//            .wrapContentHeight()
//        ) {
//
//            val (title,
//                divider,
//                playerFoulsCommitted,
//                playerFoulsDrawn,
//                playerPenaltiesCommitted,
//                playerPenaltiesDrawn,
//                playerReds,
//                playerYellows,
//                playerYellowRed,
//            ) = createRefs()
//
//            Text(text = "Discipline",
//                maxLines = 1,
//                fontSize = 24.sp,
//                modifier = Modifier
//                    .padding(start = 20.dp)
//                    .constrainAs(title) {
//                        top.linkTo(parent.top, 15.dp)
//                        start.linkTo(parent.start)
//                    }
//            )
//
//            Divider(color = Color.DarkGray,
//                thickness = 1.dp,
//                modifier = Modifier
//                    .padding(10.dp)
//                    .constrainAs(divider) {
//                        top.linkTo(title.bottom)
//                        start.linkTo(parent.start)
//                        end.linkTo(parent.end)
//                    }
//            )
//
//            DataItem(stat = "Fouls Committed",
//                value = statistic.fouls.committed.toString(),
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(playerFoulsCommitted) {
//                        top.linkTo(divider.bottom)
//                        start.linkTo(parent.start)
//                    })
//
//            DataItem(stat = "Fouls Drawn",
//                value = statistic.fouls.drawn.toString(),
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(playerFoulsDrawn) {
//                        top.linkTo(playerFoulsCommitted.bottom)
//                        start.linkTo(parent.start)
//                    })
//
//            DataItem(stat = "Penalties Committed",
//                value = statistic.penalty.commited.toString(),
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(playerPenaltiesCommitted) {
//                        top.linkTo(playerFoulsDrawn.bottom)
//                        start.linkTo(parent.start)
//                    })
//
//            DataItem(stat = "Penalties Won",
//                value = statistic.penalty.won.toString(),
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(playerPenaltiesDrawn) {
//                        top.linkTo(playerPenaltiesCommitted.bottom)
//                        start.linkTo(parent.start)
//                    })
//
//            DataItem(stat = "Reds",
//                value = statistic.cards.red.toString(),
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(playerReds) {
//                        top.linkTo(playerPenaltiesDrawn.bottom)
//                        start.linkTo(parent.start)
//                    })
//
//            DataItem(stat = "Yellows",
//                value = statistic.cards.yellow.toString(),
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(playerYellows) {
//                        top.linkTo(playerReds.bottom)
//                        start.linkTo(parent.start)
//                    })
//
//            DataItem(stat = "Yellow to Reds",
//                value = statistic.cards.yellowred.toString(),
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(playerYellowRed) {
//                        start.linkTo(parent.start)
//                        bottom.linkTo(parent.bottom)
//                        top.linkTo(playerYellows.bottom)
//                    })
//        }
//    }
//}
//
///*
//Preview
// */
//@Composable
//private fun TeamInfoPreview(modifier: Modifier) = TransferTrackerTheme {
//    Card(elevation = 4.dp,
//        shape = RoundedCornerShape(2),
//        modifier = modifier
//            .fillMaxWidth()
//            .height(200.dp)) {
//
//        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
//
//            val (teamImage,
//                leagueName,
//                countryImage,
//                leagueImage,
//                teamName) = createRefs()
//
//            Image(painter = painterResource(id = R.drawable.ic_tottenham),
//                contentDescription = "",
//                contentScale = ContentScale.Inside,
//                modifier = Modifier
//                    .size(128.dp)
//                    .clip(CircleShape)
//                    .padding(10.dp)
//                    .border(2.dp, Color.White, CircleShape)
//                    .constrainAs(teamImage) {
//                        top.linkTo(parent.top)
//                        start.linkTo(parent.start)
//                    }
//            )
//
//            Image(painter = painterResource(id = R.drawable.ic_croatia),
//                contentDescription = "",
//                modifier = Modifier
//                    .size(36.dp)
//                    .clip(CircleShape)
//                    .constrainAs(countryImage) {
//                        top.linkTo(teamImage.top, 25.dp)
//                        start.linkTo(teamImage.end, 25.dp)
//                    }
//            )
//
//            Image(painter = painterResource(id = R.drawable.ic_premier_league),
//                contentDescription = "",
//                modifier = Modifier
//                    .size(36.dp)
//                    .clip(CircleShape)
//                    .constrainAs(leagueImage) {
//                        top.linkTo(countryImage.top)
//                        start.linkTo(countryImage.end, 15.dp)
//                    }
//            )
//
//            Text(text = "Premier League",
//                maxLines = 1,
//                fontSize = 18.sp,
//                modifier = Modifier
//                    .constrainAs(leagueName) {
//                        top.linkTo(countryImage.bottom, 10.dp)
//                        start.linkTo(countryImage.start)
//                    }
//            )
//
//            Text(text = "Tottenham",
//                maxLines = 1,
//                fontSize = 24.sp,
//                modifier = Modifier
//                    .padding(top = 15.dp, bottom = 15.dp)
//                    .constrainAs(teamName) {
//                        end.linkTo(parent.end)
//                        start.linkTo(parent.start)
//                        bottom.linkTo(parent.bottom)
//                        top.linkTo(teamImage.bottom)
//                    }
//            )
//        }
//    }
//}
//
//@Composable
//fun TeamLineupsPreview(modifier: Modifier) = TransferTrackerTheme {
//    Card(elevation = 1.dp,
//        shape =  RoundedCornerShape(10),
//        modifier = modifier
//            .fillMaxWidth()
//            .height(300.dp)) {
//
//        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
//
//            val (title,
//                divider,
//                formationList,
//            ) = createRefs()
//
//            Text(text = "Lineups",
//                maxLines = 1,
//                fontSize = 24.sp,
//                modifier = Modifier
//                    .padding(start = 20.dp)
//                    .constrainAs(title) {
//                        top.linkTo(parent.top, 15.dp)
//                        start.linkTo(parent.start)
//                    }
//            )
//
//            Divider(color = Color.DarkGray,
//                thickness = 1.dp,
//                modifier = Modifier
//                    .padding(10.dp)
//                    .constrainAs(divider) {
//                        top.linkTo(title.bottom)
//                        start.linkTo(parent.start)
//                        end.linkTo(parent.end)
//                    }
//            )
//
//            LazyColumn(modifier = Modifier
//                .padding(top = 20.dp, bottom = 20.dp)
//                .fillMaxWidth()
//                .constrainAs(formationList) {
//                    height = Dimension.fillToConstraints
//                    top.linkTo(divider.bottom)
//                    bottom.linkTo(parent.bottom)
//                }) {
//                items(5) {
//                    DataItemWithExtra(stat = "Formation",
//                        value1 = "4-2-3-1",
//                        value2 = "32",
//                        modifier = Modifier
//                            .padding(top = 15.dp, start = 10.dp, end = 10.dp, bottom = 10.dp)
//                            .fillMaxWidth()
//                            .wrapContentHeight())
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun TeamFormPreview(modifier: Modifier) = TransferTrackerTheme {
//    Card(elevation = 1.dp,
//        shape =  RoundedCornerShape(10),
//        modifier = modifier
//            .fillMaxWidth()
//            .wrapContentHeight()) {
//
//        ConstraintLayout(modifier = Modifier
//            .fillMaxWidth()
//            .wrapContentHeight()) {
//
//            val (title,
//                divider,
//                form,
//                homeGamesPlayed,
//                homeGamesWon,
//                homeGamesLost,
//                awayGamesPlayed,
//                awayGamesWon,
//                awayGamesLost,
//                totalGamesPlayed,
//                totalGamesWon,
//                totalGamesLost
//            ) = createRefs()
//
//            Text(text = "Fixtures",
//                maxLines = 1,
//                fontSize = 24.sp,
//                modifier = Modifier
//                    .padding(start = 20.dp)
//                    .constrainAs(title) {
//                        top.linkTo(parent.top, 15.dp)
//                        start.linkTo(parent.start)
//                    }
//            )
//
//            Divider(color = Color.DarkGray,
//                thickness = 1.dp,
//                modifier = Modifier
//                    .padding(10.dp)
//                    .constrainAs(divider) {
//                        top.linkTo(title.bottom)
//                        start.linkTo(parent.start)
//                        end.linkTo(parent.end)
//                    }
//            )
//
//            DataItem(stat = "Form",
//                value = "W L W W W W L W W W",
//                modifier = Modifier
//                    .padding(top = 15.dp, start = 10.dp, end = 10.dp, bottom = 10.dp)
//                    .wrapContentSize()
//                    .constrainAs(form) {
//                        top.linkTo(divider.bottom)
//                        start.linkTo(parent.start)
//                    })
//
//            DataItem(stat = "Home Games",
//                value = "26",
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(homeGamesPlayed) {
//                        start.linkTo(parent.start)
//                        top.linkTo(form.bottom)
//                    })
//
//            DataItem(stat = "Home Games Won",
//                value = "2355",
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(homeGamesWon) {
//                        start.linkTo(parent.start)
//                        top.linkTo(homeGamesPlayed.bottom)
//                    })
//
//            DataItem(stat = "Home Games Lost",
//                value = "2355",
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(homeGamesLost) {
//                        start.linkTo(parent.start)
//                        top.linkTo(homeGamesWon.bottom)
//                    })
//
//            DataItem(stat = "Away Games",
//                value = "2355",
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(awayGamesPlayed) {
//                        start.linkTo(parent.start)
//                        top.linkTo(homeGamesLost.bottom)
//                    })
//
//            DataItem(stat = "Away Games Won",
//                value = "2355",
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(awayGamesWon) {
//                        start.linkTo(parent.start)
//                        top.linkTo(awayGamesPlayed.bottom)
//                    })
//
//            DataItem(stat = "Away Games Lost",
//                value = "2355",
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(awayGamesLost) {
//                        start.linkTo(parent.start)
//                        top.linkTo(awayGamesWon.bottom)
//                    })
//
//            DataItem(stat = "Total Games",
//                value = "2355",
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(totalGamesPlayed) {
//                        start.linkTo(parent.start)
//                        top.linkTo(awayGamesLost.bottom)
//                    })
//
//            DataItem(stat = "Total Games Won",
//                value = "2355",
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(totalGamesWon) {
//                        start.linkTo(parent.start)
//                        top.linkTo(totalGamesPlayed.bottom)
//                    })
//
//            DataItem(stat = "Total Games Lost",
//                value = "No",
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(totalGamesLost) {
//                        start.linkTo(parent.start)
//                        bottom.linkTo(parent.bottom)
//                        top.linkTo(totalGamesWon.bottom)
//                    })
//        }
//    }
//}
//
//@Composable
//fun TeamAttackingPreview(modifier: Modifier) = TransferTrackerTheme {
//    Card(elevation = 1.dp,
//        shape = RoundedCornerShape(10),
//        modifier = modifier
//            .fillMaxWidth()
//            .wrapContentHeight()
//    ) {
//
//        ConstraintLayout(modifier = Modifier
//            .fillMaxWidth()
//            .wrapContentHeight()
//        ) {
//
//            val (title,
//                divider,
//                totalGoalsItem,
//                homeGoalsItem,
//                awayGoalsItem,
//                minute015,
//                minute1630,
//                minute3145,
//                minute4660,
//                minute6175,
//                minute7690,
//                extraTime,
//            ) = createRefs()
//
//            Text(text = "Attacking",
//                maxLines = 1,
//                fontSize = 24.sp,
//                modifier = Modifier
//                    .padding(start = 20.dp)
//                    .constrainAs(title) {
//                        top.linkTo(parent.top, 15.dp)
//                        start.linkTo(parent.start)
//                    }
//            )
//
//            Divider(color = Color.DarkGray,
//                thickness = 1.dp,
//                modifier = Modifier
//                    .padding(10.dp)
//                    .constrainAs(divider) {
//                        top.linkTo(title.bottom)
//                        start.linkTo(parent.start)
//                        end.linkTo(parent.end)
//                    }
//            )
//
//            DataItemWithExtra(stat = "Goals",
//                value1 = "2",
//                value2 = "2",
//                modifier = Modifier
//                    .padding(top = 15.dp, start = 10.dp, end = 10.dp, bottom = 10.dp)
//                    .wrapContentSize()
//                    .constrainAs(totalGoalsItem) {
//                        top.linkTo(divider.bottom)
//                        start.linkTo(parent.start)
//                    })
//
//            DataItemWithExtra(stat = "Home Goals",
//                value1 = "2",
//                value2 = "2",
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(homeGoalsItem) {
//                        top.linkTo(totalGoalsItem.bottom)
//                        start.linkTo(parent.start)
//                    })
//
//            DataItemWithExtra(stat = "Away Goals",
//                value1 = "2",
//                value2 = "2",
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(awayGoalsItem) {
//                        top.linkTo(homeGoalsItem.bottom)
//                        start.linkTo(parent.start)
//                    })
//
//            DataItemWithExtra(stat = "Minute: 0 - 15",
//                value1 = "2",
//                value2 = "2",
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(minute015) {
//                        top.linkTo(awayGoalsItem.bottom)
//                        start.linkTo(parent.start)
//                    })
//
//            DataItemWithExtra(stat = "Minute: 16 - 30",
//                value1 = "2",
//                value2 = "2",
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(minute1630) {
//                        top.linkTo(minute015.bottom)
//                        start.linkTo(parent.start)
//                    })
//
//
//            DataItemWithExtra(stat = "Minute: 31 - 45",
//                value1 = "2",
//                value2 = "2",
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(minute3145) {
//                        top.linkTo(minute1630.bottom)
//                        start.linkTo(parent.start)
//                    })
//
//            DataItemWithExtra(stat = "Minute: 46 - 60",
//                value1 = "2",
//                value2 = "2",
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(minute4660) {
//                        top.linkTo(minute3145.bottom)
//                        start.linkTo(parent.start)
//                    })
//
//            DataItemWithExtra(stat = "Minute: 61 - 75",
//                value1 = "2",
//                value2 = "2",
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(minute6175) {
//                        top.linkTo(minute4660.bottom)
//                        start.linkTo(parent.start)
//                    })
//
//            DataItemWithExtra(stat = "Minute: 76 - 90",
//                value1 = "2",
//                value2 = "2",
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(minute7690) {
//                        top.linkTo(minute6175.bottom)
//                        start.linkTo(parent.start)
//                    })
//
//            DataItemWithExtra(stat = "Extra Time",
//                value1 = "2",
//                value2 = "2",
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(extraTime) {
//                        start.linkTo(parent.start)
//                        bottom.linkTo(parent.bottom)
//                        top.linkTo(minute7690.bottom)
//                    })
//        }
//    }
//}
//
//@Composable
//fun TeamDefensePreview(modifier: Modifier) = TransferTrackerTheme {
//    Card(elevation = 1.dp,
//        shape = RoundedCornerShape(10),
//        modifier = modifier
//            .fillMaxWidth()
//            .wrapContentHeight()
//    ) {
//
//        ConstraintLayout(modifier = Modifier
//            .fillMaxWidth()
//            .wrapContentHeight()
//        ) {
//
//
//            val (title,
//                divider,
//                totalGoalsItem,
//                homeGoalsItem,
//                awayGoalsItem,
//                minute015,
//                minute1630,
//                minute3145,
//                minute4660,
//                minute6175,
//                minute7690,
//                extraTime,
//            ) = createRefs()
//
//            Text(text = "Defense",
//                maxLines = 1,
//                fontSize = 24.sp,
//                modifier = Modifier
//                    .padding(start = 20.dp)
//                    .constrainAs(title) {
//                        top.linkTo(parent.top, 15.dp)
//                        start.linkTo(parent.start)
//                    }
//            )
//
//            Divider(color = Color.DarkGray,
//                thickness = 1.dp,
//                modifier = Modifier
//                    .padding(10.dp)
//                    .constrainAs(divider) {
//                        top.linkTo(title.bottom)
//                        start.linkTo(parent.start)
//                        end.linkTo(parent.end)
//                    }
//            )
//
//            DataItemWithExtra(stat = "Goals Against",
//                value1 = "2",
//                value2 = "2",
//                modifier = Modifier
//                    .padding(top = 15.dp, start = 10.dp, end = 10.dp, bottom = 10.dp)
//                    .wrapContentSize()
//                    .constrainAs(totalGoalsItem) {
//                        top.linkTo(divider.bottom)
//                        start.linkTo(parent.start)
//                    })
//
//            DataItemWithExtra(stat = "Home Goals Against",
//                value1 = "2",
//                value2 = "2",
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(homeGoalsItem) {
//                        top.linkTo(totalGoalsItem.bottom)
//                        start.linkTo(parent.start)
//                    })
//
//            DataItemWithExtra(stat = "Away Goals Against",
//                value1 = "2",
//                value2 = "2",
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(awayGoalsItem) {
//                        top.linkTo(homeGoalsItem.bottom)
//                        start.linkTo(parent.start)
//                    })
//
//            DataItemWithExtra(stat = "Minute: 0 - 15",
//                value1 = "2",
//                value2 = "2",
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(minute015) {
//                        top.linkTo(awayGoalsItem.bottom)
//                        start.linkTo(parent.start)
//                    })
//
//            DataItemWithExtra(stat = "Minute: 16 - 30",
//                value1 = "2",
//                value2 = "2",
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(minute1630) {
//                        top.linkTo(minute015.bottom)
//                        start.linkTo(parent.start)
//                    })
//
//
//            DataItemWithExtra(stat = "Minute: 31 - 45",
//                value1 = "2",
//                value2 = "2",
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(minute3145) {
//                        top.linkTo(minute1630.bottom)
//                        start.linkTo(parent.start)
//                    })
//
//            DataItemWithExtra(stat = "Minute: 46 - 60",
//                value1 = "2",
//                value2 = "2",
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(minute4660) {
//                        top.linkTo(minute3145.bottom)
//                        start.linkTo(parent.start)
//                    })
//
//            DataItemWithExtra(stat = "Minute: 61 - 75",
//                value1 = "2",
//                value2 = "2",
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(minute6175) {
//                        top.linkTo(minute4660.bottom)
//                        start.linkTo(parent.start)
//                    })
//
//            DataItemWithExtra(stat = "Minute: 76 - 90",
//                value1 = "2",
//                value2 = "2",
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(minute7690) {
//                        top.linkTo(minute6175.bottom)
//                        start.linkTo(parent.start)
//                    })
//
//            DataItemWithExtra(stat = "Extra Time",
//                value1 = "2",
//                value2 = "2",
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(extraTime) {
//                        start.linkTo(parent.start)
//                        bottom.linkTo(parent.bottom)
//                        top.linkTo(minute7690.bottom)
//                    })
//        }
//    }
//}
//
//@Composable
//fun TeamDisciplinePreview(modifier: Modifier) = TransferTrackerTheme {
//    Card(elevation = 1.dp,
//        shape = RoundedCornerShape(10),
//        modifier = modifier
//            .fillMaxWidth()
//            .wrapContentHeight()
//    ) {
//
//        ConstraintLayout(modifier = Modifier
//            .fillMaxWidth()
//            .wrapContentHeight()
//        ) {
//
//
//            val (title,
//                divider,
//                cleanSheets,
//                minute015,
//                minute1630,
//                minute3145,
//                minute4660,
//                minute6175,
//                minute7690,
//                extraTime,
//            ) = createRefs()
//
//            Text(text = "Discipline",
//                maxLines = 1,
//                fontSize = 24.sp,
//                modifier = Modifier
//                    .padding(start = 20.dp)
//                    .constrainAs(title) {
//                        top.linkTo(parent.top, 15.dp)
//                        start.linkTo(parent.start)
//                    }
//            )
//
//            Divider(color = Color.DarkGray,
//                thickness = 1.dp,
//                modifier = Modifier
//                    .padding(10.dp)
//                    .constrainAs(divider) {
//                        top.linkTo(title.bottom)
//                        start.linkTo(parent.start)
//                        end.linkTo(parent.end)
//                    }
//            )
//
//            DataItem(stat = "Clean Sheets",
//                value = "2",
//                modifier = Modifier
//                    .padding(top = 15.dp, start = 10.dp, end = 10.dp, bottom = 10.dp)
//                    .wrapContentSize()
//                    .constrainAs(cleanSheets) {
//                        top.linkTo(divider.bottom)
//                        start.linkTo(parent.start)
//                    })
//
//            DataItemWithExtra(stat = "Minute: 0 - 15",
//                value1 = "2",
//                value2 = "2",
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(minute015) {
//                        top.linkTo(cleanSheets.bottom)
//                        start.linkTo(parent.start)
//                    })
//
//            DataItemWithExtra(stat = "Minute: 16 - 30",
//                value1 = "2",
//                value2 = "2",
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(minute1630) {
//                        top.linkTo(minute015.bottom)
//                        start.linkTo(parent.start)
//                    })
//
//
//            DataItemWithExtra(stat = "Minute: 31 - 45",
//                value1 = "2",
//                value2 = "2",
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(minute3145) {
//                        top.linkTo(minute1630.bottom)
//                        start.linkTo(parent.start)
//                    })
//
//            DataItemWithExtra(stat = "Minute: 46 - 60",
//                value1 = "2",
//                value2 = "2",
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(minute4660) {
//                        top.linkTo(minute3145.bottom)
//                        start.linkTo(parent.start)
//                    })
//
//            DataItemWithExtra(stat = "Minute: 61 - 75",
//                value1 = "2",
//                value2 = "2",
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(minute6175) {
//                        top.linkTo(minute4660.bottom)
//                        start.linkTo(parent.start)
//                    })
//
//            DataItemWithExtra(stat = "Minute: 76 - 90",
//                value1 = "2",
//                value2 = "2",
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(minute7690) {
//                        top.linkTo(minute6175.bottom)
//                        start.linkTo(parent.start)
//                    })
//
//            DataItemWithExtra(stat = "Extra Time",
//                value1 = "2",
//                value2 = "2",
//                modifier = Modifier
//                    .padding(10.dp)
//                    .wrapContentSize()
//                    .constrainAs(extraTime) {
//                        start.linkTo(parent.start)
//                        bottom.linkTo(parent.bottom)
//                        top.linkTo(minute7690.bottom)
//                    })
//        }
//    }
//}
