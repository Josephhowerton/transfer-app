package com.transfers.transfertracker.view.news

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.transfers.transfertracker.model.news.News
import com.transfers.transfertracker.navigation.Navigator
import com.transfers.transfertracker.repo.MainRepository
import javax.inject.Inject

class NewsListViewModel @Inject constructor(
    private val repository: MainRepository,
    private val navigator: Navigator
) : ViewModel() {

    private val _newsList = mutableStateListOf<News>()
    val newsList: MutableList<News> get() = _newsList

    val currentTeamBitmap = mutableStateOf("")
}