package com.transfers.transfertracker.source

import com.transfers.transfertracker.model.User
import com.transfers.transfertracker.model.teams.Team
import com.transfers.transfertracker.util.result.BaseResult
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface UserSource {
    fun isUserLoggedIn(): Single<BaseResult<Boolean>>
    fun getUser(): Single<User>
    fun createUser(user: User): Single<BaseResult<Boolean>>
    fun updateUser(user: User): Single<User>
    fun updateUserTeam(user: User): Single<BaseResult<Boolean>>
    fun getCurrentTeam(user: User): Single<Team>
    fun getAllUsersTeam(uid: String) : Single<List<Team>>
    fun saveSelectedTeam(user: User, team: Team) : Single<BaseResult<Boolean>>
    fun removeSelectedTeam(user: User, team: Team) : Single<BaseResult<Boolean>>
}