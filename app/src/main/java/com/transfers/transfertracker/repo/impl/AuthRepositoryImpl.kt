package com.transfers.transfertracker.repo.impl

import androidx.annotation.NonNull
import com.google.firebase.auth.FirebaseAuth
import com.transfers.transfertracker.source.AuthSource
import com.transfers.transfertracker.source.UserSource
import com.transfers.transfertracker.model.User
import com.transfers.transfertracker.util.result.BaseResult
import com.transfers.transfertracker.repo.AuthRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val userSource: UserSource, private val authSource: AuthSource)
    : AuthRepository {

    override fun signOut() { authSource.signOut() }

    override fun isUserLoggedIn(): Single<BaseResult<Boolean>> =
        Single.create {
            FirebaseAuth.getInstance().currentUser?.let {

            }
        }

    override fun signUp(@NonNull displayName: String, @NonNull email: String, @NonNull password: String): Single<BaseResult<Boolean>> =
        authSource.signUp(displayName, email, password)
            .subscribeOn(Schedulers.io())
            .flatMap { userSource.createUser(User(it.uid, displayName, email)) }
            .observeOn(AndroidSchedulers.mainThread())

    override fun signIn(@NonNull email: String,@NonNull  password: String): Single<BaseResult<Boolean>> =
        authSource.signIn(email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun sendPasswordResetEmail(@NonNull email: String ): Single<BaseResult<Boolean>> =
        authSource.sendPasswordResetEmail(email)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(AndroidSchedulers.mainThread())
}