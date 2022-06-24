package com.transfers.transfertracker.source.impl

import com.transfers.transfertracker.model.User
import com.transfers.transfertracker.model.result.BaseResult
import com.transfers.transfertracker.source.UserSource
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class UserSourceImpl @Inject constructor(): UserSource {
    override fun getUser(id: String): Single<User> {
        TODO("Not yet implemented")
    }

    override fun createUser(user: User): Single<BaseResult<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun updateUser(user: User): Single<BaseResult<Boolean>> {
        TODO("Not yet implemented")
    }
}