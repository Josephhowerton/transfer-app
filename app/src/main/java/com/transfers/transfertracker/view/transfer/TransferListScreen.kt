package com.transfers.transfertracker.view.transfer

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.skydoves.landscapist.glide.GlideImage
import com.transfers.transfertracker.R
import com.transfers.transfertracker.enums.ETransfer
import com.transfers.transfertracker.view.component.TransferTopAppBar
import com.transfers.transfertracker.view.component.TransferTrackerAlertDialog
import com.transfers.transfertracker.view.theme.CardBackgroundColor
import com.transfers.transfertracker.view.theme.HyperLinkBlue
import com.transfers.transfertracker.view.theme.TransferTrackerTheme

@Composable
fun TransferList(onErrorAction: () -> Unit) = TransferTrackerTheme {
    Scaffold(topBar = {

        TransferTopAppBar(stringResource(id = R.string.title_player_profile), false, onErrorAction)

    }) {
        val viewModel: TransferListViewModel = hiltViewModel()

        DisposableEffect(key1 = viewModel) {
            onDispose { viewModel.onDestroy() }
        }

        val showErrorDialog by remember { viewModel.shouldShowErrorDialog }
        val errorTitle by remember { viewModel.errorTitle }
        val errorMessage by remember { viewModel.errorMessage }

        TransferTrackerAlertDialog(
            title = errorTitle,
            message = errorMessage,
            buttonTitle = R.string.title_dismiss_button,
            showErrorDialog = showErrorDialog,
            onClick = onErrorAction
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(viewModel.transferList) { transfer ->

                val name = transfer.name
                val type = transfer.type
                val id = transfer.id
                val enum = transfer.transfer
                val photo = transfer.photo
                val teamId = transfer.teamId

                if (name != null && id != null && photo != null) {
                    val direction = when (enum) {
                        ETransfer.IN -> R.drawable.ic_baseline_transfer_in
                        ETransfer.OUT -> R.drawable.ic_baseline_transfer_out
                        else -> R.drawable.ic_baseline_circle_24
                    }
                    TransferListItem(viewModel, name, type, id, teamId.toString(), direction, photo)
                } else {
                    viewModel.shouldShowErrorDialog.value = true
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun TransferListItem(
    viewModel: TransferListViewModel,
    name: String,
    type: String?,
    id: Int,
    teamId: String,
    what: Int,
    photoUrl: Any
) =
    Card(backgroundColor = CardBackgroundColor,
        elevation = 2.dp,
        onClick = {
            viewModel.navigateToPlayerProfile(id.toString(), teamId, "ignore")
        }
    ) {
        ConstraintLayout(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp, bottom = 5.dp)) {
            val (leagueImage, leagueName, transferType, direction) = createRefs()
            GlideImage(imageModel = photoUrl,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(start = 5.dp)
                    .size(36.dp)
                    .constrainAs(leagueImage) {
                        start.linkTo(parent.start)
                        top.linkTo(leagueName.top)
                        bottom.linkTo(leagueName.bottom)
                    }
            )

            Text(text = name,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(end = 5.dp, start = 5.dp, top = 15.dp, bottom = 15.dp)
                    .constrainAs(leagueName) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(leagueImage.end)
                    }
            )

            if (type != null) {
                Text(text = type,
                    fontSize = 18.sp,
                    color = HyperLinkBlue,
                    modifier = Modifier
                        .padding(end = 5.dp, start = 5.dp)
                        .constrainAs(transferType) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            end.linkTo(direction.start)
                        }
                )
            }

            GlideImage(imageModel = what,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(start = 5.dp)
                    .size(24.dp)
                    .constrainAs(direction) {
                        end.linkTo(parent.end)
                        top.linkTo(leagueName.top)
                        bottom.linkTo(leagueName.bottom)
                    }
            )
        }
    }