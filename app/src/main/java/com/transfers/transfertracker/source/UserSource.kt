package com.transfers.transfertracker.source

import com.transfers.transfertracker.model.User
import com.transfers.transfertracker.model.teams.Team
import com.transfers.transfertracker.util.result.BaseResult
import io.reactivex.rxjava3.core.Single

interface UserSource {
    fun getUser(): Single<User>
    fun createUser(user: User): Single<BaseResult<Boolean>>
    fun updateUser(user: User): Single<BaseResult<Boolean>>
    fun getSelectedTeam(user: User): Single<Team>
    fun updateSelectedTeam(user: User)
    fun getUnselectedTeams(user: User): Single<List<Team>>
    fun saveSelectedTeam(user: User, team: Team) : Single<BaseResult<Boolean>>
}