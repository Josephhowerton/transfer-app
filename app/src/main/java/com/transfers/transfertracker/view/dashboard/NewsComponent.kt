package com.transfers.transfertracker.view.components

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.skydoves.landscapist.glide.GlideImage
import com.transfers.transfertracker.R
import com.transfers.transfertracker.view.component.TransferTrackerAlertDialog
import com.transfers.transfertracker.view.component.TransferTopAppBar
import com.transfers.transfertracker.view.dashboard.DashboardViewModel
import com.transfers.transfertracker.view.dashboard.GetStartedItem
import com.transfers.transfertracker.view.theme.CardBackgroundColor
import com.transfers.transfertracker.view.theme.HyperLinkBlue
import com.transfers.transfertracker.view.theme.NewsOverlayColor
import com.transfers.transfertracker.view.theme.TransferTrackerTheme


@Preview(showBackground = true)
@Composable
fun NewsComponentScreenPreview() = TransferTrackerTheme {
    Card(shape = RoundedCornerShape(2),
        modifier = Modifier
            .fillMaxWidth()
            .height(450.dp)) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (descriptionText,
                addATeamText,
                selectedTeam,
                selectedTeamImage,
                newsList
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

            Text(text = "Team News",
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

            Text(text = "Select an article below to view.",
                modifier = Modifier.constrainAs(descriptionText) {
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                    top.linkTo(selectedTeam.bottom, 5.dp)
                }
            )

            LazyColumn( verticalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier
                    .padding(top = 20.dp, bottom = 40.dp)
                    .constrainAs(newsList) {
                        height = Dimension.fillToConstraints
                        top.linkTo(descriptionText.bottom)
                        bottom.linkTo(parent.bottom)
                    }) {
                items(5) {
                    NewsItemPreview()
                }
            }

            ClickableText(onClick = {}, text = AnnotatedString("More"),
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
fun NewsComponentScreen(viewModel: DashboardViewModel, modifier: Modifier) = TransferTrackerTheme {
    Card(shape = RoundedCornerShape(2), modifier = modifier
        .fillMaxWidth()
        .height(450.dp)) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (descriptionText,
                moreNews,
                selectedTeam,
                selectedTeamImage,
                newsList
            ) = createRefs()

            val currentTeam = remember {
                viewModel.selectedTeam
            }

            val news = remember {
                viewModel.newsList
            }

            val logo = if(!currentTeam.value?.logo.isNullOrEmpty())
                currentTeam.value?.logo
            else
                R.drawable.ic_baseline_sports_soccer_24


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

            Text(text = "News",
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

            Text(text = "Select an article below to view.",
                modifier = Modifier.constrainAs(descriptionText) {
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                    top.linkTo(selectedTeam.bottom, 5.dp)
                }
            )

            if(currentTeam.value == null){
                GetStartedItem("Select A Team\n\nTo Get Started", Modifier
                    .padding(top = 20.dp, bottom = 40.dp)
                    .constrainAs(newsList) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(descriptionText.bottom)
                        bottom.linkTo(parent.bottom)
                    })
            }
            else if (news.isEmpty()){
                GetStartedItem("Sorry, we could not locate any news\n\nfor your team", Modifier
                    .padding(top = 20.dp, bottom = 40.dp)
                    .constrainAs(newsList) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(descriptionText.bottom)
                        bottom.linkTo(parent.bottom)
                    })
            }
            else{
                LazyColumn( verticalArrangement = Arrangement.spacedBy(15.dp),
                    modifier = Modifier
                        .padding(top = 20.dp, bottom = 5.dp)
                        .constrainAs(newsList) {
                            height = Dimension.fillToConstraints
                            top.linkTo(descriptionText.bottom)
                            bottom.linkTo(moreNews.top)
                        }) {
                    items(news.size) { index ->
                        val article = news[index]
                        val title = article.title
                        val imageUrl = article.image_url
                        val link = article.link
                        if(!title.isNullOrEmpty() && !link.isNullOrEmpty()){
                            if(imageUrl.isNullOrEmpty()){
                                NewsItemWithoutImage(viewModel = viewModel, title = title, link = link)
                            }else{
                                NewsItem(viewModel = viewModel, title = title, imageUrl = imageUrl, link = link)
                            }
                        }
                    }
                }
            }

            if(news.isNotEmpty()) {
                ClickableText(onClick = { viewModel.navigateToNewsList() }, text = AnnotatedString("More"),
                    style = TextStyle(
                        color = HyperLinkBlue,
                        fontSize = 20.sp
                    ),
                    modifier = Modifier
                        .padding(end = 15.dp, bottom = 15.dp, top = 5.dp)
                        .constrainAs(moreNews) {
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun NewsItem(viewModel: DashboardViewModel, title: String, imageUrl: String, link: String) =
    Card(elevation = 2.dp,
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier.padding(5.dp),
        onClick = { viewModel.navigateToArticle(link) }
    ) {
        ConstraintLayout(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
        ) {
            val (image, overlay, name) = createRefs()

            GlideImage(imageModel = imageUrl,
                contentDescription = "",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .constrainAs(image) {
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
            )

            Box(modifier = Modifier
                .background(NewsOverlayColor, shape = RoundedCornerShape(5.dp))
                .constrainAs(overlay) {
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(name.top)
                    bottom.linkTo(parent.bottom)
                }
            )

            Text(text = title,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                maxLines = 1,
                modifier = Modifier
                    .padding(end = 5.dp, start = 5.dp, top = 10.dp, bottom = 15.dp)
                    .constrainAs(name) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
            )
        }
    }

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun NewsItemWithoutImage(viewModel: DashboardViewModel, title: String, link: String) =
    Card(backgroundColor = CardBackgroundColor,
        elevation = 2.dp,
        onClick = { viewModel.navigateToArticle(link) }) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val (name) = createRefs()

            Text(text = title,
                fontSize = 18.sp,
                maxLines = 2,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(end = 5.dp, start = 7.dp, top = 15.dp, bottom = 15.dp)
                    .constrainAs(name) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    }
            )
        }
    }

@Composable
fun NewsItemPreview() =
    Card(elevation = 2.dp, shape = RoundedCornerShape(5.dp), modifier = Modifier.padding(5.dp)) {
        ConstraintLayout(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()) {
            val (image, overlay, name) = createRefs()

            Image(painter = painterResource(id = R.mipmap.don_conte_tuchel),
                contentDescription = "",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .constrainAs(image) {
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
            )

            Box(modifier = Modifier
                .background(NewsOverlayColor, shape = RoundedCornerShape(5.dp))
                .constrainAs(overlay) {
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(name.top)
                    bottom.linkTo(parent.bottom)
                }
            )

            Text(text = """
                |Why did Conte and Tuchel clash in Chelsea vs. 
                |Tottenham draw? Red cards and manager history explained
                |""".trimMargin(),
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                maxLines = 1,
                modifier = Modifier
                    .padding(end = 5.dp, start = 5.dp, top = 10.dp, bottom = 15.dp)
                    .constrainAs(name) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
            )
        }
    }

@Preview
@Composable
fun NewsScreenPreview(){
    AndroidView(factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = WebViewClient()
            loadUrl("https://goodereader.com/blog/technology/google-news-app-for-android-gets-revamped")
        }
    }, update = {
        it.loadUrl("https://goodereader.com/blog/technology/google-news-app-for-android-gets-revamped")
    })
}

@Composable
fun NewsScreen(link: String?, onErrorAction: () -> Unit) = TransferTrackerTheme {

    Scaffold(topBar = {

        TransferTopAppBar(stringResource(id = R.string.title_news_web), false, onErrorAction)

    }) {
        link?.let { url ->
            AndroidView(factory = {
                WebView(it).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    webViewClient = WebViewClient()
                    loadUrl(url)
                }
            }, update = {
                it.loadUrl(url)
            })
        } ?: TransferTrackerAlertDialog(
            title = R.string.title_generic_error,
            message = R.string.message_generic_error,
            buttonTitle = R.string.title_dismiss_button,
            showErrorDialog = true,
            onClick = onErrorAction
        )
    }
}