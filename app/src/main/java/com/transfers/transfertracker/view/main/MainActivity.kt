package com.transfers.transfertracker.view.main

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.transfers.transfertracker.di.TransferApplication
import com.transfers.transfertracker.di.factory.ViewModelFactory
import com.transfers.transfertracker.view.main.screens.DashboardScreen
import com.transfers.transfertracker.view.main.viewmodel.DashboardViewModel
import com.transfers.transfertracker.view.navigation.*
import com.transfers.transfertracker.view.theme.TransferTrackerTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject lateinit var viewModelFactory: ViewModelFactory

    private val dashboardViewModel by viewModels<DashboardViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as TransferApplication)
            .component
            .dashboardComponent()
            .create()
            .inject(this)

        setContent {
            TransferTrackerTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    DashboardNavGraph(viewModel = dashboardViewModel)
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        DashboardScreen()
    }
}