package com.transfers.transfertracker.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.transfers.transfertracker.di.TransferApplication
import com.transfers.transfertracker.navigation.*
import com.transfers.transfertracker.view.theme.TransferTrackerTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val transferApplication = (application as TransferApplication)
        transferApplication
            .dashboardComponent
            .inject(this)

        setContent {
            TransferTrackerTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    DashboardNavGraph(application = transferApplication, activity = this, navigator = navigator)
                }
            }
        }
    }
}