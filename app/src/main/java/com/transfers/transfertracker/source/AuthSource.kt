package com.transfers.transfertracker.source

import com.google.firebase.auth.FirebaseUser
import com.transfers.transfertracker.util.result.BaseResult
import io.reactivex.rxjava3.core.Single

interface AuthSource {
    fun signUp(displayName: String, email: String, password: String): Single<FirebaseUser>
    fun signIn(email: String, password: String): Single<BaseResult<Boolean>>
    fun signOut(): Single<Boolean>
    fun sendPasswordResetEmail(email: String): Single<BaseResult<Boolean>>
}