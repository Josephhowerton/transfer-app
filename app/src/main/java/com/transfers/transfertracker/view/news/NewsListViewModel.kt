package com.transfers.transfertracker.view.news

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.transfers.transfertracker.R
import com.transfers.transfertracker.enums.EScreen
import com.transfers.transfertracker.model.news.News
import com.transfers.transfertracker.navigation.Navigator
import com.transfers.transfertracker.repo.MainRepository
import com.transfers.transfertracker.util.SubscribeOnLifecycle
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val repository: MainRepository,
    private val navigator: Navigator
) : ViewModel(), SubscribeOnLifecycle {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private val _newsList = mutableStateListOf<News>()
    val newsList: MutableList<News> get() = _newsList

    val shouldShowErrorDialog = mutableStateOf(false)
    val errorTitle = mutableStateOf(R.string.title_generic_error)
    val errorMessage = mutableStateOf(R.string.message_find_news_error)

    init {
        fetchTeamNews()
    }

    fun onDestroy() {
        compositeDisposable.dispose()
    }

    override fun subscribeOnLifecycle(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }


    private fun fetchTeamNews() {
        subscribeOnLifecycle(
            repository.getCurrentTeam()
                .flatMap {
                    it.name?.let { name ->
                        return@flatMap repository.fetchLatestTeamNews(name)
                    }
                    throw Exception("")
                }.subscribe(
                    {
                        if(!it.isNullOrEmpty()){
                            _newsList.clear()
                            _newsList.addAll(it)
                        }else {
                            shouldShowErrorDialog.value = true
                        }
                    },
                    {
                        shouldShowErrorDialog.value = true
                    }
                )
        )
    }

    fun navigateToArticle(link: String) {
        val encoded = URLEncoder.encode(link, StandardCharsets.UTF_8.toString())
        navigator.navigateTo("${EScreen.NEWS_WEB_VIEW}/$encoded")
    }
}