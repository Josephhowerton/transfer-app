package com.transfers.transfertracker.view.stats

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.transfers.transfertracker.R
import com.transfers.transfertracker.view.auth.viewmodel.AuthViewModel
import com.transfers.transfertracker.view.main.viewmodel.DashboardViewModel
import com.transfers.transfertracker.view.theme.TransferTrackerTheme

@Composable
fun TeamStatistics(viewModel: DashboardViewModel, navController: NavController) = TransferTrackerTheme {

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (playerImage, playersNameText, birthplaceText, countryFlagImage) = createRefs()

        Image(painter = painterResource(id = R.drawable.ic_baseline_sports_soccer_24),
            contentDescription = "Team Crest",
            modifier = Modifier.size(120.dp)
                .padding(0.dp)
                .constrainAs(playerImage) {
                    start.linkTo(parent.start, 10.dp)
                    bottom.linkTo(birthplaceText.bottom)
                    top.linkTo(parent.top)
                }
        )

        Text(text = "Team Name",
            fontSize = 24.sp,
            modifier = Modifier.constrainAs(playersNameText) {
                top.linkTo(playerImage.bottom)
                start.linkTo(playerImage.start)
            }
        )

        Image(painter = painterResource(id = R.drawable.ic_baseline_sports_soccer_24),
            contentDescription = "",
            modifier = Modifier
                .size(18.dp)
                .constrainAs(countryFlagImage) {
                    start.linkTo(playerImage.start)
                    top.linkTo(birthplaceText.top)
                    bottom.linkTo(birthplaceText.bottom)
                }
        )

        Text(text = "League",
            fontSize = 16.sp,
            modifier = Modifier
                .padding(end = 5.dp, start = 5.dp, top = 15.dp, bottom = 15.dp)
                .constrainAs(birthplaceText) {
                    top.linkTo(playersNameText.bottom)
                    start.linkTo(countryFlagImage.end)
                }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TeamStatisticsPreview() = TransferTrackerTheme {

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (playerImage, playersNameText, birthplaceText, countryFlagImage) = createRefs()

        Image(painter = painterResource(id = R.drawable.ic_baseline_sports_soccer_24),
            contentDescription = "Team Crest",
            modifier = Modifier.size(120.dp)
                .padding(0.dp)
                .constrainAs(playerImage) {
                    start.linkTo(parent.start, 10.dp)
                    bottom.linkTo(birthplaceText.bottom)
                    top.linkTo(parent.top)
                }
        )

        Text(text = "Team Name",
            fontSize = 24.sp,
            modifier = Modifier.constrainAs(playersNameText) {
                top.linkTo(playerImage.bottom)
                start.linkTo(playerImage.start)
            }
        )

        Image(painter = painterResource(id = R.drawable.ic_baseline_sports_soccer_24),
            contentDescription = "",
            modifier = Modifier
                .size(18.dp)
                .constrainAs(countryFlagImage) {
                    start.linkTo(playerImage.start)
                    top.linkTo(birthplaceText.top)
                    bottom.linkTo(birthplaceText.bottom)
                }
        )

        Text(text = "League",
            fontSize = 16.sp,
            modifier = Modifier
                .padding(end = 5.dp, start = 5.dp, top = 15.dp, bottom = 15.dp)
                .constrainAs(birthplaceText) {
                    top.linkTo(playersNameText.bottom)
                    start.linkTo(countryFlagImage.end)
                }
        )
    }
}