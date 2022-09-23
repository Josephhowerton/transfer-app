package com.transfers.transfertracker.view.country

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.room.rxjava3.EmptyResultSetException
import com.transfers.transfertracker.R
import com.transfers.transfertracker.enums.EScreen
import com.transfers.transfertracker.model.country.Country
import com.transfers.transfertracker.navigation.Navigator
import com.transfers.transfertracker.repo.MainRepository
import com.transfers.transfertracker.util.SubscribeOnLifecycle
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject

@HiltViewModel
class CountryListViewModel @Inject constructor(
    private val repository: MainRepository,
    private val navigator: Navigator
): ViewModel(), SubscribeOnLifecycle {
    private val compositeDisposable = CompositeDisposable()

    private val _countriesList = mutableStateListOf<Country>()
    val countriesList: List<Country> get() = _countriesList

    val shouldShowErrorDialog = mutableStateOf(false)
    val errorTitle = mutableStateOf(R.string.title_generic_error)
    val errorMessage = mutableStateOf(R.string.message_find_countries_error)

    init {
        getAllCountries()
    }

    fun onDestroy() {
        compositeDisposable.dispose()
    }

    override fun subscribeOnLifecycle(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    private fun getAllCountries() {
        subscribeOnLifecycle(repository.getAllCountries()
            .filter {
                if(it.isEmpty()){
                    fetchAllCountries()
                }
                it.isNotEmpty()
            }
            .subscribe(
                {
                    if(it.isNotEmpty()){
                        _countriesList.clear()
                        _countriesList.addAll(it)
                    }else{
                        shouldShowErrorDialog.value = true
                    }
                },
                {
                    if(it is EmptyResultSetException){
                        fetchAllCountries()
                    }else{
                        shouldShowErrorDialog.value = true
                    }
                }
            )
        )
    }

    private fun fetchAllCountries() {
        subscribeOnLifecycle(repository.fetchAllCountries()
            .subscribe(
                {
                    if(it.isNotEmpty()){
                        _countriesList.clear()
                        _countriesList.addAll(it)
                    }else{
                        shouldShowErrorDialog.value = true
                    }
                },
                {
                    shouldShowErrorDialog.value = true
                }
            )
        )
    }

    fun navigateToLeaguesList(country: String){
        navigator.navigateTo("${EScreen.LEAGUE_LIST}/$country")
    }
}