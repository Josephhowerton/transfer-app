package com.transfers.transfertracker.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.rememberDrawablePainter
import com.transfers.transfertracker.R
import com.transfers.transfertracker.view.theme.TransferTrackerTheme

@Preview(showBackground = true)
@Composable
fun TeamsComponentScreenPreview() {
    TeamsComponentScreen(Modifier)
}

@Composable
fun TeamsComponentScreen(modifier: Modifier) = TransferTrackerTheme {
    Card(shape = RoundedCornerShape(2),
        modifier = modifier
            .fillMaxWidth()
            .height(450.dp)) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (descriptionText,
                addATeamText,
                selectedTeam,
                selectedTeamImage,
                secondTeam,
                thirdTeam,
                fourthTeam,
                fifthTeam
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

            Text(text = "PlaceHolderText",
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

            Text(text = "Select a team below to change data.",
                modifier = Modifier.constrainAs(descriptionText) {
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                    top.linkTo(selectedTeam.bottom, 5.dp)
                }
            )

            TeamItem(
                teamName = "Team 1",
                crestUrl = "",
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(secondTeam){
                        top.linkTo(descriptionText.bottom)
                        bottom.linkTo(thirdTeam.top)
                    }
            )

            TeamItem(
                teamName = "Team 2",
                crestUrl = "",
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(thirdTeam){
                        top.linkTo(secondTeam.bottom)
                        bottom.linkTo(fourthTeam.top)
                    }
            )

            TeamItem(

                teamName = "Team 3",
                crestUrl = "",
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
                    .constrainAs(fourthTeam){
                        top.linkTo(thirdTeam.bottom)
                        bottom.linkTo(fifthTeam.top)
                    }
            )

            TeamItem(
                teamName = "Team 4",
                crestUrl = "",
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .constrainAs(fifthTeam){
                        top.linkTo(fourthTeam.bottom)
                        bottom.linkTo(addATeamText.top)
                    }
            )

            ClickableText(onClick = {}, text = AnnotatedString("Add Team"),
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
fun TeamItem(teamName: String, crestUrl: String, modifier: Modifier) =
    Card(elevation = 0.dp, modifier = modifier) {
        ConstraintLayout(modifier = Modifier
            .fillMaxWidth()) {
            val (crest, name) = createRefs()
            Image(painter = painterResource(id = R.drawable.ic_baseline_sports_soccer_24),
                contentDescription = "",
                modifier = Modifier
                    .padding(end = 5.dp, start = 5.dp, top = 15.dp, bottom = 15.dp)
                    .constrainAs(crest) {
                        start.linkTo(parent.start)
                        top.linkTo(name.top)
                        bottom.linkTo(name.bottom)
                    }
            )

            Text(text = teamName,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(end = 5.dp, start = 5.dp, top = 15.dp, bottom = 15.dp)
                    .constrainAs(name) {
                        top.linkTo(parent.top)
                        start.linkTo(crest.end)
                    }
            )
        }
}
