package com.transfers.transfertracker.view.team

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.transfers.transfertracker.R
import com.transfers.transfertracker.model.stats.*
import com.transfers.transfertracker.repo.MainRepository
import com.transfers.transfertracker.util.SubscribeOnLifecycle
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import java.text.DecimalFormat
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class TeamProfileViewModel @Inject constructor(
    private val repository: MainRepository
    ): ViewModel(), SubscribeOnLifecycle {
    private val compositeDisposable = CompositeDisposable()

    val shouldShowErrorDialog = mutableStateOf(false)
    val errorTitle = mutableStateOf(R.string.title_generic_error)
    val errorMessage = mutableStateOf(R.string.message_find_countries_error)

    //Team Profile
    val teamLogo = mutableStateOf("")
    val teamName = mutableStateOf("")
    val leagueName = mutableStateOf("")
    val leagueCountryFlag = mutableStateOf("")
    val leagueLogo = mutableStateOf("")

    //Team Lineups
    val teamLineups = mutableListOf<Lineup>()

    //Team Form
    val teamForm = mutableStateOf("")
    val teamHomeGamesPlayed = mutableStateOf("")
    val teamHomeGamesWon = mutableStateOf("")
    val teamHomeGamesLost = mutableStateOf("")
    val teamAwayGamesPlayed = mutableStateOf("")
    val teamAwayGamesWon = mutableStateOf("")
    val teamAwayGamesLost = mutableStateOf("")
    val teamTotalGamesPlayed = mutableStateOf("")
    val teamTotalGamesWon = mutableStateOf("")
    val teamTotalGamesLost = mutableStateOf("")

    //Team Attacking
    val teamTotalGoalsItemAttacking = mutableStateOf("")
    val teamTotalGoalsItemAverageAttacking = mutableStateOf("")
    val teamHomeGoalsItemAttacking = mutableStateOf("")
    val teamHomeGoalsItemAverageAttacking = mutableStateOf("")
    val teamAwayGoalsItemAttacking = mutableStateOf("")
    val teamAwayGoalsItemAverageAttacking = mutableStateOf("")
    val teamMinute015Attacking = mutableStateOf("")
    val teamMinute015PercentAttacking = mutableStateOf("")
    val teamMinute1630Attacking = mutableStateOf("")
    val teamMinute1630PercentAttacking = mutableStateOf("")
    val teamMinute3145Attacking = mutableStateOf("")
    val teamMinute3145PercentAttacking = mutableStateOf("")
    val teamMinute4660Attacking = mutableStateOf("")
    val teamMinute4660PercentAttacking = mutableStateOf("")
    val teamMinute6175Attacking = mutableStateOf("")
    val teamMinute6175PercentAttacking = mutableStateOf("")
    val teamMinute7690Attacking = mutableStateOf("")
    val teamMinute7690PercentAttacking = mutableStateOf("")
    val teamExtraTimeAttacking = mutableStateOf("")
    val teamExtraTimePercentAttacking = mutableStateOf("")

    //Team Defense
    val teamTotalGoalsItemDefense = mutableStateOf("")
    val teamTotalGoalsItemAverageDefense = mutableStateOf("")
    val teamHomeGoalsItemDefense = mutableStateOf("")
    val teamHomeGoalsItemAverageDefense = mutableStateOf("")
    val teamAwayGoalsItemDefense = mutableStateOf("")
    val teamAwayGoalsItemAverageDefense = mutableStateOf("")
    val teamMinute015Defense = mutableStateOf("")
    val teamMinute015PercentDefense = mutableStateOf("")
    val teamMinute1630Defense = mutableStateOf("")
    val teamMinute1630PercentDefense = mutableStateOf("")
    val teamMinute3145Defense = mutableStateOf("")
    val teamMinute3145PercentDefense = mutableStateOf("")
    val teamMinute4660Defense = mutableStateOf("")
    val teamMinute4660PercentDefense = mutableStateOf("")
    val teamMinute6175Defense = mutableStateOf("")
    val teamMinute6175PercentDefense = mutableStateOf("")
    val teamMinute7690Defense = mutableStateOf("")
    val teamMinute7690PercentDefense = mutableStateOf("")
    val teamExtraTimeDefense = mutableStateOf("")
    val teamExtraTimePercentDefense = mutableStateOf("")

    //Clean Sheet
    val teamCleanSheet = mutableStateOf("")

    //Team Yellows
    val teamYellows015 = mutableStateOf("")
    val teamYellows015Percent = mutableStateOf("")
    val teamYellows1630 = mutableStateOf("")
    val teamYellows1630Percent = mutableStateOf("")
    val teamYellows3145 = mutableStateOf("")
    val teamYellows3145Percent = mutableStateOf("")
    val teamYellows4660 = mutableStateOf("")
    val teamYellows4660Percent = mutableStateOf("")
    val teamYellows6175 = mutableStateOf("")
    val teamYellows6175Percent = mutableStateOf("")
    val teamYellows7690 = mutableStateOf("")
    val teamYellows7690Percent = mutableStateOf("")
    val teamYellowsExtraTime = mutableStateOf("")
    val teamYellowsExtraTimePercent = mutableStateOf("")

    //Team Reds
    val teamReds015 = mutableStateOf("")
    val teamReds015Percent = mutableStateOf("")
    val teamReds1630 = mutableStateOf("")
    val teamReds1630Percent = mutableStateOf("")
    val teamReds3145 = mutableStateOf("")
    val teamReds3145Percent = mutableStateOf("")
    val teamReds4660 = mutableStateOf("")
    val teamReds4660Percent = mutableStateOf("")
    val teamReds6175 = mutableStateOf("")
    val teamReds6175Percent = mutableStateOf("")
    val teamReds7690 = mutableStateOf("")
    val teamReds7690Percent = mutableStateOf("")
    val teamRedsExtraTime = mutableStateOf("")
    val teamRedsExtraTimePercent = mutableStateOf("")

    fun onDestroy() {
        compositeDisposable.dispose()
    }


    override fun subscribeOnLifecycle(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun fetchTeamStatistics(leagueId: String, teamId: String) {
        subscribeOnLifecycle(
            repository.fetchTeamStatistics(leagueId, teamId)
                .subscribe(
                    {
                        setTeamInfo(it.team, it.league)
                        setTeamLineups(it.lineups)
                        formatForm(it.form)
                        setTeamForm(it.fixtures)
                        setTeamAttacking(it.goals)
                        formatExtraTimeAttacking(it.goals)
                        setTeamDefense(it.goals)
                        formatExtraTimeDefense(it.goals)
                        setupCleanSheet(it.clean_sheet)
                        setupYellowCards(it.cards)
                        formatYellowExtraTime(it.cards)
                        setupRedCards(it.cards)
                        formatRedExtraTime(it.cards)
                    },
                    {
                        shouldShowErrorDialog.value = true
                    }
                )
        )
    }

    private fun setTeamInfo(team: Team?, league: League?){
        teamLogo.value = team?.logo ?: ""
        teamName.value = team?.name ?: "Not Available"
        leagueName.value = league?.name ?: "Not Available"
        leagueCountryFlag.value = league?.flag ?: ""
        leagueLogo.value = league?.logo ?: ""
    }

    private fun setTeamLineups(lineups: List<Lineup>?){
        if(lineups != null){
            teamLineups.clear()
            teamLineups.addAll(lineups)
        }
    }

    private fun setTeamForm(fixtures: Fixtures?){
        teamHomeGamesPlayed.value = fixtures?.played?.home?.toString() ?: "Not Available"
        teamHomeGamesWon.value = fixtures?.wins?.home?.toString() ?: "Not Available"
        teamHomeGamesLost.value = fixtures?.loses?.home?.toString() ?: "Not Available"
        teamAwayGamesPlayed.value = fixtures?.played?.away?.toString() ?: "Not Available"
        teamAwayGamesWon.value = fixtures?.wins?.away?.toString() ?: "Not Available"
        teamAwayGamesLost.value = fixtures?.loses?.away?.toString() ?: "Not Available"
        teamTotalGamesPlayed.value = fixtures?.played?.total?.toString() ?: "Not Available"
        teamTotalGamesWon.value = fixtures?.wins?.total?.toString() ?: "Not Available"
        teamTotalGamesLost.value = fixtures?.loses?.total?.toString() ?: "Not Available"
    }

    private fun setTeamAttacking(goalsX: GoalsX?){
        teamTotalGoalsItemAttacking.value = goalsX?.`for`?.total?.total?.toString() ?: "0"
        val totalAverage = goalsX?.`for`?.average?.total ?: "0.0"
        teamTotalGoalsItemAverageAttacking.value = "Avg: $totalAverage"

        teamHomeGoalsItemAttacking.value = goalsX?.`for`?.total?.home?.toString() ?: "0"
        val homeAverage = goalsX?.`for`?.average?.home ?: "0.0"
        teamHomeGoalsItemAverageAttacking.value = "Avg: $homeAverage"

        teamAwayGoalsItemAttacking.value = goalsX?.`for`?.total?.away?.toString() ?: "0"
        val awayAverage = goalsX?.`for`?.average?.away ?: "0.0"
        teamAwayGoalsItemAverageAttacking.value = "Avg: $awayAverage"

        teamMinute015Attacking.value = goalsX?.`for`?.minute?.`0-15`?.total?.toString() ?: "0"
        teamMinute015PercentAttacking.value = goalsX?.`for`?.minute?.`0-15`?.percentage ?: "0.00%"

        teamMinute1630Attacking.value = goalsX?.`for`?.minute?.`16-30`?.total?.toString() ?: "0"
        teamMinute1630PercentAttacking.value = goalsX?.`for`?.minute?.`16-30`?.percentage ?: "0.00%"

        teamMinute3145Attacking.value = goalsX?.`for`?.minute?.`31-45`?.total?.toString() ?: "0"
        teamMinute3145PercentAttacking.value = goalsX?.`for`?.minute?.`31-45`?.percentage ?: "0.00%"

        teamMinute4660Attacking.value = goalsX?.`for`?.minute?.`46-60`?.total?.toString() ?: "0"
        teamMinute4660PercentAttacking.value = goalsX?.`for`?.minute?.`46-60`?.percentage ?: "0.00%"

        teamMinute6175Attacking.value = goalsX?.`for`?.minute?.`61-75`?.total?.toString() ?: "0"
        teamMinute6175PercentAttacking.value = goalsX?.`for`?.minute?.`61-75`?.percentage ?: "0.00%"

        teamMinute7690Attacking.value = goalsX?.`for`?.minute?.`76-90`?.total?.toString() ?: "0"
        teamMinute7690PercentAttacking.value = goalsX?.`for`?.minute?.`76-90`?.percentage ?: "0.00%"

    }

    private fun setTeamDefense(goalsX: GoalsX?){
        teamTotalGoalsItemDefense.value = goalsX?.against?.total?.total?.toString() ?: "0"
        val totalAverage = goalsX?.against?.average?.total ?: "0.0"
        teamTotalGoalsItemAverageDefense.value = "Avg: $totalAverage"

        teamHomeGoalsItemDefense.value = goalsX?.against?.total?.home?.toString() ?: "0"
        val homeAverage = goalsX?.against?.average?.home ?: "0.0"
        teamHomeGoalsItemAverageDefense.value = "Avg: $homeAverage"

        teamAwayGoalsItemDefense.value = goalsX?.against?.total?.away?.toString() ?: "0"
        val awayAverage = goalsX?.against?.average?.away ?: "0.0"
        teamAwayGoalsItemAverageDefense.value = "Avg: $awayAverage"

        teamMinute015Defense.value = goalsX?.against?.minute?.`0-15`?.total?.toString() ?: "0"
        teamMinute015PercentDefense.value = goalsX?.against?.minute?.`0-15`?.percentage ?: "0.00%"

        teamMinute1630Defense.value = goalsX?.against?.minute?.`16-30`?.total?.toString() ?: "0"
        teamMinute1630PercentDefense.value = goalsX?.against?.minute?.`16-30`?.percentage ?: "0.00%"

        teamMinute3145Defense.value = goalsX?.against?.minute?.`31-45`?.total?.toString() ?: "0"
        teamMinute3145PercentDefense.value = goalsX?.against?.minute?.`31-45`?.percentage ?: "0.00%"

        teamMinute4660Defense.value = goalsX?.against?.minute?.`46-60`?.total?.toString() ?: "0"
        teamMinute4660PercentDefense.value = goalsX?.against?.minute?.`46-60`?.percentage ?: "0.00%"

        teamMinute6175Defense.value = goalsX?.against?.minute?.`61-75`?.total?.toString() ?: "0"
        teamMinute6175PercentDefense.value = goalsX?.against?.minute?.`61-75`?.percentage ?: "0.00%"

        teamMinute7690Defense.value = goalsX?.against?.minute?.`76-90`?.total?.toString() ?: "0"
        teamMinute7690PercentDefense.value = goalsX?.against?.minute?.`76-90`?.percentage ?: "0.00%"

    }

    private fun setupCleanSheet(cleanSheet: CleanSheet?) {
        teamCleanSheet.value = cleanSheet?.total?.toString() ?: "0"
    }

    private fun setupYellowCards(cards: Cards?){
        teamYellows015.value = cards?.yellow?.`0-15`?.total?.toString() ?: "0"
        teamYellows015Percent.value = cards?.yellow?.`0-15`?.percentage ?: "0.00%"

        teamYellows1630.value = cards?.yellow?.`16-30`?.total?.toString() ?: "0"
        teamYellows1630Percent.value = cards?.yellow?.`16-30`?.percentage ?: "0.00%"

        teamYellows3145.value = cards?.yellow?.`31-45`?.total?.toString() ?: "0"
        teamYellows3145Percent.value = cards?.yellow?.`31-45`?.percentage ?: "0.00%"

        teamYellows4660.value = cards?.yellow?.`46-60`?.total?.toString() ?: "0"
        teamYellows4660Percent.value = cards?.yellow?.`46-60`?.percentage ?: "0.00%"

        teamYellows6175.value = cards?.yellow?.`61-75`?.total?.toString() ?: "0"
        teamYellows6175Percent.value = cards?.yellow?.`61-75`?.percentage ?: "0.00%"

        teamYellows7690.value = cards?.yellow?.`76-90`?.total?.toString() ?: "0"
        teamYellows7690Percent.value = cards?.yellow?.`76-90`?.percentage ?: "0.00%"

    }

    private fun setupRedCards(cards: Cards?){

        teamReds015.value = cards?.red?.`0-15`?.total?.toString() ?: "0"
        teamReds015Percent.value = cards?.red?.`0-15`?.percentage ?: "0.00%"

        teamReds1630.value = cards?.red?.`16-30`?.total?.toString() ?: "0"
        teamReds1630Percent.value = cards?.red?.`16-30`?.percentage ?: "0.00%"

        teamReds3145.value = cards?.red?.`31-45`?.total?.toString() ?: "0"
        teamReds3145Percent.value = cards?.red?.`31-45`?.percentage ?: "0.00%"

        teamReds4660.value = cards?.red?.`46-60`?.total?.toString() ?: "0"
        teamReds4660Percent.value = cards?.red?.`46-60`?.percentage ?: "0.00%"

        teamReds6175.value = cards?.red?.`61-75`?.total?.toString() ?: "0"
        teamReds6175Percent.value = cards?.red?.`61-75`?.percentage ?: "0.00%"

        teamReds7690.value = cards?.red?.`76-90`?.total?.toString() ?: "0"
        teamReds7690Percent.value = cards?.red?.`76-90`?.percentage ?: "0.00%"

    }

    private fun formatForm(data: String?) {
        data?.let {
            teamForm.value = it
            if(it.length > 10){
                teamForm.value = it.slice(IntRange(0,4))
            }
        } ?: run {
            teamForm.value = "Not Provided"
        }
    }

    private fun formatExtraTimeAttacking(goalsX: GoalsX?) {
        val total91105 = goalsX?.`for`?.minute?.`91-105`?.total?.toDouble() ?: 0.0
        val total106120= goalsX?.`for`?.minute?.`106-120`?.total?.toDouble() ?: 0.0
        val total = goalsX?.`for`?.total?.total?.toDouble() ?: 1.0
        val percent = ( (total91105 + total106120 )/total ) * 100.0
        teamExtraTimeAttacking.value = (total91105 + total106120 ).toInt().toString()
        teamExtraTimePercentAttacking.value = DecimalFormat("#.##").format(percent).toString()
    }

    private fun formatExtraTimeDefense(goalsX: GoalsX?) {
        val total91105 = goalsX?.against?.minute?.`91-105`?.total?.toDouble() ?: 0.0
        val total106120= goalsX?.against?.minute?.`106-120`?.total?.toDouble() ?: 0.0
        val total = goalsX?.against?.total?.total?.toDouble() ?: 1.0
        val percent = ( (total91105 + total106120 )/total ) * 100.0
        teamExtraTimeDefense.value = (total91105 + total106120 ).toInt().toString()
        teamExtraTimePercentDefense.value = DecimalFormat("#.##").format(percent).toString()
    }

    private fun formatYellowExtraTime(cards: Cards?) {
        val total91105 = cards?.yellow?.`91-105`?.total?.toDouble() ?: 0.0
        val total106120= cards?.yellow?.`106-120`?.total?.toDouble() ?: 0.0
        val total = (total91105 + total106120).roundToInt()

        val total91105Percent = cards?.yellow?.`91-105`?.percentage?.replace("%", "")?.toDouble() ?: 0.0
        val total106120Percent = cards?.yellow?.`106-120`?.percentage?.replace("%", "")?.toDouble() ?: 0.0
        val percent = total91105Percent + total106120Percent

        teamYellowsExtraTime.value = total.toString()
        teamYellowsExtraTimePercent.value = "${DecimalFormat("#.##").format(percent)}%"
    }

    private fun formatRedExtraTime(cards: Cards?) {
        val total91105 = cards?.red?.`91-105`?.total?.toDouble() ?: 0.0
        val total106120= cards?.red?.`106-120`?.total?.toDouble() ?: 0.0
        val total = (total91105 + total106120).roundToInt()

        val total91105Percent = cards?.red?.`91-105`?.percentage?.replace("%", "")?.toDouble() ?: 0.0
        val total106120Percent = cards?.red?.`106-120`?.percentage?.replace("%", "")?.toDouble() ?: 0.0
        val percent = total91105Percent + total106120Percent

        teamRedsExtraTime.value = total.toString()
        teamRedsExtraTimePercent.value = "${DecimalFormat("#.##").format(percent)}%"
    }
}