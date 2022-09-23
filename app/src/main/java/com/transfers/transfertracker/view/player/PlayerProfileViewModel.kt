package com.transfers.transfertracker.view.player

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.transfers.transfertracker.R
import com.transfers.transfertracker.model.player.Player
import com.transfers.transfertracker.model.player.PlayerStatistic
import com.transfers.transfertracker.repo.MainRepository
import com.transfers.transfertracker.util.SubscribeOnLifecycle
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject

@HiltViewModel
class PlayerProfileViewModel @Inject constructor(
    private val repository: MainRepository,
): ViewModel(), SubscribeOnLifecycle {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    val shouldShowErrorDialog = mutableStateOf(false)
    val errorTitle = mutableStateOf(R.string.title_generic_error)
    val errorMessage = mutableStateOf(R.string.message_find_countries_error)

    //Player Profile
    val playerPhoto = mutableStateOf(Any())
    val playerRating = mutableStateOf("")
    val playerRatingDouble = mutableStateOf(0.0)
    val playerInjured = mutableStateOf(false)
    val playerName = mutableStateOf("")
    val playerCountryFlag = mutableStateOf("")
    val playerCountryFlagDescription = mutableStateOf("")
    val playerLogo = mutableStateOf(Any())

    //Player Details
    val playerPosition = mutableStateOf("")
    val playerAge = mutableStateOf("")
    val playerHeight = mutableStateOf("")
    val playerWeight = mutableStateOf("")

    //Player Details
    val playerAppearances = mutableStateOf("")
    val playerLineups = mutableStateOf("")
    val playerMinutes = mutableStateOf("")

    //Player Attacking
    val playerShots = mutableStateOf("")
    val playerShotsOnTarget = mutableStateOf("")
    val playerGoals = mutableStateOf("")
    val playerAssists = mutableStateOf("")
    val playerDribbleAttempts = mutableStateOf("")
    val playerDribbleSuccess = mutableStateOf("")
    val playerKeyPasses = mutableStateOf("")
    val playerPassAccuracy = mutableStateOf("")

    //Player Defense
    val playerTackles = mutableStateOf("")
    val playerBlocks = mutableStateOf("")
    val playerInterceptions = mutableStateOf("")
    val playerDuels = mutableStateOf("")
    val playerDuelsWon = mutableStateOf("")

    //Player Discipline
    val playerFoulsCommitted = mutableStateOf("")
    val playerFoulsDrawn = mutableStateOf("")
    val playerPenaltiesCommitted = mutableStateOf("")
    val playerPenaltiesDrawn = mutableStateOf("")
    val playerReds = mutableStateOf("")
    val playerYellows = mutableStateOf("")
    val playerYellowRed = mutableStateOf("")

    fun onDestroy() {
        compositeDisposable.dispose()
    }

    override fun subscribeOnLifecycle(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }


    fun fetchPlayerProfile(playerId: String, teamId: String, leagueId: String) {
        subscribeOnLifecycle(
            repository.fetchPlayerProfile(leagueId, teamId, playerId)
                .subscribe(
                    {
                        it.player?.nationality?.let { country ->
                            getPlayerCountry(country)
                        }
                        val player = it.player
                        val statistics = it.statistics
                        if(player == null || statistics.isNullOrEmpty()){
                            shouldShowErrorDialog.value = true
                        } else{
                            setPlayerProfileData(player, statistics[0])
                            setPlayerDetails(player, statistics[0])
                            setPlayerMedical(statistics[0])
                            setPlayerAttacking(statistics[0])
                            setPlayerDefense(statistics[0])
                            setPlayerDiscipline(statistics[0])
                        }
                    },
                    {
                        shouldShowErrorDialog.value = true
                    }
                )
        )
    }

    fun fetchPlayerProfile(teamId: String, playerId: String) {
        subscribeOnLifecycle(repository.fetchPlayerProfile(teamId, playerId)
            .subscribe(
                {
                    it.player?.nationality?.let { country ->
                        getPlayerCountry(country)
                    }
                    val player = it.player
                    val statistics = it.statistics
                    if(player == null || statistics.isNullOrEmpty()){
                        shouldShowErrorDialog.value = true
                    } else{
                        setPlayerProfileData(player, statistics[0])
                        setPlayerDetails(player, statistics[0])
                        setPlayerMedical(statistics[0])
                        setPlayerAttacking(statistics[0])
                        setPlayerDefense(statistics[0])
                        setPlayerDiscipline(statistics[0])
                    }
                },
                {
                    shouldShowErrorDialog.value = true
                }
            ))
    }

    private fun getPlayerCountry(country: String){
        subscribeOnLifecycle(
            repository.getPlayerCountry(country)
                .subscribe(
                    {
                        playerCountryFlag.value = it.flag ?: ""
                    },
                    {
                        Log.println(Log.ERROR, "PlayerProfileViewModel", it.toString())
                    }
                )
        )
    }

    private fun setPlayerProfileData(player: Player, statistic: PlayerStatistic){
        playerPhoto.value = player.photo ?: R.drawable.ic_baseline_person_24
        playerRating.value = statistic.games?.rating ?: "0.0"
        playerRatingDouble.value = statistic.games?.rating?.toDouble() ?: 0.0
        playerInjured.value = player.injured ?: false
        playerName.value = "${player.firstname?: "Not Provided"} ${player.lastname ?: "Not Provided"}"
        playerLogo.value = statistic.team?.logo ?: R.drawable.ic_baseline_sports_soccer_24
        playerCountryFlagDescription.value = if(player.nationality != null){
            "${player.nationality} flag"
        }
        else{
            "Not Provided"
        }
    }

    private fun setPlayerDetails(player: Player, statistic: PlayerStatistic){
        playerPosition.value = statistic.games?.position ?: "Not Provided"
        playerAge.value = player.age?.toString() ?: "Not Provided"
        playerHeight.value = player.height ?: "Not Provided"
        playerWeight.value = player.weight ?: "Not Provided"
    }

    private fun setPlayerMedical(statistic: PlayerStatistic){
        playerAppearances.value = statistic.games?.appearences?.toString() ?: "Not Provided"
        playerLineups.value = statistic.games?.lineups?.toString() ?: "Not Provided"
        playerMinutes.value = statistic.games?.minutes?.toString() ?: "Not Provided"
    }

    private fun setPlayerAttacking(statistic: PlayerStatistic){
        playerShots.value = statistic.shots?.total?.toString() ?: "Not Provided"
        playerShotsOnTarget.value = statistic.shots?.on?.toString() ?: "Not Provided"
        playerGoals.value = statistic.goals?.total?.toString() ?: "Not Provided"
        playerAssists.value = statistic.goals?.assists?.toString() ?: "Not Provided"
        playerDribbleAttempts.value = statistic.dribbles?.attempts?.toString() ?: "Not Provided"
        playerDribbleSuccess.value = statistic.dribbles?.success?.toString() ?: "Not Provided"
        playerKeyPasses.value = statistic.passes?.key?.toString() ?: "Not Provided"
        playerPassAccuracy.value = statistic.passes?.accuracy?.toString() ?: "Not Provided"
    }

    private fun setPlayerDefense(statistic: PlayerStatistic){
        playerTackles.value = statistic.tackles?.total?.toString() ?: "Not Provided"
        playerBlocks.value = statistic.tackles?.blocks?.toString() ?: "Not Provided"
        playerInterceptions.value = statistic.tackles?.interceptions?.toString() ?: "Not Provided"
        playerDuels.value = statistic.duels?.total?.toString() ?: "Not Provided"
        playerDuelsWon.value = statistic.duels?.won?.toString() ?: "Not Provided"
    }

    private fun setPlayerDiscipline(statistic: PlayerStatistic){
        playerFoulsCommitted.value = statistic.fouls?.committed?.toString() ?: "Not Provided"
        playerFoulsDrawn.value = statistic.fouls?.drawn?.toString() ?: "Not Provided"
        playerPenaltiesCommitted.value = statistic.penalty?.commited?.toString() ?: "Not Provided"
        playerPenaltiesDrawn.value = statistic.penalty?.won?.toString() ?: "Not Provided"
        playerReds.value = statistic.cards?.red?.toString() ?: "Not Provided"
        playerYellows.value = statistic.cards?.yellow?.toString() ?: "Not Provided"
        playerYellowRed.value = statistic.cards?.yellowred?.toString() ?: "Not Provided"
    }
}