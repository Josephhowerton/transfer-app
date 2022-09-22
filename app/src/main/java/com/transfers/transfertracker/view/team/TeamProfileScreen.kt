package com.transfers.transfertracker.view.team

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.skydoves.landscapist.glide.GlideImage
import com.transfers.transfertracker.R
import com.transfers.transfertracker.view.component.TransferTrackerAlertDialog
import com.transfers.transfertracker.view.component.TransferTopAppBar
import com.transfers.transfertracker.view.theme.LightGrey
import com.transfers.transfertracker.view.theme.TransferTrackerTheme

@Preview(showBackground = true)
@Composable
private fun TeamProfilePreview() = TransferTrackerTheme {

}

@Composable
fun TeamProfile(teamId: String?, leagueId: String?, onErrorAction: () -> Unit) =
    TransferTrackerTheme {
        Scaffold(topBar = {

            TransferTopAppBar("Team Profile", false, onErrorAction)

        }) {
            if (teamId == null || leagueId == null) {
                TransferTrackerAlertDialog(
                    title = R.string.title_generic_error,
                    message = R.string.message_generic_error,
                    buttonTitle = R.string.title_dismiss_button,
                    showErrorDialog = true,
                    onClick = onErrorAction
                )
            } else {
                TeamProfileScreen(
                    teamId = teamId,
                    leagueId = leagueId,
                    onErrorAction = onErrorAction
                )
            }
        }
    }

