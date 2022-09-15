package com.transfers.transfertracker.view.country

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.transfers.transfertracker.R
import com.transfers.transfertracker.model.country.Country
import com.transfers.transfertracker.view.dashboard.DashboardViewModel
import com.transfers.transfertracker.view.theme.TransferTrackerTheme

@Preview
@Composable
fun CountryListPreview() = TransferTrackerTheme {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(3.dp),
        modifier= Modifier.fillMaxSize()) {
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
fun CountryList(viewModel: CountryListViewModel) = TransferTrackerTheme {
    val countries by remember { mutableStateOf(viewModel.countriesList) }
    LazyColumn(verticalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier.fillMaxSize()) {
        items(countries.size) { index ->
            CountryListItem(viewModel, countries[index])
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CountryListItem(viewModel: CountryListViewModel, country: Country) =
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = 2.dp,
        onClick = {
            viewModel.navigateToLeaguesList(country.name)
        }
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (flag, name) = createRefs()

            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .decoderFactory(SvgDecoder.Factory())
                    .data(country.flag)
                    .build()
            )
            Image(painter = painter,
                contentDescription = "${country.name} flag",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(36.dp)
                    .padding(start = 5.dp)
                    .clip(CircleShape)
                    .constrainAs(flag) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
            )

            Text(text = country.name,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(end = 5.dp, start = 10.dp, top = 15.dp, bottom = 15.dp)
                    .constrainAs(name) {
                        top.linkTo(parent.top)
                        start.linkTo(flag.end)
                    }
            )
        }
}

@Composable
fun SvgImageSample() {

}