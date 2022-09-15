package com.transfers.transfertracker.view.news

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.skydoves.landscapist.glide.GlideImage
import com.transfers.transfertracker.R
import com.transfers.transfertracker.model.news.News
import com.transfers.transfertracker.view.theme.CardBackgroundColor
import com.transfers.transfertracker.view.theme.NewsOverlayColor
import com.transfers.transfertracker.view.theme.TransferTrackerTheme

@Preview
@Composable
fun NewsList() = TransferTrackerTheme {
    LazyColumn(
        Modifier.fillMaxSize()
    ) {
        items(20){
            NewsListItemPreview()
        }
    }
}

@Preview
@Composable
fun NewsListItemPreview() = Card(elevation = 0.dp) {
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (newsImage, newsTitle) = createRefs()
        Image(painter = painterResource(id = R.drawable.ic_baseline_sports_soccer_24),
            contentDescription = "",
            modifier = Modifier
                .padding(end = 5.dp, start = 5.dp, top = 15.dp, bottom = 15.dp)
                .constrainAs(newsImage) {
                    start.linkTo(parent.start)
                    top.linkTo(newsTitle.top)
                    bottom.linkTo(newsTitle.bottom)
                }
        )

        Text(text = "Player",
            fontSize = 24.sp,
            modifier = Modifier
                .padding(end = 5.dp, start = 5.dp, top = 15.dp, bottom = 15.dp)
                .constrainAs(newsTitle) {
                    top.linkTo(parent.top)
                    start.linkTo(newsImage.end)
                }
        )
    }
}


@Composable
fun NewsListScreen(viewModel: NewsListViewModel) = TransferTrackerTheme {
    val news = remember {
        viewModel.newsList
    }
    LazyColumn(Modifier.fillMaxSize()) {
        items(news.size){ index ->
            val article = news[index]
            val title = article.title
            val imageUrl = article.image_url
            if(title != null){
                if(imageUrl.isNullOrEmpty()){
                    NewsItemWithoutImage(viewModel = viewModel, title = title, link = article.link)
                }else{
                    NewsItem(viewModel = viewModel, title = title, imageUrl = imageUrl, link = article.link)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun NewsItem(viewModel: NewsListViewModel, title: String, imageUrl: String, link: String?) =
    Card(elevation = 2.dp,
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier.padding(5.dp),
        onClick = {  }
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
private fun NewsItemWithoutImage(viewModel: NewsListViewModel, title: String, link: String?) =
    Card(backgroundColor = CardBackgroundColor,
        elevation = 2.dp,
        onClick = {  }) {
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