@Composable
private fun TeamProfileScreen(teamId: String, leagueId: String, onErrorAction: () -> Unit) =
    TransferTrackerTheme {

        val viewModel: TeamProfileViewModel = hiltViewModel()

        DisposableEffect(key1 = viewModel) {
            onDispose { viewModel.onDestroy() }
        }

        viewModel.fetchTeamStatistics(leagueId, teamId)

        ConstraintLayout(
            Modifier.verticalScroll(
                state = rememberScrollState(),
            )
        ) {

            val (
                profile,
                lineups,
                form,
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

            TeamInfo(viewModel, Modifier.constrainAs(profile) {
                top.linkTo(parent.top, 10.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })

            TeamLineups(viewModel, Modifier.constrainAs(lineups) {
                top.linkTo(profile.bottom, 15.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })

            TeamForm(viewModel, Modifier.constrainAs(form) {
                top.linkTo(lineups.bottom, 15.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })

            TeamAttacking(viewModel, Modifier.constrainAs(attacking) {
                top.linkTo(form.bottom, 15.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })

            TeamDefense(viewModel, Modifier.constrainAs(defending) {
                top.linkTo(attacking.bottom, 15.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })

            TeamDiscipline(viewModel,
                Modifier
                    .padding(bottom = 10.dp)
                    .constrainAs(discipline) {
                        top.linkTo(defending.bottom, 15.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })
        }
    }


/*
Components
 */
@Composable
private fun TeamInfo(viewModel: TeamProfileViewModel, modifier: Modifier) = TransferTrackerTheme {
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(2),
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {

        ConstraintLayout(modifier = Modifier.fillMaxSize()) {

            val (teamImageConstraint,
                leagueNameConstraint,
                countryImageConstraint,
                leagueImageConstraint,
                teamNameConstraint) = createRefs()

            val teamLogo = remember { viewModel.teamLogo }
            GlideImage(imageModel = teamLogo.value,
                contentDescription = "",
                modifier = Modifier
                    .size(128.dp)
                    .padding(10.dp)
                    .constrainAs(teamImageConstraint) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    }
            )

            val countryFlag = remember { viewModel.leagueCountryFlag }

            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .decoderFactory(SvgDecoder.Factory())
                    .data(countryFlag.value)
                    .build()
            )

            Image(painter =  painter,
                contentDescription = "",
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .constrainAs(countryImageConstraint) {
                        top.linkTo(teamImageConstraint.top, 25.dp)
                        start.linkTo(teamImageConstraint.end, 25.dp)
                    }
            )

            val leagueLogo = remember { viewModel.leagueLogo }
            GlideImage(imageModel = leagueLogo.value,
                contentDescription = "",
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .constrainAs(leagueNameConstraint) {
                        top.linkTo(countryImageConstraint.top)
                        start.linkTo(countryImageConstraint.end, 15.dp)
                    }
            )


            val leagueName = remember { viewModel.leagueName }
            Text(text = leagueName.value,
                maxLines = 1,
                fontSize = 18.sp,
                modifier = Modifier
                    .constrainAs(leagueImageConstraint) {
                        top.linkTo(countryImageConstraint.bottom, 10.dp)
                        start.linkTo(countryImageConstraint.start)
                    }
            )

            val teamName = remember { viewModel.teamName }
            Text(text = teamName.value,
                maxLines = 1,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(top = 15.dp, bottom = 15.dp)
                    .constrainAs(teamNameConstraint) {
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                        top.linkTo(teamImageConstraint.bottom)
                    }
            )
        }
    }
}

@Composable
fun TeamLineups(viewModel: TeamProfileViewModel, modifier: Modifier) = TransferTrackerTheme {
    Card(
        elevation = 1.dp,
        shape = RoundedCornerShape(10),
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {

        ConstraintLayout(modifier = Modifier.fillMaxSize()) {

            val (
                titleConstraints,
                dividerConstraints,
                formationListConstraints,
            ) = createRefs()

            Text(text = "Lineups",
                maxLines = 1,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(start = 20.dp)
                    .constrainAs(titleConstraints) {
                        top.linkTo(parent.top, 15.dp)
                        start.linkTo(parent.start)
                    }
            )

            Divider(color = Color.DarkGray,
                thickness = 1.dp,
                modifier = Modifier
                    .padding(10.dp)
                    .constrainAs(dividerConstraints) {
                        top.linkTo(titleConstraints.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            val lineups = viewModel.teamLineups

            if (lineups.isEmpty()) {
                Text(text = "Unavailable",
                    fontSize = 24.sp,
                    modifier = Modifier
                        .padding(top = 20.dp, bottom = 20.dp)
                        .fillMaxWidth()
                        .constrainAs(formationListConstraints) {
                            height = Dimension.fillToConstraints
                            top.linkTo(dividerConstraints.bottom)
                            bottom.linkTo(parent.bottom)
                        }
                )
            } else {
                LazyColumn(modifier = Modifier
                    .padding(top = 20.dp, bottom = 20.dp)
                    .fillMaxWidth()
                    .constrainAs(formationListConstraints) {
                        height = Dimension.fillToConstraints
                        top.linkTo(dividerConstraints.bottom)
                        bottom.linkTo(parent.bottom)
                    }) {
                    items(lineups) { lineup ->

                        if (lineup.formation != null && lineup.played != null) {
                            DataItemWithExtra(
                                stat = "Formation",
                                value1 = lineup.formation,
                                value2 = lineup.played.toString(),
                                modifier = Modifier
                                    .padding(
                                        top = 15.dp,
                                        start = 10.dp,
                                        end = 10.dp,
                                        bottom = 10.dp
                                    )
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TeamForm(viewModel: TeamProfileViewModel, modifier: Modifier) = TransferTrackerTheme {
    Card(
        elevation = 1.dp,
        shape = RoundedCornerShape(10),
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {

            val (titleConstraint,
                dividerConstraint,
                formConstraint,
                homeGamesPlayedConstraint,
                homeGamesWonConstraint,
                homeGamesLostConstraint,
                awayGamesPlayedConstraint,
                awayGamesWonConstraint,
                awayGamesLostConstraint,
                totalGamesPlayedConstraint,
                totalGamesWonConstraint,
                totalGamesLostConstraint
            ) = createRefs()

            Text(text = "Fixtures",
                maxLines = 1,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(start = 20.dp)
                    .constrainAs(titleConstraint) {
                        top.linkTo(parent.top, 15.dp)
                        start.linkTo(parent.start)
                    }
            )

            Divider(color = Color.DarkGray,
                thickness = 1.dp,
                modifier = Modifier
                    .padding(10.dp)
                    .constrainAs(dividerConstraint) {
                        top.linkTo(titleConstraint.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            val teamForm = remember { viewModel.teamForm }
            DataItem(stat = "Form",
                value = teamForm.value,
                modifier = Modifier
                    .padding(top = 15.dp, start = 10.dp, end = 10.dp, bottom = 10.dp)
                    .wrapContentSize()
                    .constrainAs(formConstraint) {
                        top.linkTo(dividerConstraint.bottom)
                        start.linkTo(parent.start)
                    })

            val teamHomeGamesPlayed = remember { viewModel.teamHomeGamesPlayed }
            DataItem(stat = "Home Games",
                value = teamHomeGamesPlayed.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(homeGamesPlayedConstraint) {
                        start.linkTo(parent.start)
                        top.linkTo(formConstraint.bottom)
                    })

            val teamHomeGamesWon = remember { viewModel.teamHomeGamesWon }
            DataItem(stat = "Home Games Won",
                value = teamHomeGamesWon.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(homeGamesWonConstraint) {
                        start.linkTo(parent.start)
                        top.linkTo(homeGamesPlayedConstraint.bottom)
                    })

            val teamHomeGamesLost = remember { viewModel.teamHomeGamesLost }
            DataItem(stat = "Home Games Lost",
                value = teamHomeGamesLost.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(homeGamesLostConstraint) {
                        start.linkTo(parent.start)
                        top.linkTo(homeGamesWonConstraint.bottom)
                    })

            val teamAwayGamesPlayed = remember { viewModel.teamAwayGamesPlayed }
            DataItem(stat = "Away Games",
                value = teamAwayGamesPlayed.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(awayGamesPlayedConstraint) {
                        start.linkTo(parent.start)
                        top.linkTo(homeGamesLostConstraint.bottom)
                    })

            val teamAwayGamesWon = remember { viewModel.teamAwayGamesWon }
            DataItem(stat = "Away Games Won",
                value = teamAwayGamesWon.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(awayGamesWonConstraint) {
                        start.linkTo(parent.start)
                        top.linkTo(awayGamesPlayedConstraint.bottom)
                    })

            val teamAwayGamesLost = remember { viewModel.teamAwayGamesLost }
            DataItem(stat = "Away Games Lost",
                value = teamAwayGamesLost.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(awayGamesLostConstraint) {
                        start.linkTo(parent.start)
                        top.linkTo(awayGamesWonConstraint.bottom)
                    })

            val teamTotalGamesPlayed = remember { viewModel.teamTotalGamesPlayed }
            DataItem(stat = "Total Games",
                value = teamTotalGamesPlayed.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(totalGamesPlayedConstraint) {
                        start.linkTo(parent.start)
                        top.linkTo(awayGamesLostConstraint.bottom)
                    })

            val teamTotalGamesWon = remember { viewModel.teamTotalGamesWon }
            DataItem(stat = "Total Games Won",
                value = teamTotalGamesWon.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(totalGamesWonConstraint) {
                        start.linkTo(parent.start)
                        top.linkTo(totalGamesPlayedConstraint.bottom)
                    })

            val teamTotalGamesLost = remember { viewModel.teamTotalGamesLost }
            DataItem(stat = "Total Games Lost",
                value = teamTotalGamesLost.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(totalGamesLostConstraint) {
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                        top.linkTo(totalGamesWonConstraint.bottom)
                    })
        }
    }
}

@Composable
fun TeamAttacking(viewModel: TeamProfileViewModel, modifier: Modifier) = TransferTrackerTheme {
    Card(
        elevation = 1.dp,
        shape = RoundedCornerShape(10),
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {

            val (
                title,
                divider,
                totalGoalsItemConstraint,
                homeGoalsItemConstraint,
                awayGoalsItemConstraint,
                minute015Constraint,
                minute1630Constraint,
                minute3145Constraint,
                minute4660Constraint,
                minute6175Constraint,
                minute7690Constraint,
                extraTimeConstraint,
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

            val teamTotalGoalsItem = remember { viewModel.teamTotalGoalsItemAttacking }
            val teamTotalGoalsItemAverage =
                remember { viewModel.teamTotalGoalsItemAverageAttacking }
            DataItemWithExtra(stat = "Goals",
                value1 = teamTotalGoalsItem.value,
                value2 = teamTotalGoalsItemAverage.value,
                modifier = Modifier
                    .padding(top = 15.dp, start = 10.dp, end = 10.dp, bottom = 10.dp)
                    .wrapContentSize()
                    .constrainAs(totalGoalsItemConstraint) {
                        top.linkTo(divider.bottom)
                        start.linkTo(parent.start)
                    })

            val teamHomeGoalsItem = remember { viewModel.teamHomeGoalsItemAttacking }
            val teamHomeGoalsItemAverage = remember { viewModel.teamHomeGoalsItemAverageAttacking }
            DataItemWithExtra(stat = "Home Goals",
                value1 = teamHomeGoalsItem.value,
                value2 = teamHomeGoalsItemAverage.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(homeGoalsItemConstraint) {
                        top.linkTo(totalGoalsItemConstraint.bottom)
                        start.linkTo(parent.start)
                    })

            val teamAwayGoalsItem = remember { viewModel.teamAwayGoalsItemAttacking }
            val teamAwayGoalsItemAverage = remember { viewModel.teamAwayGoalsItemAverageAttacking }
            DataItemWithExtra(stat = "Away Goals",
                value1 = teamAwayGoalsItem.value,
                value2 = teamAwayGoalsItemAverage.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(awayGoalsItemConstraint) {
                        top.linkTo(homeGoalsItemConstraint.bottom)
                        start.linkTo(parent.start)
                    })

            val teamMinute015 = remember { viewModel.teamMinute015Attacking }
            val teamMinute015Percent = remember { viewModel.teamMinute015PercentAttacking }
            DataItemWithExtra(stat = "Minute: 0 - 15",
                value1 = teamMinute015.value,
                value2 = teamMinute015Percent.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(minute015Constraint) {
                        top.linkTo(awayGoalsItemConstraint.bottom)
                        start.linkTo(parent.start)
                    })

            val teamMinute1630 = remember { viewModel.teamMinute1630Attacking }
            val teamMinute1630Percent = remember { viewModel.teamMinute1630PercentAttacking }
            DataItemWithExtra(stat = "Minute: 16 - 30",
                value1 = teamMinute1630.value,
                value2 = teamMinute1630Percent.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(minute1630Constraint) {
                        top.linkTo(minute015Constraint.bottom)
                        start.linkTo(parent.start)
                    })

            val teamMinute3145 = remember { viewModel.teamMinute3145Attacking }
            val teamMinute3145Percent = remember { viewModel.teamMinute3145PercentAttacking }
            DataItemWithExtra(stat = "Minute: 31 - 45",
                value1 = teamMinute3145.value,
                value2 = teamMinute3145Percent.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(minute3145Constraint) {
                        top.linkTo(minute1630Constraint.bottom)
                        start.linkTo(parent.start)
                    })

            val teamMinute4660 = remember { viewModel.teamMinute4660Attacking }
            val teamMinute4660Percent = remember { viewModel.teamMinute4660PercentAttacking }
            DataItemWithExtra(stat = "Minute: 46 - 60",
                value1 = teamMinute4660.value,
                value2 = teamMinute4660Percent.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(minute4660Constraint) {
                        top.linkTo(minute3145Constraint.bottom)
                        start.linkTo(parent.start)
                    })

            val teamMinute6175 = remember { viewModel.teamMinute6175Attacking }
            val teamMinute6175Percent = remember { viewModel.teamMinute6175PercentAttacking }
            DataItemWithExtra(stat = "Minute: 61 - 75",
                value1 = teamMinute6175.value,
                value2 = teamMinute6175Percent.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(minute6175Constraint) {
                        top.linkTo(minute4660Constraint.bottom)
                        start.linkTo(parent.start)
                    })

            val teamMinute7690 = remember { viewModel.teamMinute7690Attacking }
            val teamMinute7690Percent = remember { viewModel.teamMinute7690PercentAttacking }
            DataItemWithExtra(stat = "Minute: 76 - 90",
                value1 = teamMinute7690.value,
                value2 = teamMinute7690Percent.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(minute7690Constraint) {
                        top.linkTo(minute6175Constraint.bottom)
                        start.linkTo(parent.start)
                    })

            val teamExtraTime = remember { viewModel.teamExtraTimeAttacking }
            val teamExtraTimePercent = remember { viewModel.teamExtraTimePercentAttacking }
            DataItemWithExtra(stat = "Extra Time",
                value1 = teamExtraTime.value,
                value2 = teamExtraTimePercent.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(extraTimeConstraint) {
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                        top.linkTo(minute7690Constraint.bottom)
                    })
        }
    }
}

@Composable
fun TeamDefense(viewModel: TeamProfileViewModel, modifier: Modifier) = TransferTrackerTheme {
    Card(
        elevation = 1.dp,
        shape = RoundedCornerShape(10),
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {

            val (
                title,
                divider,
                totalGoalsItemConstraint,
                homeGoalsItemConstraint,
                awayGoalsItemConstraint,
                minute015Constraint,
                minute1630Constraint,
                minute3145Constraint,
                minute4660Constraint,
                minute6175Constraint,
                minute7690Constraint,
                extraTimeConstraint,
            ) = createRefs()

            Text(text = "Defending",
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

            val teamTotalGoalsItem = remember { viewModel.teamTotalGoalsItemDefense }
            val teamTotalGoalsItemAverage = remember { viewModel.teamTotalGoalsItemAverageDefense }
            DataItemWithExtra(stat = "Goals Against",
                value1 = teamTotalGoalsItem.value,
                value2 = teamTotalGoalsItemAverage.value,
                modifier = Modifier
                    .padding(top = 15.dp, start = 10.dp, end = 10.dp, bottom = 10.dp)
                    .wrapContentSize()
                    .constrainAs(totalGoalsItemConstraint) {
                        top.linkTo(divider.bottom)
                        start.linkTo(parent.start)
                    })

            val teamHomeGoalsItem = remember { viewModel.teamHomeGoalsItemDefense }
            val teamHomeGoalsItemAverage = remember { viewModel.teamHomeGoalsItemAverageDefense }
            DataItemWithExtra(stat = "Home Goals Against",
                value1 = teamHomeGoalsItem.value,
                value2 = teamHomeGoalsItemAverage.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(homeGoalsItemConstraint) {
                        top.linkTo(totalGoalsItemConstraint.bottom)
                        start.linkTo(parent.start)
                    })

            val teamAwayGoalsItem = remember { viewModel.teamAwayGoalsItemDefense }
            val teamAwayGoalsItemAverage = remember { viewModel.teamAwayGoalsItemAverageDefense }
            DataItemWithExtra(stat = "Away Goals Against",
                value1 = teamAwayGoalsItem.value,
                value2 = teamAwayGoalsItemAverage.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(awayGoalsItemConstraint) {
                        top.linkTo(homeGoalsItemConstraint.bottom)
                        start.linkTo(parent.start)
                    })

            val teamMinute015 = remember { viewModel.teamMinute015Defense }
            val teamMinute015Percent = remember { viewModel.teamMinute015PercentDefense }
            DataItemWithExtra(stat = "Minute: 0 - 15",
                value1 = teamMinute015.value,
                value2 = teamMinute015Percent.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(minute015Constraint) {
                        top.linkTo(awayGoalsItemConstraint.bottom)
                        start.linkTo(parent.start)
                    })

            val teamMinute1630 = remember { viewModel.teamMinute1630Defense }
            val teamMinute1630Percent = remember { viewModel.teamMinute1630PercentDefense }
            DataItemWithExtra(stat = "Minute: 16 - 30",
                value1 = teamMinute1630.value,
                value2 = teamMinute1630Percent.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(minute1630Constraint) {
                        top.linkTo(minute015Constraint.bottom)
                        start.linkTo(parent.start)
                    })

            val teamMinute3145 = remember { viewModel.teamMinute3145Defense }
            val teamMinute3145Percent = remember { viewModel.teamMinute3145PercentDefense }
            DataItemWithExtra(stat = "Minute: 31 - 45",
                value1 = teamMinute3145.value,
                value2 = teamMinute3145Percent.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(minute3145Constraint) {
                        top.linkTo(minute1630Constraint.bottom)
                        start.linkTo(parent.start)
                    })

            val teamMinute4660 = remember { viewModel.teamMinute4660Defense }
            val teamMinute4660Percent = remember { viewModel.teamMinute4660PercentDefense }
            DataItemWithExtra(stat = "Minute: 46 - 60",
                value1 = teamMinute4660.value,
                value2 = teamMinute4660Percent.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(minute4660Constraint) {
                        top.linkTo(minute3145Constraint.bottom)
                        start.linkTo(parent.start)
                    })

            val teamMinute6175 = remember { viewModel.teamMinute6175Defense }
            val teamMinute6175Percent = remember { viewModel.teamMinute6175PercentDefense }
            DataItemWithExtra(stat = "Minute: 61 - 75",
                value1 = teamMinute6175.value,
                value2 = teamMinute6175Percent.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(minute6175Constraint) {
                        top.linkTo(minute4660Constraint.bottom)
                        start.linkTo(parent.start)
                    })

            val teamMinute7690 = remember { viewModel.teamMinute7690Defense }
            val teamMinute7690Percent = remember { viewModel.teamMinute7690PercentDefense }
            DataItemWithExtra(stat = "Minute: 76 - 90",
                value1 = teamMinute7690.value,
                value2 = teamMinute7690Percent.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(minute7690Constraint) {
                        top.linkTo(minute6175Constraint.bottom)
                        start.linkTo(parent.start)
                    })

            val teamExtraTime = remember { viewModel.teamExtraTimeDefense }
            val teamExtraTimePercent = remember { viewModel.teamExtraTimePercentDefense }
            DataItemWithExtra(stat = "Extra Time",
                value1 = teamExtraTime.value,
                value2 = teamExtraTimePercent.value,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(extraTimeConstraint) {
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                        top.linkTo(minute7690Constraint.bottom)
                    })
        }
    }
}

@Composable
fun TeamDiscipline(viewModel: TeamProfileViewModel, modifier: Modifier) = TransferTrackerTheme {
    Card(
        elevation = 1.dp,
        shape = RoundedCornerShape(10),
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {


            val (
                title,
                divider,
                cleanSheets,
                minute015,
                minute1630,
                minute3145,
                minute4660,
                minute6175,
                minute7690,
                extraTime,
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

            val teamCleanSheet = remember { viewModel.teamCleanSheet }
            DataItem(stat = "Clean Sheets",
                value = teamCleanSheet.value,
                modifier = Modifier
                    .padding(top = 15.dp, start = 10.dp, end = 10.dp, bottom = 10.dp)
                    .wrapContentSize()
                    .constrainAs(cleanSheets) {
                        top.linkTo(divider.bottom)
                        start.linkTo(parent.start)
                    })

            val teamYellows015 = remember { viewModel.teamYellows015 }
            val teamYellows015Percent = remember { viewModel.teamYellows015Percent }
            val teamReds015 = remember { viewModel.teamReds015 }
            val teamReds015Percent = remember { viewModel.teamReds015Percent }
            DataItemWithExtra(stat = "Minute: 0 - 15",
                value1 = "Yellows: ${teamYellows015.value} (${teamYellows015Percent.value})",
                value2 = "Red: ${teamReds015.value} (${teamReds015Percent.value})",
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(minute015) {
                        top.linkTo(cleanSheets.bottom)
                        start.linkTo(parent.start)
                    })

            val teamYellows1630 = remember { viewModel.teamYellows1630 }
            val teamYellows1630Percent = remember { viewModel.teamYellows1630Percent }
            val teamReds1630 = remember { viewModel.teamReds1630 }
            val teamReds1630Percent = remember { viewModel.teamReds1630Percent }
            DataItemWithExtra(stat = "Minute: 16 - 30",
                value1 = "Yellows: ${teamYellows1630.value} (${teamYellows1630Percent.value})",
                value2 = "Red: ${teamReds1630.value} (${teamReds1630Percent.value})",
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(minute1630) {
                        top.linkTo(minute015.bottom)
                        start.linkTo(parent.start)
                    })


            val teamYellows3145 = remember { viewModel.teamYellows3145 }
            val teamYellows3145Percent = remember { viewModel.teamYellows3145Percent }
            val teamReds3145 = remember { viewModel.teamReds3145 }
            val teamReds3145Percent = remember { viewModel.teamReds3145Percent }
            DataItemWithExtra(stat = "Minute: 31 - 45",
                value1 = "Yellows: ${teamYellows3145.value} (${teamYellows3145Percent.value})",
                value2 = "Red: ${teamReds3145.value} (${teamReds3145Percent.value})",
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(minute3145) {
                        top.linkTo(minute1630.bottom)
                        start.linkTo(parent.start)
                    })

            val teamYellows4660 = remember { viewModel.teamYellows4660 }
            val teamYellows4660Percent = remember { viewModel.teamYellows4660Percent }
            val teamReds4660 = remember { viewModel.teamReds4660 }
            val teamReds4660Percent = remember { viewModel.teamReds4660Percent }
            DataItemWithExtra(stat = "Minute: 46 - 60",
                value1 = "Yellows: ${teamYellows4660.value} (${teamYellows4660Percent.value})",
                value2 = "Red: ${teamReds4660.value} (${teamReds4660Percent.value})",
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(minute4660) {
                        top.linkTo(minute3145.bottom)
                        start.linkTo(parent.start)
                    })

            val teamYellows6175 = remember { viewModel.teamYellows6175 }
            val teamYellows6175Percent = remember { viewModel.teamYellows6175Percent }
            val teamReds6175 = remember { viewModel.teamReds6175 }
            val teamReds6175Percent = remember { viewModel.teamReds6175Percent }
            DataItemWithExtra(stat = "Minute: 61 - 75",
                value1 = "Yellows: ${teamYellows6175.value} (${teamYellows6175Percent.value})",
                value2 = "Red: ${teamReds6175.value} (${teamReds6175Percent.value})",
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(minute6175) {
                        top.linkTo(minute4660.bottom)
                        start.linkTo(parent.start)
                    })

            val teamYellows7690 = remember { viewModel.teamYellows7690 }
            val teamYellows7690Percent = remember { viewModel.teamYellows7690Percent }
            val teamReds7690 = remember { viewModel.teamReds7690 }
            val teamReds7690Percent = remember { viewModel.teamReds7690Percent }
            DataItemWithExtra(stat = "Minute: 76 - 90",
                value1 = "Yellows: ${teamYellows7690.value} (${teamYellows7690Percent.value})",
                value2 = "Red: ${teamReds7690.value} (${teamReds7690Percent.value})",
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(minute7690) {
                        top.linkTo(minute6175.bottom)
                        start.linkTo(parent.start)
                    })

            val teamYellowsExtraTime = remember { viewModel.teamYellowsExtraTime }
            val teamYellowsExtraTimePercent = remember { viewModel.teamYellowsExtraTimePercent }
            val teamRedsExtraTime = remember { viewModel.teamRedsExtraTime }
            val teamRedsExtraTimePercent = remember { viewModel.teamRedsExtraTimePercent }
            DataItemWithExtra(stat = "Extra Time",
                value1 = "Yellows: ${teamYellowsExtraTime.value} (${teamYellowsExtraTimePercent.value})",
                value2 = "Red: ${teamRedsExtraTime.value} (${teamRedsExtraTimePercent.value})",
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(extraTime) {
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                        top.linkTo(minute7690.bottom)
                    })
        }
    }
}

/*
DataItems
 */
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
private fun DataItemWithExtra(stat: String, value1: String, value2: String, modifier: Modifier) =
    TransferTrackerTheme {
        ConstraintLayout(
            modifier = modifier
                .fillMaxWidth()
        ) {
            val (statText, valueText, valueAvgText) = createRefs()

            Text(text = stat,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(end = 5.dp, start = 25.dp, bottom = 5.dp)
                    .constrainAs(statText) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        bottom.linkTo(valueAvgText.top)
                    }
            )

            Text(text = value1,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(end = 50.dp, bottom = 5.dp)
                    .constrainAs(valueText) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        bottom.linkTo(valueAvgText.top)
                    }
            )

            Text(text = value2,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(end = 50.dp)
                    .constrainAs(valueAvgText) {
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
            )
        }
    }