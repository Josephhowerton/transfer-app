package com.transfers.source.impl

import androidx.annotation.NonNull
import com.transfers.source.AuthSource
import com.transfers.source.UserSource
import com.transfers.transfertracker.model.User
import com.transfers.transfertracker.model.errors.AuthException
import com.transfers.transfertracker.model.result.BaseResult
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class UserAuthRepository @Inject constructor(private val userSource: UserSource, private val authSource: AuthSource) {

    fun initializeUser(@NonNull id: String): Single<BaseResult<Boolean>> =
        userSource.getUserRemote(id)
            .flatMap {
                userSource.updateUserLocal(it)
                    .doOnSuccess { result ->
                        if(result is BaseResult.Success<Boolean>){
                            if(!result.data){
                                userSource.createUserLocal(user = it)
                            }
                        }
                    }
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
            }

    fun signUp(@NonNull displayName: String, @NonNull email: String, @NonNull password: String): Single<BaseResult<Boolean>> =
        authSource.signUp(email, password)
            .subscribeOn(Schedulers.io())
            .flatMap { userSource.createUserRemote(User(it.userId, displayName, email, "")) }
            .flatMap{ userSource.createUserLocal(it) }
            .observeOn(AndroidSchedulers.mainThread())

    fun signIn(@NonNull email: String,@NonNull  password: String): Single<BaseResult<Boolean>> =
        authSource.signIn(email, password)
            .subscribeOn(Schedulers.io())
            .flatMap {
                userSource.getUserRemote(it.userId)
            }
            .flatMap {
                    user -> userSource.updateUserLocal(user = user)
            }
            .flatMap { result ->
                if(result is BaseResult.Error){
                    val user = (result.exception as AuthException).user
                    if(user != null){
                        return@flatMap userSource.createUserLocal(user)
                    }
                }
                return@flatMap Single.create { it.onSuccess(result) }
            }

    fun sendPasswordResetEmail(@NonNull email: String ): Single<BaseResult<Boolean>> =
        authSource.sendPasswordResetEmail(email)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(AndroidSchedulers.mainThread())

    fun logout() = authSource.logout()

}