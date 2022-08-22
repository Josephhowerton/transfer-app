package com.transfers.transfertracker.view.list

import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.transfers.transfertracker.R
import com.transfers.transfertracker.enums.Screen
import com.transfers.transfertracker.model.country.Country
import com.transfers.transfertracker.view.main.viewmodel.DashboardViewModel
import com.transfers.transfertracker.view.theme.TransferTrackerTheme

@Preview
@Composable
fun CountryListPreview() = TransferTrackerTheme {
    LazyColumn(Modifier.fillMaxSize()) {
        items(20){
            CountryListItemPreview()
        }
    }
}

@Preview
@Composable
fun CountryListItemPreview() = Card(elevation = 0.dp) {
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (leagueImage, leagueName) = createRefs()
        Image(painter = painterResource(id = R.drawable.ic_baseline_flag_24),
            contentDescription = "",
            modifier = Modifier
                .padding(end = 5.dp, start = 5.dp, top = 15.dp, bottom = 15.dp)
                .constrainAs(leagueImage) {
                    start.linkTo(parent.start)
                    top.linkTo(leagueName.top)
                    bottom.linkTo(leagueName.bottom)
                }
        )

        Text(text = "Country",
            fontSize = 24.sp,
            modifier = Modifier
                .padding(end = 5.dp, start = 5.dp, top = 15.dp, bottom = 15.dp)
                .constrainAs(leagueName) {
                    top.linkTo(parent.top)
                    start.linkTo(leagueImage.end)
                }
        )
    }
}

@Composable
fun CountryList(navController: NavHostController, dashboardViewModel: DashboardViewModel) = TransferTrackerTheme {
    dashboardViewModel.fetchCountries()
    val countries by remember { mutableStateOf(dashboardViewModel.countriesList) }
    LazyColumn(Modifier.fillMaxSize()) {
        items(countries.size) { index ->
            CountryListItem(dashboardViewModel, navController, countries[index])
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CountryListItem(viewModel: DashboardViewModel, navController: NavHostController, country: Country) =
    Card(elevation = 0.dp, onClick = {
        viewModel.fetchLeaguesByCountry(country)
        navController.navigate(Screen.LEAGUE_LIST.name)
    }) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (flag, name) = createRefs()

            Image(
                painter = rememberAsyncImagePainter(model = country.flag),
                contentDescription = "${country.name} flag",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(36.dp)
                    .padding(end = 2.dp)
                    .constrainAs(flag) {
                        start.linkTo(parent.start)
                        top.linkTo(name.top)
                        bottom.linkTo(name.bottom)
                        end.linkTo(name.start)
                    }
            )

            Text(text = country.name,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(end = 5.dp, start = 5.dp, top = 15.dp, bottom = 15.dp)
                    .constrainAs(name) {
                        top.linkTo(parent.top)
                        start.linkTo(flag.end)
                    }
            )
        }
}