package com.transfers.transfertracker.view.country

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.transfers.transfertracker.R
import com.transfers.transfertracker.model.country.Country
import com.transfers.transfertracker.view.component.TransferTrackerAlertDialog
import com.transfers.transfertracker.view.component.TransferTopAppBar
import com.transfers.transfertracker.view.theme.TransferTrackerTheme

@Preview
@Composable
fun CountryListPreview() = TransferTrackerTheme {
    CountryList{}
}

@Composable
fun CountryList(onErrorAction: () -> Unit) = TransferTrackerTheme {
    val viewModel: CountryListViewModel = hiltViewModel()
    val countries by remember { mutableStateOf(viewModel.countriesList) }
    val showErrorDialog by remember { viewModel.shouldShowErrorDialog }
    val errorTitle by remember { viewModel.errorTitle }
    val errorMessage by remember { viewModel.errorMessage }

    DisposableEffect(key1 = viewModel) {
        onDispose { viewModel.onDestroy() }
    }

    Scaffold(topBar = {

        TransferTopAppBar(stringResource(id = R.string.title_country_list), false, onErrorAction)

    }) {

        TransferTrackerAlertDialog(
            title = errorTitle,
            message = errorMessage,
            buttonTitle = R.string.title_dismiss_button,
            showErrorDialog = showErrorDialog,
            onErrorAction
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(countries.size) { index ->
                CountryListItem(viewModel, countries[index], onErrorAction)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CountryListItem(viewModel: CountryListViewModel, country: Country, onErrorAction: () -> Unit) =
    Card(shape = RoundedCornerShape(10.dp),
        elevation = 2.dp,
        onClick = { viewModel.navigateToLeaguesList(country.name) }) {

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