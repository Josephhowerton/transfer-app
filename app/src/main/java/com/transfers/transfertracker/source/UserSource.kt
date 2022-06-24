package com.transfers.transfertracker.source

import com.transfers.transfertracker.model.User
import com.transfers.transfertracker.model.result.BaseResult
import io.reactivex.rxjava3.core.Single

interface UserSource {
    fun getUser(id: String): Single<User>
    fun createUser(user: User): Single<BaseResult<Boolean>>
    fun updateUser(user: User): Single<BaseResult<Boolean>>
}